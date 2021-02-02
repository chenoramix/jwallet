package org.czen;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class MainWindow extends JFrame {
    private final String configFilename;
    private CreateLoadConfig configFile;

    public MainWindow() {
        configFilename = "jwallet.xml";
        configFile = new CreateLoadConfig(configFilename);
        File confFile = new File(configFilename);

        if(!confFile.exists()) {
            int windowHeight = 500;
            int windowWidth = 400;

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int windowPositionX = dim.width / 2 - windowWidth / 2;
            int windowPositionY = dim.height / 2 - windowHeight / 2;

            configFile.setWindowSize(windowHeight, windowWidth);
            configFile.setWindowPosition(windowPositionX, windowPositionY);
            configFile.createConfigFile();
        }

        configFile.loadConfig();
    }
}
