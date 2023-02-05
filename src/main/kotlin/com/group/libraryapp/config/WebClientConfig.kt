package com.group.libraryapp.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@Configuration
class WebClientConfig(
  private val webClientBuilder: WebClient.Builder,
) {
  
  @Bean
  fun webClient(): WebClient {
    val provider = ConnectionProvider.builder("book-search-pool")
      .maxConnections(50)
      .pendingAcquireTimeout(Duration.ofSeconds(3))
      .pendingAcquireMaxCount(10)
      .maxIdleTime(Duration.ofSeconds(30))
      .maxLifeTime(Duration.ofSeconds(30))
      .build()
    
    
    return webClientBuilder.clientConnector(
      ReactorClientHttpConnector(
        HttpClient.create(provider)
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3 * 1000)
          .responseTimeout(Duration.ofSeconds(5))
          .doOnConnected {
            it.addHandlerFirst(ReadTimeoutHandler(5))
            it.addHandlerLast(WriteTimeoutHandler(5))
          }
      )
    ).build()
  }
  
}