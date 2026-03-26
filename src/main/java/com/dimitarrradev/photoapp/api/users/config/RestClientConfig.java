package com.dimitarrradev.photoapp.api.users.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    @LoadBalanced
    @Qualifier("loadBalancedBuilder")
    public RestClient.Builder loadBalancedBuilder() {
        return RestClient.builder();
    }

    @Bean
    @Primary
    @Qualifier("primaryBuilder")
    public RestClient.Builder primaryBuilder() {
        return RestClient.builder();
    }

    @Bean
    @Primary
    public RestClient primaryClient(RestClient.Builder builder) {
        return builder.build();
    }

    @Bean
    @Qualifier("loadBalancedClient")
    public RestClient loadBalancedClient(@Qualifier("loadBalancedBuilder") @LoadBalanced RestClient.Builder builder) {
        return builder.baseUrl("http://albums-ws").build();
    }

}
