package com.group.libraryapp.service.book.save

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.dto.book.request.BookRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookSaveServiceV2(
  private val bookRepository: BookRepository,
) {
  
  @Transactional
  fun saveBook(request: BookRequest, adminId: Long) {
    val book = bookRepository.findByName(request.name)
      ?: bookRepository.save(Book(request.name, adminId))
    book.addInventory()
  }
  
}