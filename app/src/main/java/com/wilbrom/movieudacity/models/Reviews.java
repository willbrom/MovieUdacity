package com.wilbrom.movieudacity.models;


import java.util.ArrayList;
import java.util.List;

public class Reviews {
    private int id;
    private int page;
    private int totalPages;
    private int totalResults;
    private List<ReviewResults> reviewsResults = new ArrayList<>();

    public List<ReviewResults> getReviewsResults() {
        return reviewsResults;
    }

    public void setReviewsResults(String id, String author, String content, String url) {
        this.reviewsResults.add(new ReviewResults(id, author, content, url));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public class ReviewResults {
        private String id;
        private String author;
        private String content;
        private String url;

        ReviewResults(String id, String author, String content, String url) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getUrl() {
            return url;
        }
    }
}
