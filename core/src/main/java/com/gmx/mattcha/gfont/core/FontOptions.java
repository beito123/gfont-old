package com.gmx.mattcha.gfont.core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FontOptions {
    public int pixel = 16;
    public int scale = 1;
    public int count = 16;
    public Color color = Color.WHITE;
    public Color background = new Color(0, 0, 0, 0);
    public boolean enableAntialiasing;
    public Map<Integer, BufferedImage> imageFont = new HashMap<>();
}
