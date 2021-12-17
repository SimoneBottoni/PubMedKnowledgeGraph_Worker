package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.annotator;

import bioc.BioCDocument;
import gov.nih.nlm.nls.metamap.document.FreeText;
import gov.nih.nlm.nls.metamap.lite.resultformats.ResultFormatter;
import gov.nih.nlm.nls.metamap.lite.resultformats.ResultFormatterRegistry;
import gov.nih.nlm.nls.metamap.lite.types.Entity;
import gov.nih.nlm.nls.ner.MetaMapLite;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Annotator {

    private MetaMapLite metaMapLite;
    private ResultFormatter resultFormatter;

    private final String modelsFolder;
    private final String indexFolder;
    private final String specialterms;
    private final String configProperties;

    public Annotator(String modelsFolder, String indexFolder, String specialterms, String configProperties) {
        this.modelsFolder = modelsFolder;
        this.indexFolder = indexFolder;
        this.specialterms = specialterms;
        this.configProperties = configProperties;
        init();
    }

    private void init() {
        Properties properties = setMetaMapProperties(setProperties());
        try {
            metaMapLite = new MetaMapLite(properties);
        } catch (ClassNotFoundException | InstantiationException | NoSuchMethodException | IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
        resultFormatter = ResultFormatterRegistry.get("mmi");
        resultFormatter.initProperties(properties);
    }

    public String[] getTag(String pathText) throws Exception {
        if (pathText != null && !pathText.equals("")) {
            BioCDocument bioCDocument = readDocumentFromFreeText(pathText);
            List<Entity> entityList = metaMapLite.processDocument(bioCDocument);
            return getMMIFromEntities(entityList);
        }
        return null;
    }

    private Properties setProperties() {
        Properties properties = MetaMapLite.getDefaultConfiguration();
        MetaMapLite.expandModelsDir(properties, modelsFolder);
        MetaMapLite.expandIndexDir(properties, indexFolder);
        properties.setProperty("metamaplite.excluded.termsfile", specialterms);
        return properties;
    }

    private Properties setMetaMapProperties(Properties properties) {
        FileReader fr;
        try {
            fr = new FileReader(configProperties);
            properties.load(fr);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private BioCDocument readDocumentFromFreeText(String text) {
        return FreeText.instantiateBioCDocument(text);
    }

    private String[] getMMIFromEntities(List<Entity> entityList) {
        String mmiEntity = resultFormatter.entityListFormatToString(entityList);
        return mmiEntity.split("00000000.tx\\|MMI\\|");
    }

}
