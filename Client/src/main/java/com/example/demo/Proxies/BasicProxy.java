package com.example.demo.Proxies;

import com.example.demo.Managers.SessionContext;
import com.example.demo.Managers.UDPManager;
import com.example.demo.Managers.UTF8Control;

import java.util.ResourceBundle;

public interface BasicProxy<V>  {
    public default ResourceBundle loadResourceBundle() {
        return ResourceBundle.getBundle(this.getClass().getCanonicalName(), SessionContext.getLanguage());
    }

    public V getOrigin();


}