package com.example.demo.Proxies.Localizers;

import com.example.demo.Managers.SessionContext;
import com.example.demo.Managers.UTF8Control;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainLocalizer {

    public static ResourceBundle getBundle(Class<?> clazz) {
        return ResourceBundle.getBundle(clazz.getCanonicalName(), SessionContext.getLanguage());
    }

    public static void localize(Class<?> clazz) {
        var r = getBundle(clazz);
        Arrays.stream(clazz.getDeclaredFields()).forEach(f -> {
            try {
                if (Modifier.isStatic(f.getModifiers())){
                    f.setAccessible(true);
                    f.set(null,r.getString(f.getName()));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (Exception e){
                System.out.println("Error in resource bundle");
                throw new RuntimeException(e);
            }


        });
    }
}
