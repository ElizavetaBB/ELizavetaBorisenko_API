package com.epam.tc.hw3.dto;

import lombok.Data;

@Data
public class StickerDTO {
    private String id;
    private String image;
    private float top;
    private float left;
    private int zIndex;
    private String idCard;

    public StickerDTO(String image, float top, float left, int zIndex) {
        this.image = image;
        this.top = top;
        this.left = left;
        this.zIndex = zIndex;
    }
}
