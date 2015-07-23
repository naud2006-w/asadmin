/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.component.winsettings;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author naumenko_ds
 */
public class ExtFileFilter extends FileFilter {

    String ext;
    String description;
    
    public ExtFileFilter(String ext, String description) {
        this.ext = ext;
        this.description = description;
    }    
    
    @Override
    public boolean accept(File f) {
        if(f != null) {
              if(f.isDirectory()) {
                  return true;
              }
              String extension = getExtension(f);
              if( extension == null )
                  return (ext.length() == 0);
              return ext.equals(extension);
          }
          return false;
    }

    @Override
    public String getDescription() {
        return description;
    }   

    public String getExtension(File f) {
        if(f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
                return filename.substring(i+1).toLowerCase();
            };
        }
        return null;
    }    
}
