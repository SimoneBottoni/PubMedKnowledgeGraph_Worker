package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.compositekeys;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.Book;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class IsbnKey implements Serializable {

    private String isbn;

    @ManyToOne
    private Book book;

    public IsbnKey() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

        IsbnKey isbnKey = (IsbnKey) o;

        return isbn.equals(isbnKey.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
