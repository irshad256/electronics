package com.spring.electronics.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.electronics.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

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

    @Column(length = 1024)
    private String description;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<Product> products;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "super_categories",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "super_category_id")
    )
    @JsonIgnore
    private Set<Category> superCategories;

    public CategoryDto getCategoryDto() {
        Set<String> superCategoryCodes = new HashSet<>();
        superCategories.forEach(category->superCategoryCodes.add(category.getCode()));
        return CategoryDto.builder()
                .code(code)
                .name(name)
                .description(description)
                .superCategories(superCategoryCodes)
                .build();
    }

}
