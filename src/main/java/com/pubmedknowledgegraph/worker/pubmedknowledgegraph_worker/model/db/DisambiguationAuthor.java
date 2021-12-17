package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.compositekeys.DisambiguationKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "disambiguation_author")
public class DisambiguationAuthor implements Serializable {

    @EmbeddedId
    private DisambiguationKey disambiguationKey;
    private String Source;
    private String lastname;
    private String firstname;
    private String name;

    public DisambiguationKey getDisambiguationKey() {
        return disambiguationKey;
    }

    public void setDisambiguationKey(DisambiguationKey disambiguationKey) {
        this.disambiguationKey = disambiguationKey;
    }

    public DisambiguationAuthor() { }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
