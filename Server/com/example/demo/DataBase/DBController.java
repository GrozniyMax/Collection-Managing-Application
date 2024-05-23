package com.example.demo.DataBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.example.demo.CollectionWrappers.CollectionItem;
import com.example.demo.CollectionWrappers.MyCollection;
import com.example.demo.CommonClasses.Entities.*;
import com.example.demo.CommonClasses.Interaction.Requests.AutentificationRequest;
import com.example.demo.Exceptions.NoAutentificationException;
import com.example.demo.Main.Main;


public class DBController {



    static final Logger logger = Main.logger;

    static DBController instance;
    public static DBController getInstance(){
        if (instance==null) instance=new DBController();
        return instance;
    }

    Connection connection;


    private DBController() {
        try {
            Properties info = new Properties();
            info.load(new FileInputStream(new File("Credentials.txt")));
            connection = DriverManager.getConnection(info.getProperty("url"), info);
            //
        } catch (SQLException e) {
            logger.warning("Connection to DB failed for some freaky reason");
            System.exit(-1);
        } catch (FileNotFoundException e) {
            logger.warning("No Credentials.txt found. Unable to connect to DB.");
            System.exit(-1);
        } catch (IOException e) {
            logger.warning("Unable to read Credentials.txt.");
            System.exit(-1);
        }
    }

    public CollectionItem getItemByID(Long id) throws SQLException {
        var statement = connection.prepareStatement("SELECT * FROM flat WHERE id=?");
        statement.setLong(1,id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        Flat flat = new Flat();
        flat.setId(resultSet.getLong("ID"));
        flat.setName(resultSet.getString("name"));
        flat.setCreationDate(resultSet.getTimestamp("creation_Date").toInstant().atZone(ZoneId.systemDefault()));
        flat.setArea(resultSet.getInt("area"));
        flat.setNumberOfRooms(resultSet.getLong("number_of_rooms"));
        flat.setFurnish(Furnish.valueOf(resultSet.getString("furnish").toUpperCase().strip()));
        flat.setView(View.valueOf(resultSet.getString("view").toUpperCase().strip()));
        flat.setTransport(Transport.valueOf(resultSet.getString("transport").toUpperCase().strip()));


        flat.setCoordinates(readCoordinates(resultSet.getInt("coordinated_id")));
        flat.setHouse(readHouse(resultSet.getInt("house_id")));

        String userLogin =getUserLogin(resultSet.getInt("owner"));
        CollectionItem item = new CollectionItem(flat,userLogin);
        return item;
    }

    public int getUserID(String login) throws SQLException {
        var statement = connection.prepareStatement("SELECT id FROM users WHERE user_login=?");
        statement.setString(1,login);
        var s = statement.executeQuery();
        s.next();
        return s.getInt("id");
    }

    public int getCoordinatesID(Coordinates c) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM coordinates where x=? AND y=?");
        statement1.setInt(1,c.getX());
        statement1.setFloat(2,c.getY());
        var r = statement1.executeQuery();
        r.next();
        if (r.getInt("id")==0) throw new SQLException();
        return r.getInt("id");
    }

    public void updateItemByID(long ID,CollectionItem item) throws SQLException {
        var f = item.getItem();
        PreparedStatement flatInsert = connection.prepareStatement("UPDATE flat SET name=?, coordinated_id=?, creation_date=?, area=?, number_of_rooms=?, furnish=?::furnish, view=?::view, transport=?::transport, house_id=? WHERE id =?;");
        int coordinatesID = 0;
        try {
            coordinatesID = insertCoordinates(f.getCoordinates());
        }catch (SQLException e){

            coordinatesID = getCoordinatesID(f.getCoordinates());
        }
        int houseID = 0;
        try {
            houseID = insertHouse(f.getHouse());
        }catch (SQLException e){
            houseID = getHouseID(f.getHouse());
        }
        var userID = item.getOwnerLogin();
        flatInsert.setString(1,f.getName());
        flatInsert.setInt(2,coordinatesID);
        flatInsert.setTimestamp(3,Timestamp.from(f.getCreationDate().toInstant()));
        flatInsert.setInt(4,f.getArea());
        flatInsert.setLong(5,f.getNumberOfRooms());
        flatInsert.setObject(6,f.getFurnish().toString());
        flatInsert.setObject(7,f.getView().toString());
        flatInsert.setObject(8,f.getTransport().toString());
        flatInsert.setInt(9,houseID);
        flatInsert.setLong(10,ID);

        flatInsert.executeUpdate();
    }

    public int getHouseID(House h) throws SQLException {
        PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM house where name=? AND year=? AND number_of_floors=?");
        statement1.setString(1,h.getName());
        statement1.setInt(2,h.getYear());
        statement1.setInt(3,h.getNumberOfFloors());
        var r = statement1.executeQuery();
        r.next();
        if (r.getInt("id")==0) throw new SQLException();
        return r.getInt("id");
    }

    public void insertUser(AutentificationRequest authData) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users(user_login, user_password) VALUES (?,?)");
        var credentials = authData.getCredentials();
        statement.setString(1,credentials.getLogin());
        statement.setBytes(2, credentials.getPassword());
        statement.executeUpdate();
    }

    public int insertCoordinates(Coordinates c) throws SQLException {
        //TODO add my exceptionThrowing to all inserts
        PreparedStatement coordinatesInsert = connection.prepareStatement("INSERT INTO coordinates(x,y) VALUES (?,?) ON CONFLICT DO NOTHING",PreparedStatement.RETURN_GENERATED_KEYS);
        coordinatesInsert.setInt(1,c.getX());
        coordinatesInsert.setFloat(2,c.getY());
        coordinatesInsert.executeUpdate();
        var result = coordinatesInsert.getGeneratedKeys();
        result.next();
        if (result.getInt("id")==0) throw new SQLException();
        return result.getInt("id");
    }

    public int insertHouse(House h) throws SQLException {
        PreparedStatement houseInsert = connection.prepareStatement("INSERT INTO house(name, year, number_of_floors) VALUES (?,?,?) ON CONFLICT DO NOTHING",PreparedStatement.RETURN_GENERATED_KEYS);
        houseInsert.setString(1, h.getName());
        houseInsert.setInt(2,h.getYear());
        houseInsert.setInt(3,h.getNumberOfFloors());
        houseInsert.executeUpdate();
        var result = houseInsert.getGeneratedKeys();
        result.next();
        if (result.getInt("id")==0) throw new SQLException();
        return result.getInt("id");
    }

    public void insertFlat(CollectionItem item) throws SQLException {
        var f = item.getItem();
        PreparedStatement flatInsert = connection.prepareStatement("INSERT INTO flat(name, coordinated_id, creation_date, area, number_of_rooms, furnish, view, transport, house_id, owner) VALUES (?,?,?,?,?,?::furnish,?::view,?::transport,?,?)");
        int coordinatesID = 0;
        try {
            coordinatesID = insertCoordinates(f.getCoordinates());
        }catch (SQLException e){

            coordinatesID = getCoordinatesID(f.getCoordinates());
        }
        int houseID = 0;
        try {
            houseID = insertHouse(f.getHouse());
        }catch (SQLException e){
            houseID = getHouseID(f.getHouse());
        }
        var userID = item.getOwnerLogin();
        flatInsert.setString(1,f.getName());
        flatInsert.setInt(2,coordinatesID);
        flatInsert.setTimestamp(3,Timestamp.from(f.getCreationDate().toInstant()));
        flatInsert.setInt(4,f.getArea());
        flatInsert.setLong(5,f.getNumberOfRooms());
        flatInsert.setObject(6,f.getFurnish().toString());
        flatInsert.setObject(7,f.getView().toString());
        flatInsert.setObject(8,f.getTransport().toString());
        flatInsert.setInt(9,houseID);
        flatInsert.setInt(10,getUserID(item.getOwnerLogin()));

        flatInsert.executeUpdate();
    }

    public Coordinates readCoordinates(Integer ID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM coordinates WHERE id=?;");
        statement.setInt(1,ID);
        var set = statement.executeQuery();
        Coordinates c = new Coordinates();
        set.next();
        c.setX(set.getInt("x"));
        c.setY(set.getFloat("y"));
        return c;
    }

    public House readHouse(Integer ID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM house WHERE id=?;");
        statement.setInt(1,ID);
        var set = statement.executeQuery();
        set.next();
        House h = new House();
        h.setName(set.getString("name"));
        h.setYear(set.getInt("year"));
        h.setNumberOfFloors(set.getInt("number_of_floors"));
        return h;
    }

    public String getUserLogin(Integer ID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT user_login FROM users WHERE id=?");
        statement.setInt(1,ID);
        var s = statement.executeQuery();
        s.next();
        return s.getString("user_login");
    }


    public MyCollection loadCollection(){
        LinkedList<CollectionItem> list = null;
        long lastVal = 0;
        try {
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM flat");
            list = new LinkedList<>();
            while (resultSet.next()){
                Flat flat = new Flat();
                flat.setId(resultSet.getLong("ID"));
                flat.setName(resultSet.getString("name"));
                flat.setCreationDate(resultSet.getTimestamp("creation_Date").toInstant().atZone(ZoneId.systemDefault()));
                flat.setArea(resultSet.getInt("area"));
                flat.setNumberOfRooms(resultSet.getLong("number_of_rooms"));
                flat.setFurnish(Furnish.valueOf(resultSet.getString("furnish").toUpperCase().strip()));
                flat.setView(View.valueOf(resultSet.getString("view").toUpperCase().strip()));
                flat.setTransport(Transport.valueOf(resultSet.getString("transport").toUpperCase().strip()));


                flat.setCoordinates(readCoordinates(resultSet.getInt("coordinated_id")));
                flat.setHouse(readHouse(resultSet.getInt("house_id")));

                String userLogin =getUserLogin(resultSet.getInt("owner"));
                CollectionItem item = new CollectionItem(flat,userLogin);
                list.add(item);

            }
            var l = connection.createStatement().executeQuery("SELECT flat_id_seq.last_value FROM flat_id_seq;");
            l.next();
            lastVal =l.getInt("last_value");
        } catch (SQLException e) {
            logger.warning("Unable to load collection");
            throw new RuntimeException(e);
        }
        return new MyCollection(list,lastVal+1);
    }

    public void saveCollection(MyCollection collection){
        collection.getList().forEach(new Consumer<CollectionItem>() {
            @Override
            public void accept(CollectionItem collectionItem) {
                try {
                    insertFlat( collectionItem);
                } catch (SQLException e) {
                    logger.warning("Unable to save collection element with ID="+collectionItem.getItem().getId());
                }
            }
        });
    }

    public void removeByID(Long ID) throws SQLException {
        var deleteStatement = connection.prepareStatement("DELETE FROM flat WHERE id=?");
        deleteStatement.setInt(1,ID.intValue());
        deleteStatement.executeUpdate();
    }

    public void chechAuth(AutentificationRequest authData) throws NoAutentificationException, SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT user_password FROM users WHERE user_login=?");
        var credentials = authData.getCredentials();
        statement.setString(1,credentials.getLogin());
        var result = statement.executeQuery();
        result.next();
        byte[] passwd = result.getBytes("user_password");
        for (int i = 0; i < passwd.length; i++) {
            if (passwd[i]!=credentials.getPassword()[i]) throw new NoAutentificationException();
        }

    }

    //Methods for garbage collector
    protected Map<Integer,Integer> getCoordinatesIDs() throws SQLException {
        Statement statement = connection.createStatement();
        var res = statement.executeQuery("SELECT id FROM coordinates");
        Map<Integer,Integer>  IDs = new HashMap<>();
        while (res.next()){
            IDs.put(res.getInt(1),0);
        }
        return IDs;
    }

    protected Map<Integer,Integer> getHousesIDs() throws SQLException {
        Statement statement = connection.createStatement();
        var res = statement.executeQuery("SELECT id FROM house");
        Map<Integer,Integer> IDs = new HashMap<>();
        while (res.next()){
            IDs.put(res.getInt(1),0);
        }
        return IDs;
    }

    protected void fillCounts(Map<Integer,Integer> houses,
                                            Map<Integer,Integer> coordinates) throws SQLException {
        Statement statement = connection.createStatement();
        var res = statement.executeQuery("SELECT coordinated_id, house_id FROM flat");
        int courdinatesID = 0;
        int houseID = 0;
        while (res.next()){
            courdinatesID = res.getInt("coordinated_id");
            houseID = res.getInt("house_id");
            houses.put(houseID, houses.get(houseID)+1);
            coordinates.put(courdinatesID,coordinates.get(courdinatesID)+1);
        }
    }

    public void removeHouseByID(Integer key) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM house WHERE id=?");
        statement.setInt(1,key);
        statement.executeUpdate();
    }

    public void removeCoordinatesByID(Integer key) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM coordinates WHERE id=?");
        statement.setInt(1,key);
        statement.executeUpdate();
    }

    public void cleanDatabase(){
        GarbageCollector.getInstance().clean();
    }
}
