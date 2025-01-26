package com.tutorial.dreamshops.dto;

import lombok.Data;


public class ImageDto {
    private Long imageId;
    private String imageName;
    private String downloadUrl;

    public void setImageId(Long id) {
        this.imageId = id;
    }

    public void setImageName(String name) {
        this.imageName = name;
    }

    public void setDownloadUrl(String url) {
        this.downloadUrl = url;
    }
}
