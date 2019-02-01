package com.gmx.mattcha.gfont.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainClass {

    public static void main(String []args) {
        GenerateBitmapFont();
    }

    public static void GenerateBitmapFont() {
        var fdata = new FontData();
        //fdata.addFont(new Font("美咲ゴシック", Font.BOLD, 26)); // For scale 2
        fdata.addFont(new Font("Noto CJK Regular", Font.PLAIN, 26));
        //fdata.addFont(new Font("Noto CJK Regular", Font.BOLD, 52)); // for scale 4

        var ignoreIDs = new ArrayList<Short>();
        ignoreIDs.add((short) 0xe0);

        var ids = new ArrayList<Short>();
        for(short i = 0; i < 0xff; i++) {
            if(ignoreIDs.contains(i)) {
                continue;
            }

            ids.add(i);
        }

        BufferedImage read = null;
        try{
            read = ImageIO.read(new File("./img/potato.png"));
        }catch(IOException e){
            //
        }

        FontOptions options = new FontOptions();
        options.scale = 2;
        options.enableAntialiasing = true;
        options.imageFont.put((int) '〇', read); //U+3007

        var out = new File("./font/");

        if (!out.exists()) {
            try {
                out.mkdirs();
            } catch (SecurityException e) {
                System.out.println(e);
            }
        }

        var gen = new FontGenerator(fdata, options);
        var fontMap = gen.generateFont(ids);
        for(Map.Entry<Short, BufferedImage> entry : fontMap.entrySet()) {
            String id = String.format("%02X", entry.getKey());

            File file = new File(out, "glyph_" + id + ".png");

            System.out.println("i: " + id + ", Save at " + file.getName());

            try{
                ImageIO.write(entry.getValue(), "png", file);
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }
}
