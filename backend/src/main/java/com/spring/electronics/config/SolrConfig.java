package com.spring.electronics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

    @Value("${spring.data.solr.host}")
    private String solrUrl;
//
//    @Bean
//    public SolrClient solrClient() {
//        return new Http2SolrClient.Builder(solrUrl).build();
//    }
//
//    @Bean
//    public SolrTemplate solrTemplate(SolrClient solrClient) {
//        return new SolrTemplate(solrClient);
//    }
}
