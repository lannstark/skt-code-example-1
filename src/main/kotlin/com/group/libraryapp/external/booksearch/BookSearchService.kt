package com.group.libraryapp.external.booksearch

import com.group.libraryapp.external.dto.NaverBookSearchItem

interface BookSearchService {
  
  fun search(query: String): List<BookSearchResponse>
  
}

data class BookSearchResponse(
  val name: String, // 책이름
  val author: String, // 책 저자
) {
  companion object {
    fun of(item: NaverBookSearchItem): BookSearchResponse {
      return BookSearchResponse(item.title, item.author)
    }
  }
}