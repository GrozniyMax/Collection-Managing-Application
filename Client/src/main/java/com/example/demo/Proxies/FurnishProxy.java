package com.example.demo.Proxies;

import com.example.demo.CommonClasses.Entities.Furnish;

public enum FurnishProxy implements BasicProxy<Furnish> {
    DESIGNER(Furnish.DESIGNER,"DESIGNER"),
    NONE(Furnish.NONE,"NONE"),
    LITTLE(Furnish.LITTLE,"LITTLE");

    String localName;
    Furnish origin;


    FurnishProxy(Furnish origin, String localName) {
        this.origin = origin;
        this.localName = localName;
    }




    public Furnish getOrigin() {
        if (origin==null) throw new IllegalArgumentException("GOT NULL"+this);
        return origin;
    }


    public static FurnishProxy valueOfLocalString(String localName){
        for(FurnishProxy furnish : FurnishProxy.values()){
            if(furnish.localName.equals(localName)){
                return furnish;
            }
        }
        throw new IllegalArgumentException("No Furnish found for localName " + localName);
    }


    public static FurnishProxy valueOf(Furnish value) {
        for (FurnishProxy proxy : FurnishProxy.values()) {
            if (proxy.getOrigin().equals(value)) {
                return proxy;
            }
        }
        //IF NOT FOUND
        throw new IllegalArgumentException("No Furnish proxy found for " + value);
    }

    @Override
    public String toString() {
        return this.localName;
    }

    public static void localize(){
        var r = NONE.loadResourceBundle();
        for (FurnishProxy v : FurnishProxy.values()) {
            v.localName = r.getString(v.name());
        }
    }

}
