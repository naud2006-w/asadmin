/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.denis.utilits;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author naumenko_ds
 */
public class AppConstants {
    
    // Список доступных команд
    public static final String[] listCommand = new String[]{
                    "*Установка переменных -JVMOptions",
                    "Стоп сервера",
                    "Старт серврера",
                    "Рестарт сервера",
                    "Опции сервера (jvm-options-list)",
                    "Состояние домена(ов)",
                    "Удаление приложения",
                };    

    public AppConstants() {        
               
    }
    
    public static final String APP_NAME = "asadminj";
    
    public static final String asadminbat = "Файл для запуска glassfish asadmin.bat";
    
    public static final String pathglassfish = "Каталог установки Glassfish";
    
    public static final String defadmpasswordfile = "Файл с паролем по умолчанию";
    
    public static final String app_name_gkh = "название приложения на сервере для удаления";
    
    public static final String ASADMIN_FILE_NAME = "\\bin\\asadmin.bat";
    
    
    public static final String sn_pathglassfish = "pathglassfish";
    public static final String sn_defadmpasswordfile = "defaultadminpass";
    public static final String sn_app_name_gkh = "web";
}
