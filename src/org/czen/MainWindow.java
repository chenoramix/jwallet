package org.czen;

import java.awt.*;
import java.io.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

public class MainWindow {
    private final String configFilename;
    private int windowHeight;
    private int windowWidth;
    private int windowPositionX;
    private int windowPositionY;

    public MainWindow() {
        configFilename = "jwallet.xml";
        File confFile = new File(configFilename);

        if(!confFile.exists()) {
            windowHeight = 500;
            windowWidth = 400;
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            windowPositionX = dim.width / 2 - windowWidth / 2;
            windowPositionY = dim.height / 2 - windowHeight / 2;

            createConfigFile();
        }

        loadConfig();
    }

    // try to load the xml
    private void loadConfig() {

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }

    // create initial config with size and position
    private void createConfigFile() {
        try {
            StringWriter stringWriter = new StringWriter();
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(stringWriter);

            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeStartElement("database");

            xmlStreamWriter.writeStartElement("window");
            xmlStreamWriter.writeAttribute("type", "windowHeight");
            xmlStreamWriter.writeCharacters(String.valueOf(windowHeight));
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("window");
            xmlStreamWriter.writeAttribute("type", "windowWidth");
            xmlStreamWriter.writeCharacters(String.valueOf(windowWidth));
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("window");
            xmlStreamWriter.writeAttribute("type", "windowPositionX");
            xmlStreamWriter.writeCharacters(String.valueOf(windowPositionX));
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("window");
            xmlStreamWriter.writeAttribute("type", "windowPositionY");
            xmlStreamWriter.writeCharacters(String.valueOf(windowPositionY));
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeEndDocument();

            xmlStreamWriter.flush();
            xmlStreamWriter.close();

            String xmlString = stringWriter.getBuffer().toString();

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(configFilename);
                byte[] xmlBytes = xmlString.getBytes();
                fos.write(xmlBytes);
            } finally {
                if(fos != null) {
                    fos.close();
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }

    }
}
