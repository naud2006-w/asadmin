/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.asadmin;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import ru.denis.command.CommandBean;
import ru.denis.db.DataBaseUtils;
import ru.denis.utilits.AppConstants;

/**
 *
 * @author naumenko_ds
 */
public class Asadmin {
    
    private static TrayIcon trayIcon;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.print(System.getProperty("user.dir"));
        
        try {
            // готовим рабочие директории
            CommandBean.initWorkFolder();
            
            DataBaseUtils du = DataBaseUtils.getInstance();            
            
            final MainWindow mw = new MainWindow();
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mw.setVisible(true);                
                }
            });

            setTrayIcon(mw);        
            
        } catch (Exception ex) {
            Logger.getLogger(Asadmin.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    
    // помещаем в трей значек
    private static void setTrayIcon(final MainWindow mw) {
        if(! SystemTray.isSupported() ) {
          return;
        }

        PopupMenu trayMenu = new PopupMenu();
        
        MenuItem itemCTM = new MenuItem("Время");
        itemCTM.addActionListener(new ActionListener(){            
            
            @Override
            public void actionPerformed(ActionEvent e) {
                long ctm = System.currentTimeMillis();
                                
                mw.setTextCustom("System.currentTimeMils(): " + ctm); 
            }
        });
        
        trayMenu.add(itemCTM);        
        trayMenu.insertSeparator(1);
        
        MenuItem item = new MenuItem("Выйти");
        item.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            System.exit(0);
          }
        });
        trayMenu.add(item);

        URL imageURL = Asadmin.class.getResource("images/gf.png");//("images/calendar.png");

        ImageIcon icon = new ImageIcon(imageURL);
        
        
        trayIcon = new TrayIcon(icon.getImage(), AppConstants.APP_NAME, trayMenu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        try {
          tray.add(trayIcon);
        } catch (AWTException e) {
          e.printStackTrace();
        }

        trayIcon.displayMessage(AppConstants.APP_NAME, "Программа запущенна!",
                                TrayIcon.MessageType.INFO);
    }

    public static TrayIcon getTrayIcon() {
        return trayIcon;
    }
    
    
}
