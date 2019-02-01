package com.gmx.mattcha.gfont.desktop;

import javax.swing.*;
import java.awt.*;

public class MainClass {

    public static void main(String args[]) {
        // Change UI style for Windows like
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }

        JFrame form = new JFrame("gfont");
        form.setBounds(0 , 0 , 400 , 200);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] list = {"potato", "carrot", "beef", "momo", "tanuki", "ninja"};

        var flist = new JList(list);

        JScrollPane sp = new JScrollPane();
        sp.getViewport().setView(flist);
        sp.setPreferredSize(new Dimension(200, 80));

        JPanel p1 = new JPanel();
        p1.add(sp);

        JButton addButton = new JButton();
        addButton.setText("Add font");

        JButton removeButton = new JButton();
        removeButton.setText("Remove font");

        JPanel p2 = new JPanel();
        p2.add(addButton);
        p2.add(removeButton);
        p2.setBounds(0, 0, 100, 200);


        Container p = form.getContentPane();
        p.setLayout(null);
        p.add(p1);
        p.add(p2);

        form.setVisible(true);

    }
}
