package com.group.libraryapp.external.booksearch

import com.group.libraryapp.external.dto.NaverBookSearchResponse
import com.group.libraryapp.util.Log
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.body
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class BookSearchServiceV2(
  webClient: WebClient,
) : BookSearchService {
  
  private val client = webClient.mutate().baseUrl("https://openapi.naver.com")
    .defaultHeader("X-Naver-Client-Id", "hSJmKPBYYv3pj32JZCGV")
    .defaultHeader("X-Naver-Client-Secret", "LZHsBSVHHv")
    .build()
  
  override fun search(query: String): List<BookSearchResponse> {
    try {
      val response = client.get().uri("/v1/search/book.json?query=$query").retrieve()
        .bodyToMono<NaverBookSearchResponse>()
        .block() ?: return emptyList()
      return response.items.map { BookSearchResponse.of(it) }
    } catch (e: Exception) {
      BookSearchServiceV1.log.error("", e)
      throw IllegalArgumentException("Naver API 호출 에러")
    }
  }
  
  companion object : Log
  
}