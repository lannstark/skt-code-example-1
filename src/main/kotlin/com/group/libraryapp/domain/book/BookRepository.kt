package com.group.libraryapp.domain.book

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType

interface BookRepository : JpaRepository<Book, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  fun findByName(bookName: String): Book?
  
  fun findAllByNameContains(name: String): List<Book>

}