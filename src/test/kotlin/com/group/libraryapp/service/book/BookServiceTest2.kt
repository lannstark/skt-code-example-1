package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.external.booksearch.BookSearchResponse
import com.group.libraryapp.external.booksearch.BookSearchService
import com.group.libraryapp.repository.user.loanhistory.UserLoanHistoryQuerydslRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest2 @Autowired constructor(
  private val bookRepository: BookRepository,
  private val userRepository: UserRepository,
  private val userLoanHistoryQuerydslRepository: UserLoanHistoryQuerydslRepository,
) {
  
  @Test
  fun mockTest() {
    // given
    val bookSearchService = mockk<BookSearchService>()
    every { bookSearchService.search(any()) } returns listOf(BookSearchResponse("클린 코드", "밥아저씨"))
    val sut = BookService(bookRepository, userRepository, userLoanHistoryQuerydslRepository, bookSearchService)
    
    // when
    val results = sut.searchBooks("클린")
    
    // then
    assertThat(results).isEqualTo(listOf(BookSearchResponse("클린 코드", "밥아저씨")))
  }
  
}