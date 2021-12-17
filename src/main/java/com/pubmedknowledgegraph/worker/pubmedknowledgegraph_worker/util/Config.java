package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Logger logger = LogManager.getRootLogger();

    public Config() {
    }

    public void initConfig() {
        configFolder();
        dbFile();
        configFile();
    }

    private void configFolder() {
        File folder = new File("./config");
        if (!createConfigFolder(folder)) {
            logger.info("Error creating config folder.");
            System.exit(1);
        }
    }

    private boolean createConfigFolder(File folder) {
        boolean check = true;
        if (!folder.exists()) {
            check = folder.mkdir();
        }
        return check;
    }

    private void configFile() {
        File file = new File("./config/config.properties");
        if (!file.exists()) {
            logger.info(file + " not found, creating..");
            createConfigFile(file);
        } else {
            checkConfigFile(file);
        }
    }

    private void dbFile() {
        File file = new File("./config/db.properties");
        if (!file.exists()) {
            logger.info(file + " not found, creating..");
            createDBFile(file);
        } else {
            checkDbFile(file);
        }
    }

    private void createConfigFile(File file) {
        try {
            InputStream inputStream = new ClassPathResource("config.templates").getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, new File(file.getPath()));
            IOUtils.closeQuietly(inputStream);
            logger.info(file + " created.");
        } catch (IOException e) {
            logger.info("Error creating config file.");
            System.exit(1);
        }
    }

    private void createDBFile(File file) {
        try {
            InputStream inputStream = new ClassPathResource("db.templates").getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, new File(file.getPath()));
            IOUtils.closeQuietly(inputStream);
            logger.info(file + " created.");
        } catch (IOException e) {
            logger.info("Error creating db file.");
            System.exit(1);
        }
    }

    private void checkDbFile(File file) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.info("Db file is not valid.");
            System.exit(1);
        }

        for (Object object : properties.keySet()) {
            switch (object.toString()) {
                case "spring.datasource.url":
                    String url = properties.getProperty(object.toString());
                    if (!url.matches("^jdbc:postgresql://\\b(?:(?:2(?:[0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9])\\.){3}(?:(?:2([0-4][0-9]|5[0-5])|[0-1]?[0-9]?[0-9]))\\b:[0-9]{4}/[a-zA-Z]+$")) {
                        logger.info("Db url is not valid.");
                        System.exit(1);
                    }
                    break;
                case "spring.datasource.username":
                case "spring.datasource.password":
                    break;
                case "spring.jpa.hibernate.ddl-auto":
                    String ddl = properties.getProperty(object.toString());
                    if (!ddl.equals("update")) {
                        logger.info(object + " can take the value update.");
                        System.exit(1);
                    }
                    break;
                case "spring.rabbitmq.host":
                case "spring.rabbitmq.port":
                case "spring.rabbitmq.username":
                case "spring.rabbitmq.password":
                    break;
                default:
                    logger.info("Properties " + object + " doesn't exists.");
                    System.exit(1);
            }
        }
    }

    private void checkConfigFile(File file) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.info("Config file is not valid.");
            System.exit(1);
        }

        for (Object object : properties.keySet()) {
            switch (object.toString()) {
                case "data.folder.baseline":
                    checkBaseLineFolder(properties, object);
                    break;
                case "data.folder.update":
                    File updateFolder = new File(properties.getProperty(object.toString()));
                    if (!updateFolder.exists()) {
                        updateFolder.mkdirs();
                    }
                    break;
                case "metamaplite.models":
                    checkMetaMapLiteFolder(properties, object, "models");
                    break;
                case "metamaplite.index":
                    checkMetaMapLiteFolder(properties, object, "index");
                    break;
                case "metamaplite.data.specialterms":
                    checkMetaMapLiteFile(properties, object, "specialterms.txt");
                    break;
                case "metamaplite.config":
                    checkMetaMapLiteFile(properties, object, "metamaplite.config");
                    break;
                default:
                    logger.info("Properties " + object + " doesn't exists.");
                    System.exit(1);
            }
        }
    }

    private void checkBaseLineFolder(Properties properties, Object object) {
        File baselineFolder = new File(properties.getProperty(object.toString()));
        if (!baselineFolder.exists()) {
            logger.info("Baseline folder doesn't exists, creating ...");
            baselineFolder.mkdirs();
        } else {
            checkBaseLineFolderContent(baselineFolder);
        }
    }

    private void checkBaseLineFolderContent(File baselineFolder) {
        File[] folderFiles = baselineFolder.listFiles();
        if (folderFiles != null) {
            for (File base : folderFiles) {
                checkBaseLineFolderContentFileName(base);
            }
        } else {
            logger.info("Baseline folder is empty.");
        }
    }

    private void checkBaseLineFolderContentFileName(File base) {
        if (!base.getName().matches("pubmed" + "\\d{2}" + "n" + "\\d{4}" + "(.xml|.xml.gz)")) {
            logger.info("Baseline folder contains invalid files.");
            System.exit(1);
        }
    }

    private void checkMetaMapLiteFolder(Properties properties, Object object, String folderName) {
        File models = new File(properties.getProperty(object.toString()));
        if (!models.exists()) {
            logger.info("Metamaplite " + folderName + " folder doesn't exists.");
            System.exit(1);
        } else {
            File[] folderFiles = models.listFiles();
            if (folderFiles == null) {
                logger.info("Metamaplite " + folderName + " folder is empty.");
                System.exit(1);
            }
        }
    }

    private void checkMetaMapLiteFile(Properties properties, Object object, String fileName) {
        File specialterms = new File(properties.getProperty(object.toString()));
        if (!specialterms.exists()) {
            logger.info("Metamaplite " + fileName + " file doesn't exists.");
            System.exit(1);
        }
    }

}
