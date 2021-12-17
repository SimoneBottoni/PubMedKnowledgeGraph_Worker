package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@SQLInsert(sql="INSERT INTO publication_type (name, ui) values (?, ?) ON CONFLICT DO NOTHING")
public class PublicationType implements Persistable<String> {

    @Id
    private String UI;

    private String name;

    public PublicationType() {
    }

    public String getUI() {
        return UI;
    }

    public void setUI(String UI) {
        this.UI = UI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    private boolean isNew = true;

    @JsonIgnore
    @Override
    public String getId() {
        return UI;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
