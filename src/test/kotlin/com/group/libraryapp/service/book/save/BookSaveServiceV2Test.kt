package com.group.libraryapp.service.book.save

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.service.lock.UserLockManager
import com.group.libraryapp.util.Log
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.AssertionsForInterfaceTypes
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import java.util.concurrent.Executors

@SpringBootTest
internal class BookSaveServiceV2Test @Autowired constructor(
  private val bookSaveServiceV2: BookSaveServiceV2,
  private val userLockManager: UserLockManager,
  private val bookRepository: BookRepository,
) {
  
  @AfterEach
  fun clean() {
    bookRepository.deleteAllInBatch()
  }
  
  @Test
  fun `동일한 책에 대해 20개의 API가 동시에 호철되도 1건만 성공한다`(): Unit = runBlocking {
    // given
    val threadPool = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
    
    // when
    val jobs = (1..20).map {
      launch(threadPool) {
        log.info("SAVE LAUNCH")
        userLockManager.execute("클린 코드") {
          bookSaveServiceV2.saveBook(BookRequest("클린 코드"), 1L)
        }
      }
    }
    jobs.forEach { job -> job.join() }
    
    // then
    val books = bookRepository.findAll()
    assertThat(books).hasSize(1)
    
    assertThat(books[0].count).isEqualTo(20)
  }
  
  companion object : Log
  
}