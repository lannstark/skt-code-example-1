package com.group.libraryapp.service.book.loan

import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookLoanService(
  private val bookRepository: BookRepository,
  private val userRepository: UserRepository,
) {
  
  @Transactional
  fun loanBook(request: BookLoanRequest) {
    val book = bookRepository.findByName(request.bookName) ?: fail()
    val user = userRepository.findByName(request.userName) ?: fail()
    user.loanBook(book)
  }
  
}