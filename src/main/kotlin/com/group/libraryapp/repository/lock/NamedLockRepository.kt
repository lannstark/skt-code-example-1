package com.group.libraryapp.repository.lock

import com.group.libraryapp.domain.book.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface NamedLockRepository : JpaRepository<Book, Long> {
  
  @Query(value = "select get_lock(:key, 1000)", nativeQuery = true)
  fun getLock(key: String)
  
  @Query(value = "select release_lock(:key)", nativeQuery = true)
  fun releaseLock(key: String)
  
}