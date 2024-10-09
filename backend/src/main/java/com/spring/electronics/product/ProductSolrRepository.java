package com.spring.electronics.product;

import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository("productSolrRepository")
public interface ProductSolrRepository extends SolrCrudRepository<Product, Long> {

//    Set<Product> findByNameContaining(String name);
//
//    Set<Product> findByCategories_CodeIn(Set<String> categoryCodes);
}
