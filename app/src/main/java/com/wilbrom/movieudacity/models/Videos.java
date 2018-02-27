package com.wilbrom.movieudacity.models;


import java.util.ArrayList;
import java.util.List;

public class Videos {
    private int id;
    private List<VideoResults> videoResultsList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<VideoResults> getVideoResultsList() {
        return videoResultsList;
    }

    public void addVideoResultsListItem(String id, String iso_639_1, String iso_3166_1, String key, String name, String site, int size, String type) {
        this.videoResultsList.add(new VideoResults(id, iso_639_1, iso_3166_1, key, name, site, size, type));
    }

    public class VideoResults {
        private String id;
        private String iso_639_1;
        private String iso_3166_1;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;

        public VideoResults(String id, String iso_639_1, String iso_3166_1, String key, String name, String site, int size, String type) {
            this.id = id;
            this.iso_639_1 = iso_639_1;
            this.iso_3166_1 = iso_3166_1;
            this.key = key;
            this.name = name;
            this.site = site;
            this.size = size;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getSite() {
            return site;
        }

        public int getSize() {
            return size;
        }

        public String getType() {
            return type;
        }
    }
}
