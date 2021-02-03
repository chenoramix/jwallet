package org.czen;

import javax.swing.*;
import java.awt.*;

public class CreateDatabase extends JFrame {

    public CreateDatabase() {
        int windowWidth = 600;
        int windowHeight = 400;

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int windowPositionX = dim.width / 2 - windowWidth / 2;
        int windowPositionY = dim.height / 2 - windowHeight / 2;


        this.setTitle("Create database file");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
        this.setLocation(windowPositionX, windowPositionY);
        this.setLayout(null);
        this.setVisible(true);
    }

}
