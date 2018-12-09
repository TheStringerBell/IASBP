package com.example.soram.iasbp.db;

import com.example.soram.iasbp.db.model.Connections;

public class UpdateTable {

    public static Connections addConn(final GetDatabase db, Connections connections){
        db.testDao().insertAll(connections);
        return connections;
    }

    public static void populateDb(GetDatabase db, String host, String ip){
        Connections connections = new Connections();
        connections.setHostname(host);
        connections.setIp(ip);
        addConn(db, connections);


    }




}
