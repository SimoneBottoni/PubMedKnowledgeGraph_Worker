package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.annotator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class RestAnnotator {

    private WebTarget webTarget;
    private MultivaluedHashMap<String, String> multivaluedHashMap;

    public RestAnnotator() {
        init();
    }

    public void init() {
        URI uri = UriBuilder.fromUri("https://ii-public1.nlm.nih.gov/metamaplite/rest/annotate").build();
        Client client = ClientBuilder.newClient();
        webTarget = client.target(uri);
    }

    public String[] getTag(String pathText) {
        if (pathText != null && !pathText.equals("")) {
            return request(pathText);
        }
        return null;
    }

    private String[] request(String textToAnnotate) {
        setnewMultiValuedMap();
        multivaluedHashMap.add("inputtext", textToAnnotate + "\\n");
        return getResponseText(sendRequest().readEntity(String.class)).split("\n");
    }

    private Response sendRequest() {
        return webTarget.request()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(Entity.form(multivaluedHashMap));
    }

    private void setnewMultiValuedMap() {
        multivaluedHashMap = new MultivaluedHashMap<>();
        multivaluedHashMap.add("docformat", "freetext");
        multivaluedHashMap.add("resultformat", "mmi");
    }

    private String getResponseText(String mmi) {
        Document document = Jsoup.parse(mmi);
        return document.getElementsByTag("pre").text();
    }

}
