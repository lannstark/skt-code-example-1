package com.group.libraryapp.config

import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {
  
  @Bean
  fun restTemplate(): RestTemplate {
    val httpClient = HttpClientBuilder.create()
      .setMaxConnTotal(100)
      .setMaxConnTotal(5)
      .build()
    
    val factory = HttpComponentsClientHttpRequestFactory()
    factory.httpClient = httpClient
    factory.setConnectTimeout(3 * 1000)
    factory.setReadTimeout(5 * 1000)
    factory.setConnectionRequestTimeout(3 * 1000)
    
    return RestTemplate(factory)
  }
  
}