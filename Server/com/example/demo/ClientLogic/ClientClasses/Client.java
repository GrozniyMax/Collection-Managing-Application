package com.example.demo.ClientLogic.ClientClasses;



import com.example.demo.CommonClasses.Interaction.Requests.CommandRequest;
import com.example.demo.CommonClasses.Interaction.Requests.CredentialsData;
import com.example.demo.CommonClasses.Interaction.Responces.ServerResponse;
import com.example.demo.Exceptions.ExitCommandException;
import com.example.demo.Managers.CommandManager;
import com.example.demo.Main.Main;
import com.example.demo.Net.ConnectionStatus;


import java.net.SocketAddress;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Класс реализующий клиента
 */
public class Client {
    static final Logger logger = Main.logger;
    static long lastID=0;
    protected Long ID;
    protected ConnectionStatus status;
    protected CommandManager manager;
    protected final long creationTimeInMillis = System.currentTimeMillis();
    protected SocketAddress address;

    protected CredentialsData authData;


    /**
     * Конструктор
     * @param manager - менеджер команд
     * @param address - адрес клиента
     */
    public Client(CommandManager manager, SocketAddress address) {
        this.address = address;
        logger.info("Registered new "+this);
        this.status = ConnectionStatus.CONNECTION_STARTED;
        this.manager = manager;

    }

    /**
     * Метод для выполнения команды
     * @param request - запрос
     * @return - ответ
     * @throws ExitCommandException - если команда завершает работу
     */
    public ServerResponse executeCommand(CommandRequest request) throws ExitCommandException {
        logger.info(this+" started executing command"+request.getComandName());
        status = ConnectionStatus.USER_COMMAND_SENT;
        return manager.handle(request);
    }



    /**
     * Метод для закрытия соединения
     * @return - статус
     */
    public void closeConnection(){
        logger.info(this +" finished session");
    }
    /**
     * Метод для закрытия соединения
     * @param reason - причина
     * @return - статус
     */
    public void closeConnection(String reason){
        logger.info(this +" finished session due to "+reason);
    }

    @Override
    public String toString() {
        return "Client{" +
                "ID=" + ID +
                ", address=" + address +
                '}';
    }

    /**
     * Метод для получения времени создания
     * @return - время создания
     */
    public long getCreationTime() {
        return creationTimeInMillis;
    }

    public SocketAddress getAddress() {
        return address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return creationTimeInMillis == client.creationTimeInMillis && Objects.equals(ID, client.ID) && status == client.status && Objects.equals(manager, client.manager) && Objects.equals(address, client.address) && Objects.equals(authData, client.authData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, status, manager, creationTimeInMillis, address, authData);
    }
}
