package com.gmx.mattcha.gfont;

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
        fdata.addFont(new Font("美咲ゴシック", Font.BOLD, 26));
        fdata.addFont(new Font("Noto CJK Regular", Font.BOLD, 26));

        var scale = 2;

        var ids = new ArrayList<Short>();
        for(short i = 0; i < 0xff; i++) {
            ids.add(i);
        }

        FontOptions options = new FontOptions();
        options.enableAntialiasing = true;

        var out = new File("./font/");

        if (!out.exists()) {
            try {
                out.mkdirs();
            } catch (SecurityException e) {
                System.out.println(e);
            }
        }

        var fontMap = FontGenerator.generateFont(fdata, scale, ids, options);
        for(Map.Entry<Short, BufferedImage> entry : fontMap.entrySet()) {
            String id = String.format("%02X", entry.getKey()); // ABCD -> AB

            File file = new File(out, "glyph_" + id + ".png");

            System.out.println("i: " + id + ", Save at " + file.getName());

            try{
                ImageIO.write(entry.getValue(), "png", file);
            }catch(IOException e){
                System.out.println(e);
            }
        }

        /*

        FontData font = new FontData(new Font("美咲ゴシック", Font.BOLD, 26), FontData.DEFAULT_BASELINE);
        FontData subfont = new FontData(new Font("Noto CJK Regular", Font.BOLD, 26), FontData.DEFAULT_BASELINE);

        FontGenerator generator = new FontGenerator(font, subfont);

        generator.setEnableAntialiasing(true);

        generator.generateFont(out);

        */

        /*
        int width = 256;
        int height = 256;

        File out = new File("./font/");

        if (!out.exists()) {
            try {
                out.mkdirs();
            } catch (SecurityException e) {
                System.out.println(e);
            }
        }

        Font font = new Font("Nico Moji", Font.PLAIN, 16);
        Font subfont = new Font("Noto CJK Black", Font.PLAIN, 16);

        int charWidth = width / 16; // x
        int charHeight = width / 16; // y baseline

        for (int i = 0; i < 0xffff; i += 256) {
            List<String> chars = GetChars(i, i + 256);

            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = img.createGraphics();

            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int j = 0; j < chars.size(); j++) {
                String c = chars.get(j);
                int diffX = (j % 16);
                int diffY = (j / 16) + 1;

                g2d.clearRect(charWidth * diffX, charHeight * diffY, 16, 16);

                if (!font.canDisplay(c.charAt(0))) {
                    g2d.setFont(subfont);
                }

                g2d.drawString(c, charWidth * diffX, charHeight * diffY);

                if (font.canDisplay(c.charAt(0))) {
                    g2d.setFont(font);
                }
            }

            String id = String.format("%02X", (i >> 8) & 0xff); // ABCD -> AB

            File file = new File(out, "glyph_" + id + ".png");

            System.out.println("i: " + i + ", Save at " + file.getName());

            try{
                ImageIO.write(img, "png", file);
            }catch(IOException e){
                System.out.println(e);
            }
        }*/
    }
}
