/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Anderson Jorge
 */
public class TratamentoImagem {

    // Converte BufferedImage to ImageView 
    public static Image decodeToImage(String imageString) {
        Image i = null;
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            i = SwingFXUtils.toFXImage(image, null);
            //imgLogo.setImage(i);                
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
