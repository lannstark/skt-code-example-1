package com.group.libraryapp.service.book

import com.group.libraryapp.external.booksearch.BookSearchResponse
import com.group.libraryapp.external.booksearch.BookSearchService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 *
 */
@SpringBootTest
class BookServiceTest1 @Autowired constructor(
  private val bookService: BookService,
) {
  
  @MockkBean
  private lateinit var bookSearchService: BookSearchService
  
  @Test
  fun mockkBeanTest() {
    // given
    every { bookSearchService.search(any()) } returns listOf(BookSearchResponse("클린 코드", "밥아저씨"))
    
    // when
    val results = bookService.searchBooks("클린")
    
    // then
    assertThat(results).isEqualTo(listOf(BookSearchResponse("클린 코드", "밥아저씨")))
  }
  
}