package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.external.booksearch.BookSearchResponse
import com.group.libraryapp.external.booksearch.BookSearchService
import com.group.libraryapp.repository.user.loanhistory.UserLoanHistoryQuerydslRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest3 @Autowired constructor(
  private val bookRepository: BookRepository,
  private val userRepository: UserRepository,
  private val userLoanHistoryQuerydslRepository: UserLoanHistoryQuerydslRepository,
) {
  
  @Test
  fun stubTest() {
    // given
    val bookSearchService = object : BookSearchService {
      override fun search(query: String): List<BookSearchResponse> {
        return listOf(BookSearchResponse("클린 코드", "밥아저씨"))
      }
    }
    val sut = BookService(bookRepository, userRepository, userLoanHistoryQuerydslRepository, bookSearchService)
    
    // when
    val results = sut.searchBooks("클린")
    
    // then
    assertThat(results).isEqualTo(listOf(BookSearchResponse("클린 코드", "밥아저씨")))
  }
  
}