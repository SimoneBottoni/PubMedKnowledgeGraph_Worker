package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.compositekeys;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Book;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class BookHistoryKey implements Serializable {

    private String status;
    private String date;

    @ManyToOne
    private Book book;

    public BookHistoryKey() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookHistoryKey that = (BookHistoryKey) o;

        if (!status.equals(that.status)) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
