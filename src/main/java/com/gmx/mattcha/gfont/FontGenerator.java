package com.gmx.mattcha.gfont;

/*
	gfont

	Copyright (c) 2019 beito

	This software is released under the MIT License.
	http://opensource.org/licenses/mit-license.php
*/

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

public class FontGenerator {
    public static int COUNT = 16 * 16;


    public static Map<Short, BufferedImage> generateFont(FontData fdata, int scale, List<Short> ids, FontOptions options) {
        Map<Short, BufferedImage> map = new LinkedHashMap<>();

        int width = 256 * scale;
        int height = 256 * scale;

        Map<Object, Object> hints = new HashMap<>();

        if (options.enableAntialiasing) {
            hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }

        BufferedImage workspace = new BufferedImage(width / 16, height / 16, BufferedImage.TYPE_INT_ARGB);

        for(short i: ids) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D dst = img.createGraphics();

            drawGlyph(fdata, dst, workspace, i << 8, hints);

            dst.dispose();

            map.put(i, img);
        }

        return map;
    }

    public static void drawGlyph(FontData fdata, Graphics2D dst, BufferedImage work, int start) {
        drawGlyph(fdata, dst, work, start, null);
    }

    public static void drawGlyph(FontData fdata, Graphics2D dst, BufferedImage work, int start, Map<?, ?> hints) {
        List<String> chars = GetChars(start, start + COUNT, " ");

        int size = 32;

        Graphics2D g2d = work.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.setBackground(new Color(0, 0, 0, 0));

        if(hints != null) {
            g2d.setRenderingHints(hints);
        }

        for(int i = 0; i < chars.size(); i++) {
            char c = chars.get(i).charAt(0);

            int x = i % 16;
            int y = i / 16; //ceil

            g2d.clearRect(0, 0, size, size);

            drawChar(g2d, fdata, c, size);

            dst.drawImage(work, x * size, y * size, null);
        }

        g2d.dispose();
    }

    public static void drawChar(Graphics2D g2d, FontData fdata, char c, int size) {
        Font font = fdata.getDisplayableFont(c, fdata.getLast());
        g2d.setFont(font);

        int baseline = fdata.getBaseline(font);

        FontMetrics metric = g2d.getFontMetrics();

        if(baseline == FontData.DEFAULT_BASELINE) {
            baseline = metric.getLeading() + metric.getAscent();
            fdata.setBaseline(font, baseline);
        }

        g2d.drawString(String.valueOf(c), ((size - metric.charWidth(c)) / 2), baseline);
    }

    /*public List<Image> generateFont(File out) {
        // TODO: implement to range

        List<Image> list = new ArrayList<>();

        int width = 512;
        int height = 512;

        for (int i = 0; i < 0xffff; i += 256) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            this.workspace = new BufferedImage(width / 16, height / 16, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = img.createGraphics();

            if (this.enableAntialiasing) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            this.drawGlyph(g2d, i);

            g2d.dispose();

            String id = String.format("%02X", (i >> 8) & 0xff); // ABCD -> AB

            File file = new File(out, "glyph_" + id + ".png");

            System.out.println("i: " + i + ", Save at " + file.getName());

            try{
                ImageIO.write(img, "png", file);
            }catch(IOException e){
                System.out.println(e);
            }
        }

        return list;
    }*/

    /*
    public void drawGlyph(Graphics2D g2d, int start) {
        List<String> chars = GetChars(start, start + COUNT, " ");

        for(int i = 0; i < chars.size(); i++) {
            char c = chars.get(i).charAt(0);

            int x = i % 16;
            int y = i / 16; //ceil

            this.drawChar(this.workspace, c, x, y, 32);
        }
    }

    public void drawChar(Graphics2D g2d, char c, int x, int y, int size) {
        g2d.setBackground(new Color(0, 0, 0, 0));
        g2d.clearRect(x * size, y * size, size, size);

        // Fun
        //g2d.setColor(new Color(x * 8, y * 8, x * y, 0xff));
        //Random rand = new Random();
        //g2d.setColor(new Color(y * 16, y * 16, y * 16, 0xff));

        g2d.setColor(Color.WHITE);

        FontData fdata;
        if(this.font.canDisplay(c)) {
            fdata = this.font;
        } else {
            fdata = this.subfont;
        }

        g2d.setFont(fdata.getFont());

        FontMetrics metric = g2d.getFontMetrics();

        if(fdata.getBaseline() == FontData.DEFAULT_BASELINE) {
            fdata.setBaseline(metric.getLeading() + metric.getAscent());
        }

        g2d.drawString(String.valueOf(c), (x * size) + ((size - metric.charWidth(c)) / 2), (y * size) + fdata.getBaseline());
    }*/

    public static List<String> GetChars(int start, int end, String empty) {
        List<String> chars = new ArrayList<>();

        // Thanks from http://helpdesk.objects.com.au/java/how-to-list-all-unicode-characters
        for (int i = start; i < end; i++) {
            if (Character.isDefined(i)) {
                chars.add(new String(Character.toChars(i)));
            } else {
                chars.add(empty);
            }
        }

        return chars;
    }
}
