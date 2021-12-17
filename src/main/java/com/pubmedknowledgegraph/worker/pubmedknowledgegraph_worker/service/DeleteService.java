package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.service;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.DeleteCitation;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.ArticleRepository;
import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class DeleteService {

    private final ArticleRepository articleRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DeleteService(ArticleRepository articleRepository, BookRepository bookRepository) {
        this.articleRepository = articleRepository;
        this.bookRepository = bookRepository;
    }

    public void deleteArticlesOrBooks(List<DeleteCitation> deleteCitationList) {
        delete(deleteCitationList);
    }

    private void delete(List<DeleteCitation> deleteCitationList) {
        for (DeleteCitation deleteCitation : deleteCitationList) {
            updateCancellationDate(deleteCitation);
        }
    }

    private void updateCancellationDate(DeleteCitation deleteCitation) {
        if (!articleRepository.getPmidByPmidAndVersion(deleteCitation.getPmid(), deleteCitation.getVersion()).isEmpty()) {
            articleRepository.updateCancellationDate(getTodayTimeStamp(), deleteCitation.getPmid(), deleteCitation.getVersion());
        }
        if (!bookRepository.getPmidByPmidAndVersion(deleteCitation.getPmid(), deleteCitation.getVersion()).isEmpty()) {
            bookRepository.updateCancellationDate(getTodayTimeStamp(), deleteCitation.getPmid(), deleteCitation.getVersion());
        }
    }

    private String getTodayTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(timestamp);
    }

}
