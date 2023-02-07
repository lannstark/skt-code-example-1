package com.group.libraryapp.service.book.save

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.dto.book.request.BookRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookSaveServiceV1(
  private val bookRepository: BookRepository,
) {
  
  @Transactional
  fun saveBook(request: BookRequest, adminId: Long) {
    val book = Book(request.name, adminId)
    bookRepository.save(book)
  }
  
}