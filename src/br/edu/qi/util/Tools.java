/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author Anderson Jorge
 */
public class Tools {

    public static Image iconImage = new Image("/br/edu/qi/resource/img.png");

    public static void Notification(String title, String msg, NotificationType type) {
        NotificationType notificationType = type;
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(msg);
        tray.setNotificationType(notificationType);
        tray.showAndDismiss(Duration.millis(3000));
    }

    private static SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

    public static Date toDate(DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            return null;
        }
        LocalDate ld = datePicker.getValue();
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        return date;
    }

    public static String space(int tamanho) {
        try {
            String retorno = "";
            for (int i = 0; i < tamanho; i++) {
                retorno += " "; //printa espaços
            }
            return retorno;
        } catch (Exception e) {
            return "";
        }
    }
    
    public static Date converterStringData(String srtData) throws Exception {
        Date data = null;
        data = formato.parse(srtData);
        return data;
    }
    
    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    
    public static LocalDate stringToLocalDate(String d) throws Exception {
        Date dt = converterStringData(d);
        Instant instant = Instant.ofEpochMilli(dt.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    public static int calcularDiferencaDatas(LocalDate i, LocalDate f){
    float dia = 0;
        if (f.isAfter(i)) {
            dia = ChronoUnit.DAYS.between(i, f); //Pega dias de diferença
        }
        return (int) dia;
    }
    
    //Period periodo = Period.between(Tools.stringToLocalDate(dInicial),(Tools.stringToLocalDate(dFinal)));
    
}
