/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.component;

/**
 *
 * @author naumenko_ds
 */
public class SchemaUsrObject {
    
    private String name;
    private String usr;
    private String pwd;

    public SchemaUsrObject(String name, String usr, String pwd) {
        this.name = name;
        this.usr = usr;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public String getUsr() {
        return usr;
    }

    public String getPwd() {
        return pwd;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
