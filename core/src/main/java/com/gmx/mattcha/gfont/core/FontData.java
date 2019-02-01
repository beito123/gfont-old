package com.gmx.mattcha.gfont.core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FontData {
    public static int DEFAULT_BASELINE = -1;

    public List<Data> fonts;

    public FontData() {
        this(new ArrayList<>());
    }

    public FontData(List<Data> fonts) {
        this.fonts = fonts;
    }

    public int addFont(Font font) {
        this.fonts.add(new Data(font, DEFAULT_BASELINE));

        return this.fonts.size() - 1; // returns index
    }

    public int addFont(Font font, int baseline) {
        this.fonts.add(new Data(font, baseline));

        return this.fonts.size() - 1; // returns index
    }

    protected Data getFontData(int i) {
        if(i > this.fonts.size()-1) {
            return null;
        }

        return this.fonts.get(i);
    }

    protected Data getFontData(Font font) {
        for(Data data : this.fonts) {
            if(data.getFont().equals(font)) {
                return data;
            }
        }

        return null;
    }

    public Font getFont(int i) {
        return this.getFontData(i).getFont();
    }

    public Font getFirst() {
        return (this.fonts.size() != 0) ? this.getFontData(this.fonts.size() - 1).getFont(): null;
    }

    public Font getLast() {
        return (this.fonts.size() != 0) ? this.getFontData(0).getFont(): null;
    }

    public int getBaseline(int i) {
        return this.getFontData(i).getBaseline();
    }

    public int getBaseline(Font font) {
        return this.getFontData(font).getBaseline();
    }

    public void setBaseline(int i, int baseline) {
        this.getFontData(i).setBaseline(baseline);
    }

    public void setBaseline(Font font, int baseline) {
        this.getFontData(font).setBaseline(baseline);
    }

    public boolean canDisplay(int i, char c) {
        return this.getFontData(i).canDisplay(c);
    }

    public Font getDisplayableFont(char c) {
        return this.getDisplayableFont(c, null);
    }

    public Font getDisplayableFont(char c, Font def) {
        for(Data data : this.fonts) {
            if(data.canDisplay(c)) {
                return data.getFont();
            }
        }

        return def;
    }

    public class Data {

        private Font font;
        private int baseline;

        public Data(Font font, int baseline) {
            this.font = font;
            this.baseline = baseline;
        }

        public Font getFont() {
            return font;
        }

        public int getBaseline() {
            return baseline;
        }

        public void setBaseline(int baseline) {
            this.baseline = baseline;
        }

        public boolean canDisplay(char c) {
            return this.font.canDisplay(c);
        }
    }
}
