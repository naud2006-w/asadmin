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
public class AppConst {
    
    // Список доступных команд
    public static final String[] listCommand = new String[]{
                    "Запуск сервера с переменными",
                    "Стоп сервера",
                    "Старт серврера",
                    "Рестарт сервера",
                    "Опции сервера (jvm-options-list)"
                };    

    public AppConst() {        
               
    }
    
    public static final String asadminbat = "Файл для запуска glassfish asadmin.bat";
    
    public static final String defadmpasswordfile = "Файл с паролем по умолчанию";
}
