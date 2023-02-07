package com.group.libraryapp.service.book.loan

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.util.Log
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.Executors

@SpringBootTest
internal class BookLoanServiceTest @Autowired constructor(
  private val bookLoanService: BookLoanService,
  private val bookRepository: BookRepository,
  private val userRepository: UserRepository,
  private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {
  
  @AfterEach
  fun clean() {
    userRepository.deleteAll()
    bookRepository.deleteAllInBatch()
  }
  
  @Test
  fun `재고가 1권 남은 책을 5명이 빌리면 대출기록이 한 개만 쌓이게 된다`(): Unit = runBlocking {
    // given
    val bookName = "클린 코드"
    bookRepository.save(Book.fixture(name = bookName, count = 1))
    val users = listOf("김1", "김2", "김3", "김4", "김5").map { userRepository.save(User.fixture(it)) }
    val threadPool = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
    
    // when
    val jobs = users.map {
      launch(threadPool) {
        log.info("SAVE LAUNCH")
        kotlin.runCatching { bookLoanService.loanBook(BookLoanRequest(it.name, bookName)) }
      }
    }
    jobs.forEach { job -> job.join() }
    
    // then
    assertThat(userLoanHistoryRepository.findAll()).hasSize(1)
    val book = bookRepository.findAll()[0]
    assertThat(book.count).isEqualTo(0)
  }
  
  @Test
  fun `재고가 5권 남은 책을 5명이 동시에 빌리면 재고가 정상적으로 0이 된다`(): Unit = runBlocking {
    // given
    val bookName = "클린 코드"
    bookRepository.save(Book.fixture(name = bookName, count = 5))
    val users = listOf("김1", "김2", "김3", "김4", "김5").map { userRepository.save(User.fixture(it)) }
    val threadPool = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
    
    // when
    val jobs = users.map {
      launch(threadPool) {
        log.info("SAVE LAUNCH")
        bookLoanService.loanBook(BookLoanRequest(it.name, bookName))
      }
    }
    jobs.forEach { job -> job.join() }
    
    // then
    assertThat(userLoanHistoryRepository.findAll()).hasSize(5)
    val book = bookRepository.findAll()[0]
    assertThat(book.count).isEqualTo(0)
  }
  
  companion object : Log
}