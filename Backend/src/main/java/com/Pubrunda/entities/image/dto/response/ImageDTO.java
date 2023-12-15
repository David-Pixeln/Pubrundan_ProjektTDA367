package com.Pubrunda.entities.image.dto.response;

import lombok.Data;

@Data
public class ImageDTO {

    private String name;
    private String type;
    private byte[] data;

}
