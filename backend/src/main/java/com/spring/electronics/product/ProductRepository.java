package com.spring.electronics.product;

import com.spring.electronics.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository("productJpaRepository")
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);

    Set<Product> findByCodeIn(Set<String> codes);

}
