package com.spring.electronics.images;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDto {

    private String name;

    private String type;

    private byte[] picByte;
}
