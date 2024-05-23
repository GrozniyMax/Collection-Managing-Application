package com.example.demo.DataBase;

import com.example.demo.Main.Main;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

class GarbageCollector {
    static final Logger logger = Main.logger;

    static GarbageCollector instance;
    static GarbageCollector getInstance(){
        if (instance==null) instance = new GarbageCollector();
        return instance;
    }
    static final DBController connectionInstance = DBController.getInstance();


    private Map<Integer,Integer> housesIDs = new HashMap<>();
    private Map<Integer,Integer> coordinatesIDs = new HashMap<>();

    private void refreshIDs(){
        try {
            housesIDs = connectionInstance.getHousesIDs();
            coordinatesIDs = connectionInstance.getCoordinatesIDs();
        }catch (SQLException e){
            logger.warning("Unable to get IDs from coordinates/house. Please check if database is available");
        }
    }

     void clean(){
        try {
            refreshIDs();
            connectionInstance.fillCounts(housesIDs,coordinatesIDs);
            housesIDs.entrySet().stream().filter((integerIntegerEntry -> integerIntegerEntry.getValue()==0)).forEach((entry)-> {
                try {
                    connectionInstance.removeHouseByID(entry.getKey());
                } catch (SQLException e) {
                    logger.warning("Unable to remove row from house. Check if it is available");
                }
            });
            coordinatesIDs.entrySet().stream().filter(entry->entry.getValue()==0).forEach(entry-> {
                try {
                    connectionInstance.removeCoordinatesByID(entry.getKey());
                } catch (SQLException e) {
                    logger.warning("Unable to remove row from house. Check if it is available");
                }
            });
        } catch (SQLException e) {
            logger.warning("Unable to do requests to database. Please check if it is available");
        }

    }
}
