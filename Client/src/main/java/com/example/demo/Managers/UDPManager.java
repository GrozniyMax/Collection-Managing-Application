package com.example.demo.Managers;

import com.example.demo.CommonClasses.Exceptions.DeserializationException;
import com.example.demo.CommonClasses.Exceptions.FunctionFailedException;
import com.example.demo.CommonClasses.Interaction.Requests.AutentificationRequest;
import com.example.demo.CommonClasses.Interaction.Requests.RefreshRequest;
import com.example.demo.CommonClasses.Interaction.Requests.RegistrationRequest;
import com.example.demo.CommonClasses.Interaction.Requests.ValidIDRequest;
import com.example.demo.CommonClasses.Interaction.Responces.AutentificationResponce;
import com.example.demo.CommonClasses.Interaction.Responces.CheckIDResponce;
import com.example.demo.CommonClasses.Interaction.Responces.RefreshResponce;
import com.example.demo.CommonClasses.Interaction.Responces.ServerResponse;
import com.example.demo.CommonClasses.Utils.IOUtils;
import com.example.demo.Proxies.FlatProxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

public class UDPManager {
    static final String HOSTNAME = "localhost";
    static final int SERVER_PORT = 8080;
    private final InetSocketAddress serverAddress;
    private DatagramSocket clientSocket;
    private static final int waitingInMillis = 6000;

    private static UDPManager commandSenderInstance = new UDPManager();
    private static UDPManager refresherInstance = new UDPManager();

    public static UDPManager getCommandSenderInstance() {
        return commandSenderInstance;
    }
    public static UDPManager getRefresherInstance() {
        return refresherInstance;
    }

    public UDPManager() {
        try {
            this.serverAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
            clientSocket = new DatagramSocket(null);
            clientSocket.setSoTimeout(waitingInMillis);
        } catch (UnknownHostException|SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendLoginRequest() throws IOException {
        byte[] request = IOUtils.toByteArray(SessionContext.toAutentificationRequest());
        DatagramPacket helloPackage = new DatagramPacket(request, request.length, commandSenderInstance.serverAddress);
        commandSenderInstance.clientSocket.send(helloPackage);
    }

    public static void sendCommandRequest() {
        byte[] buffer = IOUtils.toByteArray(CommandContext.instance().toRequest());
        try {
            commandSenderInstance.clientSocket.send(new DatagramPacket(buffer, buffer.length,commandSenderInstance.serverAddress));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendAbstractRequest(AutentificationRequest request){
        byte[] buffer = IOUtils.toByteArray(request);
        try {
            commandSenderInstance.clientSocket.send(new DatagramPacket(buffer, buffer.length, commandSenderInstance.serverAddress));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<FlatProxy> getRefreshResponce(){
        try {
            refresherInstance.sendAbstractRequest(new RefreshRequest());
            Object object = getAbstractServerResponce();
            return ((RefreshResponce) object).objects().stream().map(FlatProxy::valueOf).toList();
        } catch (FunctionFailedException e) {
            //TODO add this exception handling
            throw new RuntimeException("Reading failed");
        }
    }

    public static Object getAbstractServerResponce() throws FunctionFailedException {
        byte[] buffer = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        IOUtils.ChunksBuilder builder = new IOUtils.ChunksBuilder();

        try {
            do {
                commandSenderInstance.clientSocket.receive(packet);
                builder.add(packet.getData());
            } while(!builder.ready());

            return builder.toObject();
        } catch (SocketTimeoutException e) {
            if (builder.ready()) {
                try {
                    return builder.toObject();
                } catch (DeserializationException exception) {
                    throw new FunctionFailedException();
                }
            } else {
                throw new FunctionFailedException();
            }
        } catch (DeserializationException | IOException e) {
            throw new FunctionFailedException();
        }
    }

    public static ServerResponse getServerResponce() throws FunctionFailedException {
        return (ServerResponse) getAbstractServerResponce();
    }

    public static boolean getCheckIDResponce(Long id) throws FunctionFailedException {
        sendAbstractRequest(new ValidIDRequest(id));
        return ((CheckIDResponce) getAbstractServerResponce()).result();
    }

    public static boolean getAutentificationResponce() throws FunctionFailedException, IOException {
        sendLoginRequest();
        return ((AutentificationResponce) getAbstractServerResponce()).status();
    }

    public static boolean getRegistrationResponce() throws FunctionFailedException {
        sendAbstractRequest(new RegistrationRequest());
        return ((AutentificationResponce) getAbstractServerResponce()).status();
    }



//    public ServerResponse update(Long id) throws FunctionFailedException, AuthError {
//        byte[] buffer = IOUtils.toByteArray(new UpdateRecord(this.greetRequest, id));
//
//        try {
//            this.clientSocket.send(new DatagramPacket(buffer, buffer.length));
//        } catch (IOException var5) {
//            IOException e = var5;
//            throw new RuntimeException(e);
//        }
//
//        ServerResponse response = this.getServerResponce();
//        if (!response.status()) {
//            throw new AuthError("Не ваш объект");
//        } else {
//            Flat read_obj = ConsoleInputManager.getInstance().readFlat();
//            this.sendUserRequest(new UserRequest(new CommandDescription("update", UserParams.BOTH, new IDParser()), new UserData(id, read_obj), this.greetRequest));
//            return this.getServerResponce();
//        }
//    }
}
