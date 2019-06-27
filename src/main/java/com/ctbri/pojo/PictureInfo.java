package com.ctbri.pojo;

import java.util.List;

public class PictureInfo {

    private String title;
    private String imageType;
    private List<String> tags;
    private List<String> downs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getDowns() {
        return downs;
    }

    public void setDowns(List<String> downs) {
        this.downs = downs;
    }
}
