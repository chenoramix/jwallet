package org.czen;

import java.awt.*;
import java.io.*;
import java.util.Iterator;
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
        boolean windowHeight = false;
        boolean windowWidth = false;
        boolean windowPositionX = false;
        boolean windowPositionY = false;

        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = xmlInputFactory.createXMLEventReader(new FileReader(configFilename));

            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch(event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        if(qName.equalsIgnoreCase("database")) continue;

                        Iterator<Attribute> attributes = startElement.getAttributes();
                        qName = attributes.next().getValue();

                        if(qName.equalsIgnoreCase("windowHeight")) {
                            windowHeight = true;
                        } else if(qName.equalsIgnoreCase("windowWidth")) {
                            windowWidth = true;
                        } else if(qName.equalsIgnoreCase("windowPositionX")) {
                            windowPositionX = true;
                        } else if(qName.equalsIgnoreCase("windowPositionY")) {
                            windowPositionY = true;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();

                        if(windowHeight) {
                            windowHeight = false;
                            this.windowHeight = Integer.parseInt(characters.getData());
                        } else if(windowWidth) {
                            windowWidth = false;
                            this.windowWidth = Integer.parseInt(characters.getData());
                        } else if(windowPositionX) {
                            windowPositionX = false;
                            this.windowPositionX = Integer.parseInt(characters.getData());
                        } else if (windowPositionY) {
                            windowPositionY = false;
                            this.windowPositionY = Integer.parseInt(characters.getData());
                        }
                        break;
                }
            }

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

            try (FileOutputStream fos = new FileOutputStream(configFilename)) {
                byte[] xmlBytes = xmlString.getBytes();
                fos.write(xmlBytes);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
