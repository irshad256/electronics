package com.spring.electronics.backoffice.category;

import com.spring.electronics.backoffice.product.Product;
import com.spring.electronics.images.Image;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;

    // Self-referencing Many-to-Many for superCategories and subcategories
    @ManyToMany
    @JoinTable(
            name = "category_hierarchy",
            joinColumns = @JoinColumn(name = "subCategory_id"),
            inverseJoinColumns = @JoinColumn(name = "superCategory_id")
    )
    private Set<Category> superCategories = new HashSet<>();

    @ManyToMany(mappedBy = "superCategories")
    private Set<Category> subCategories = new HashSet<>();
}
