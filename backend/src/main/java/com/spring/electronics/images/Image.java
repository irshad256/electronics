package com.spring.electronics.images;

import com.spring.electronics.backoffice.category.Category;
import com.spring.electronics.backoffice.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String type;

    @Column(name = "picByte", length = 102400)
    private  byte[] picByte;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(mappedBy = "image")
    private Category category;
}
