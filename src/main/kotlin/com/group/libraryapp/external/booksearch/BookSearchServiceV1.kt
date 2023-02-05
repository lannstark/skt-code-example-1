package com.group.libraryapp.external.booksearch

import com.group.libraryapp.external.dto.NaverBookSearchResponse
import com.group.libraryapp.util.Log
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

/**
 * RestTemplate을 사용한 외부 API 호출 Service
 */
@Service
@Primary
class BookSearchServiceV1(
  private val restTemplate: RestTemplate,
) : BookSearchService {
  
  override fun search(query: String): List<BookSearchResponse> {
    val headers = HttpHeaders()
    headers.set("X-Naver-Client-Id", "hSJmKPBYYv3pj32JZCGV")
    headers.set("X-Naver-Client-Secret", "LZHsBSVHHv")
    
    val request = HttpEntity<String>(headers)
    
    try {
      val response = restTemplate.exchange<NaverBookSearchResponse>(
        "https://openapi.naver.com/v1/search/book.json?query=$query",
        HttpMethod.GET,
        request
      ).body ?: return emptyList()
  
      return response.items.map { BookSearchResponse.of(it) }
    } catch(e: Exception) {
      log.error("", e)
      throw IllegalArgumentException("Naver API 호출 에러")
    }
  }
  
  companion object : Log
  
}