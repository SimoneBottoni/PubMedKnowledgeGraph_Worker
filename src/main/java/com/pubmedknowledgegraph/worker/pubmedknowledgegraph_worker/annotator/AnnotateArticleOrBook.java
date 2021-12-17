package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.annotator;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.db.*;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.MMITag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class AnnotateArticleOrBook implements Callable<List<Tag>> {

    private final Logger logger = LogManager.getLogger("com.pubmedknowledgegraph.master.annotator");

    private final Annotate annotate;

    private final Map<String, Tag> tagMainList;
    private List<ArticleTag> articleTagList;
    private List<BookTag> bookTagList;

    private final List<Article> articleList;
    private final List<Book> bookList;

    private final String fileName;

    public AnnotateArticleOrBook(String fileName, Annotator annotator, List<Article> articleList, List<Book> bookList) {
        tagMainList = new HashMap<>();
        annotate = new Annotate(annotator);
        this.articleList = articleList;
        this.bookList = bookList;
        this.fileName = fileName;
    }

    @Override
    public List<Tag> call() {
        if (articleList != null) {
                annotateArticles();
        } else {
            annotateBooks();
        }
        return new ArrayList<>(tagMainList.values());
    }

    private void annotateArticles() {
        int count = 1;
        for (Article article : articleList) {
            logger.debug("Annotating " + fileName + " - Article PMID: " + article.getPmid() + "(" + count++ + "/" + articleList.size() + ")");
            articleTagList = new ArrayList<>();
            if (!article.getArticleTitle().equals("")) {
                createTag(annotate.annotate(article.getArticleTitle()), article, null, "article", "Title");
            }
            if (article.getAbstractText() != null) {
                if (!article.getAbstractText().equals("")) {
                    createTag(annotate.annotate(article.getAbstractText()), article, null, "article", "Abstract");
                }
            }
            article.setArticleTagList(articleTagList);
        }
    }

    private void annotateBooks() {
        int count = 1;
        for (Book book : bookList) {
            logger.debug("Annotating " + fileName + " - Book PMID: " + book.getPmid() + "(" + count++ + "/" + bookList.size() + ")");
            bookTagList = new ArrayList<>();
            if (!book.getArticleTitle().equals("")) {
                createTag(annotate.annotate(book.getBookTitle()), null, book, "book", "Title");
            }
            if (book.getAbstractText() != null) {
                if (!book.getAbstractText().equals("")) {
                    createTag(annotate.annotate(book.getAbstractText()), null, book, "book", "Abstract");
                }
            }
            book.setBookTagList(bookTagList);
        }
    }

    private void createTag(List<MMITag> mmiTagList, Article article, Book book, String type, String position) {
        for (MMITag mmiTag : mmiTagList) {
            Tag tag;
            if (tagMainList.containsKey(mmiTag.getCUI())) {
                tag = tagMainList.get(mmiTag.getCUI());
            } else {
                tag = new Tag(mmiTag.getCUI(), mmiTag.getPreferredName(), mmiTag.getSemanticType());
                tagMainList.put(tag.getCUI(), tag);
            }
            createArticleBookTag(mmiTag, tag, article, book, type, position);
        }
    }

    private void createArticleBookTag(MMITag mmiTag, Tag tag, Article article, Book book, String type, String position) {
        if (type.equals("article")) {
            if (mmiTag.getTreeCodes() != null) {
                articleTagList.add(new ArticleTag(article, tag, position, mmiTag.getScore(), mmiTag.getTriggerInfo(),
                        mmiTag.getPositionalInfo(), mmiTag.getTreeCodes()));
            } else {
                articleTagList.add(new ArticleTag(article, tag, position, mmiTag.getScore(), mmiTag.getTriggerInfo(),
                        mmiTag.getPositionalInfo()));
            }
        } else {
            if (mmiTag.getTreeCodes() != null) {
                bookTagList.add(new BookTag(book, tag, position, mmiTag.getScore(), mmiTag.getTriggerInfo(),
                        mmiTag.getPositionalInfo(), mmiTag.getTreeCodes()));
            } else {
                bookTagList.add(new BookTag(book, tag, position, mmiTag.getScore(), mmiTag.getTriggerInfo(),
                        mmiTag.getPositionalInfo()));
            }
        }
    }
}
