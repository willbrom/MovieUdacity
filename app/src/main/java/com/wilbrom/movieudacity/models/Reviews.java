package com.wilbrom.movieudacity.models;


import java.util.ArrayList;
import java.util.List;

public class Reviews {
    private int id;
    private int page;
    private int totalPages;
    private int totalResults;
    private List<ReviewResults> reviewsResults = new ArrayList<>();

    public class ReviewResults {
        private String id;
        private String author;
        private String content;
        private String url;

        public ReviewResults(String id, String author, String content, String url) {
            this.id = id;
            this.author = author;
            this.content = content;
            this.url = url;
        }

    }
}
