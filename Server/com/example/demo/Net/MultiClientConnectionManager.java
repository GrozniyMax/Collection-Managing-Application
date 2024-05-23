package com.example.demo.Net;


import com.example.demo.ClientLogic.ClientClasses.Client;
import com.example.demo.ClientLogic.ClientFactory;


import com.example.demo.CommonClasses.Interaction.Responces.*;
import com.example.demo.CommonClasses.Utils.IOUtils;
import com.example.demo.CommonClasses.Utils.Pair;
import com.example.demo.DataBase.DBController;
import com.example.demo.Exceptions.ExitCommandException;
import com.example.demo.Exceptions.NoAutentificationException;
import com.example.demo.Main.Main;
import com.example.demo.Managers.CommandManager;
import com.example.demo.CommonClasses.Interaction.Requests.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Класс, управляющий подключениями клиентов
 */
public class MultiClientConnectionManager {
    static final Logger logger = Main.logger;
    private CommandManager baseCommandManager;
    private DatagramChannel serverChannel;
    private ClientFactory factory;

    private ConcurrentLinkedQueue<Pair<Client,Serializable>> reponcesToSend = new ConcurrentLinkedQueue<>();
    static final int PORT = 8080;

    static final int checkEveryMillis = 15 * 60 * 1000;

    static final long cleanEveryMillis = 60*60*1000;


    /**
     * Конструктор
     *
     * @param commandManager - менеджер команд
     */
    public MultiClientConnectionManager(CommandManager commandManager) {
        try {
            this.baseCommandManager = commandManager;
            InetSocketAddress s = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
            this.serverChannel = DatagramChannel.open().bind(s);
            this.serverChannel.configureBlocking(false);
            this.factory = new ClientFactory(baseCommandManager);
        } catch (IOException e) {
            logger.warning("Unable to create server channel");
            System.exit(-1);
        }

    }



    private class RefreshRequestHandle implements Callable<Object>{
        private Client client;
        private RefreshRequest request;

        private RefreshRequestHandle(RefreshRequest request,SocketAddress currentAddr){
            this.client = factory.createClient(currentAddr);
            this.request = request;
        }

        @Override
        public Object call() throws Exception {
            var responce = new RefreshResponce(baseCommandManager.getCollectionManager().getList().stream()
                    .map((item)->item.getItem()).toList());
            return reponcesToSend.add(new Pair<>(client, responce));
        }
    }


    private class CollectionWithOwnerShipRequestHandle implements Callable<Object>{
        private Client client;
        private LoadCollectionWithOwnerShip request;

        public CollectionWithOwnerShipRequestHandle(LoadCollectionWithOwnerShip request,SocketAddress clientADDR) {
            this.request = request;
            client = factory.createClient(clientADDR);
        }

        @Override
        public Object call() throws Exception {
            var responce = baseCommandManager.getCollectionManager().getCollection().getList().stream()
                    .map((obj)-> new ListItem(obj.getOwnerLogin(),obj.getItem())).toList();
            return reponcesToSend.add(new Pair<>(client, new OwnerShipResponce(responce)));
        }
    }

    /**
     * Обработчик нового пользователя(Того, который регистрируется)
     */
    private class RegistrationRequestHandle implements Callable<Object>{

        private Client client;
        private AutentificationRequest authData;

        public RegistrationRequestHandle(AutentificationRequest request, SocketAddress currentAddr) {
            client = factory.createClient(currentAddr);
            authData = request;
        }


        @Override
        public Boolean call() throws Exception {
            try {
                logger.info("Registering new user");
                DBController.getInstance().insertUser(authData);
                return reponcesToSend.add(new Pair<>(client,new AutentificationResponce(true)));
            }catch (Exception e){
                //TODO specify exception
                return reponcesToSend.add(new Pair<>(client,new AutentificationResponce(false)));
            }
        }
    }

    /**
     * Обработчик валидного id
     */
    private class ValidIDRequestHandle implements Callable<Object>{

        private Client client;
        private CredentialsData authData;
        private Long ID;

        public ValidIDRequestHandle(ValidIDRequest request, SocketAddress currentAddr) {
            client = factory.createClient(currentAddr);
            authData = request.getCredentials();
            ID = request.getId();
        }


        @Override
        public Boolean call() throws Exception {
            try {
                logger.info("Executing ID check");

                var item = DBController.getInstance().getItemByID(ID);
                if (item.getOwnerLogin().equals(authData.getLogin())) {
                    logger.info("Permission cheched");
                    return reponcesToSend.add(new Pair<>(client, new CheckIDResponce(true)));
                }
                return reponcesToSend.add(new Pair<>(client, new CheckIDResponce(false)));
            } catch (Exception e) {
                //TODO maybe add some code
            }
            return false;
        }
    }


    /**
     * Отправка запроса
     */
    private class SendingData extends RecursiveAction {

        @Override
        protected void compute() {
            var pair = reponcesToSend.poll();
            if (!reponcesToSend.isEmpty()) {
                new SendingData().fork();
            }
            if (pair == null) return;
            Client client = pair.getKey();
            sendChunks(pair.getValue(), client.getAddress());
        }

    }


    /**
     Обработчик клиента, который логиниться
     */
    private class LoginingUser implements Callable<Object>{
        private Client client;
        private AutentificationRequest authData;

        public LoginingUser(AutentificationRequest request, SocketAddress currentAddr) {
            client = factory.createClient(currentAddr);
            authData = request;
        }


        @Override
        public Boolean call() throws Exception {
            try {
                DBController.getInstance().chechAuth(authData);
                logger.info("succesfuly loggined user");
                return reponcesToSend.add(new Pair<>(client,new AutentificationResponce(true)));
            }catch (NoAutentificationException | SQLException e){
                logger.info("Mistake in logined user handling"+e.getMessage());
                return reponcesToSend.add(new Pair<>(client,new AutentificationResponce(false)));
            }
        }
    }

    /**
     Обработчик запроса на исполнение команды
     */
    private class CommandRequestHandle implements Callable<Boolean>{

        Client client;
        CommandRequest request;
        public CommandRequestHandle(CommandRequest request, SocketAddress currentAddr) {
            this.request = request;
            client = factory.createClient(currentAddr);


        }


        @Override
        public Boolean call() throws Exception {
            try {
                ServerResponse s = client.executeCommand(request);
                System.out.println("!!!"+request.getComandName()+":"+s.status());
                return reponcesToSend.add(new Pair<>(client,s));
            } catch (ExitCommandException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Запускает менеджер
     */
    public void run() {
        Thread console = new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    try {
                        if (reader.ready()) {
                            String adminCommand = reader.readLine();
                            if (adminCommand.strip().equals("save")) {
                                logger.info("Read command: save from admin");
                            } else if (adminCommand.strip().equals("exit")) {
                                System.exit(0);
                            }
                        }
                    } catch (IOException e) {
                        logger.warning("Unable to save collention");
                    }
                }
            }
        });
        console.start();


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DBController.getInstance().cleanDatabase();
                logger.info("Cleaned collection");
            }
        },
                cleanEveryMillis,
                cleanEveryMillis);

        logger.info("Started waiting for packages");

        ExecutorService cachedTreadPool = Executors.newCachedThreadPool();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ByteBuffer readBuffer = ByteBuffer.allocate(4096);
        SocketAddress currentClientAddr = null;
        while (true) {
            try {
                readBuffer.clear();
                currentClientAddr = serverChannel.receive(readBuffer);
                if (currentClientAddr!=null){
                    Object readObj = IOUtils.fromByteArray(readBuffer.array());
                    logger.info("Recieved packet from " + currentClientAddr.toString());
                    Class objectClass = readObj.getClass();
                    if (objectClass.equals(AutentificationRequest.class)){
                        cachedTreadPool.submit(new LoginingUser((AutentificationRequest) readObj,currentClientAddr));
                    } else if (objectClass.equals(CommandRequest.class)) {
                        cachedTreadPool.submit(new CommandRequestHandle((CommandRequest) readObj,currentClientAddr));
                    } else if (objectClass.equals(RegistrationRequest.class)) {
                        cachedTreadPool.submit(new RegistrationRequestHandle(((RegistrationRequest) readObj), currentClientAddr));
                    }else if (objectClass.equals(ValidIDRequest.class)) {
                        cachedTreadPool.submit(new ValidIDRequestHandle(((ValidIDRequest) readObj), currentClientAddr ));
                    } else if (objectClass.equals(RefreshRequest.class)) {
                        cachedTreadPool.submit(new RefreshRequestHandle((RefreshRequest) readObj,currentClientAddr));
                    } else if (objectClass.equals(LoadCollectionWithOwnerShip.class)){
                        cachedTreadPool.submit(new CollectionWithOwnerShipRequestHandle((LoadCollectionWithOwnerShip) readObj,currentClientAddr));
                    }else {
                        logger.warning("Unable to read request: Unknown request type");
                    }
                }
                if (!reponcesToSend.isEmpty()){
                    forkJoinPool.invoke(new SendingData());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendChunks(Serializable object, SocketAddress socketAddress){
        IOUtils.Chunck[] chuncks= IOUtils.getChunks(object);
        for(int i=0;i<chuncks.length;i++){
            try {
                serverChannel.send(ByteBuffer.wrap(IOUtils.toByteArray(chuncks[i])),socketAddress);
                Thread.sleep(100);
                logger.info("Sending data %d/%d".formatted(i+1,chuncks[i].length())+" to "+socketAddress.toString());
            } catch (IOException e) {
                logger.info("Missing chunk %d to client %s".formatted(i,socketAddress.toString()));
            }catch (InterruptedException e){
                logger.warning("Unterrupting of thread is not allowed");
            }
        }
    }

}

