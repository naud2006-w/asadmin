/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.component;

import java.util.Objects;

/**
 *
 * @author naumenko_ds
 */
public class DomainGFObject {

    private String name;
    private Integer port;

    public DomainGFObject(String name, Integer port) {
        this.name = name;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public Integer getPort() {
        return port;
    }

    @Override
    public String toString() {
        return name + " : " + port;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.port);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DomainGFObject other = (DomainGFObject) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.port, other.port)) {
            return false;
        }
        return true;
    }
    
    
    
}
