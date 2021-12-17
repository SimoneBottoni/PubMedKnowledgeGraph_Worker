package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.handler.Handler;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.HandlerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class ParserService {

    private Handler handler;

    @Autowired
    public ParserService() {
    }

    public HandlerContainer parse(String path) {
        handler = new Handler();
        SAXParser saxParser = createParser();
        if (saxParser != null) {
            parseXML(saxParser, new File(path));
        }
        return handler.getHandlerOutput();
    }

    private SAXParser createParser() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            return saxParserFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseXML(SAXParser saxParser, File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            saxParser.parse(is, handler);
            //saxParser.parse(file, handler);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}
