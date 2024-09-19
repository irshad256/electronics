package com.spring.electronics.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.electronics.category.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Builder
@Getter
public class ProductDto {

    private String code;

    private String name;

    private String description;

    private Long stock;

    private boolean active;

    private double price;

    private String categoryCode;
}
