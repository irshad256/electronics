package com.spring.electronics.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.electronics.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String code;

    private String name;

    @Lob
    private String description;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Collection<Product> products;

    public CategoryDto getAllCategoryDto(){
        return CategoryDto.builder()
                .code(code)
                .name(name)
                .description(description)
                .build();
    }

}
