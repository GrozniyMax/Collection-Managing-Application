package com.example.demo.Proxies;

import com.example.demo.CommonClasses.Entities.Furnish;
import com.example.demo.CommonClasses.Entities.Transport;

public enum TransportProxy implements BasicProxy<Transport> {
    NONE(Transport.NONE,"NONE"),
    LITTLE(Transport.LITTLE,"LITTLE"),
    NORMAL(Transport.NORMAL,"NORMAL"),
    ENOUGH(Transport.ENOUGH,"ENOUGH");

    String localName;
    Transport origin;


    TransportProxy(Transport origin, String localName) {
        this.origin = origin;
        this.localName = localName;
    }


    public Transport getOrigin() {
        return origin;
    }


    public static TransportProxy valueOf(Transport value) {
        for (TransportProxy proxy : TransportProxy.values()) {
            if (proxy.getOrigin().equals(value)) {
                return proxy;
            }
        }
        //IF NOT FOUND
        throw new IllegalArgumentException("No view proxy found for " + value);
    }

    public static TransportProxy valueOfLocalString(String localName){
        for(TransportProxy transportProxy : TransportProxy.values()){
            if(transportProxy.localName.equals(localName)){
                return transportProxy;
            }
        }
        throw new IllegalArgumentException("No Furnish found for localName " + localName);
    }

    @Override
    public String toString() {
        return this.localName;
    }

    public static void localize(){
        var r = NONE.loadResourceBundle();
        for (TransportProxy v : TransportProxy.values()) {
            v.localName = r.getString(v.name());
        }
    }
}
