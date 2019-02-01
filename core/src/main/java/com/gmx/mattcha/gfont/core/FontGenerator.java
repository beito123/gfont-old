package com.gmx.mattcha.gfont.core;

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

    public static int CHAR_BASESIZE = 16;
    public static int BASESIZE = CHAR_BASESIZE * CHAR_BASESIZE;

    public FontData fdata;
    public FontOptions options;

    public BufferedImage workspace;

    public FontGenerator(FontData fdata, FontOptions options) {
        this.fdata = fdata;
        this.options = options;
    }

    public Map<Short, BufferedImage> generateFont(List<Short> ids) {
        Map<Short, BufferedImage> map = new LinkedHashMap<>();

        int width = BASESIZE * this.options.scale;
        int height = BASESIZE * this.options.scale;

        Map<Object, Object> hints = new HashMap<>();

        if (this.options.enableAntialiasing) {
            hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        }

        this.workspace = new BufferedImage(width / this.options.count, height / this.options.count, BufferedImage.TYPE_INT_ARGB);

        for(short i: ids) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D dst = img.createGraphics();

            this.drawGlyph(dst, i << 8, hints);

            dst.dispose();

            map.put(i, img);
        }

        return map;
    }

    public void drawGlyph(Graphics2D dst, int start, Map<?, ?> hints) {
        List<String> chars = GetChars(start, start + COUNT, " ");

        int size = CHAR_BASESIZE * this.options.scale;

        Graphics2D g2d = workspace.createGraphics();

        g2d.setColor(this.options.color);
        g2d.setBackground(this.options.background);

        if(hints != null) {
            g2d.setRenderingHints(hints);
        }

        for(int i = 0; i < chars.size(); i++) {
            char c = chars.get(i).charAt(0);

            int x = i % this.options.count;
            int y = i / this.options.count; //ceil

            g2d.clearRect(0, 0, size, size);

            int id = (int) c;
            if(this.options.imageFont.containsKey(id)) {
                dst.drawImage(this.options.imageFont.get(id), x * size, y * size, null);
            } else {
                this.drawChar(g2d, c, size);
                dst.drawImage(this.workspace, x * size, y * size, null);
            }

        }

        g2d.dispose();
    }

    public void drawChar(Graphics2D g2d, char c, int size) {
        Font font = this.fdata.getDisplayableFont(c, this.fdata.getLast());
        g2d.setFont(font);

        int baseline = this.fdata.getBaseline(font);

        FontMetrics metric = g2d.getFontMetrics();

        if(baseline == FontData.DEFAULT_BASELINE) {
            baseline = metric.getLeading() + metric.getAscent();
            this.fdata.setBaseline(font, baseline);
        }

        g2d.drawString(String.valueOf(c), ((size - metric.charWidth(c)) / 2), baseline);
    }

    public void drawImage(Graphics2D g2d, BufferedImage img) {
        g2d.drawImage(img, null, 0, 0);
    }

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
