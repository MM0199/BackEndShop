package com.tutorial.dreamshops.dto;

import lombok.Data;
import lombok.Setter;

@Setter
public class ImageDto {
    private Long imageId;
    private String imageName;
    private String downloadUrl;
}
