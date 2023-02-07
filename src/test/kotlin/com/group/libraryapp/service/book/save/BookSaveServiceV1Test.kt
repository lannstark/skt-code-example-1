package com.group.libraryapp.service.book.save

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.util.Log
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import java.util.concurrent.Executors

@SpringBootTest
internal class BookSaveServiceV1Test @Autowired constructor(
  private val bookSaveServiceV1: BookSaveServiceV1,
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
        try {
          bookSaveServiceV1.saveBook(BookRequest("클린 코드"), 1L)
        } catch (e: DataIntegrityViolationException) {
          // Ignore
        }
      }
    }
    jobs.forEach { job -> job.join() }
    
    // then
    assertThat(bookRepository.findAll()).hasSize(1)
  }
  
  companion object : Log
  
}

