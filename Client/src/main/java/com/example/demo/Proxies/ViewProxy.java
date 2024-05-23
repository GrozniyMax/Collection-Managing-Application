package com.example.demo.Proxies;

import com.example.demo.CommonClasses.Entities.Furnish;
import com.example.demo.CommonClasses.Entities.View;

public enum ViewProxy implements BasicProxy<View> {
    STREET(View.STREET,"STREET"),
    YARD(View.YARD,"YARD"),
    PARK(View.PARK,"PARK"),
    BAD(View.BAD,"BAD"),
    GOOD(View.GOOD,"GOOD"),;

    String localName;
    View origin;


    ViewProxy(View origin, String localName) {
        this.origin = origin;
        this.localName = localName;
    }


    public View getOrigin() {
        return origin;
    }

    public static ViewProxy valueOf(View value) {
        for (ViewProxy proxy : ViewProxy.values()) {
            if (proxy.getOrigin().equals(value)) {
                return proxy;
            }
        }
        //IF NOT FOUND
        throw new IllegalArgumentException("No view proxy found for " + value);
    }

    public static ViewProxy valueOfLocalString(String localName){
        for(ViewProxy view : ViewProxy.values()){
            if(view.localName.equals(localName)){
                return view;
            }
        }
        throw new IllegalArgumentException("No Furnish found for localName " + localName);
    }


    @Override
    public String toString() {
        return this.localName;
    }

    public static void localize(){
        var r = STREET.loadResourceBundle();
        for (ViewProxy v : ViewProxy.values()) {
            v.localName = r.getString(v.name());
        }
    }


}
