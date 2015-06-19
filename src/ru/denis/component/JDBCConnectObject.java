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
public class JDBCConnectObject {
    
    private String name;
    private String link;

    public JDBCConnectObject(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getEncodeLink() {
        return encodeLink(link);
    }
    
    @Override
    public String toString() {
        return name;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
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
        final JDBCConnectObject other = (JDBCConnectObject) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    private String encodeLink(String link){
        
        // : -> \\:
        // . -> \\.       
        
        
        return link.replaceAll("\\.", "\\\\\\\\.").replaceAll("\\:", "\\\\\\\\:");
    }
    
}
