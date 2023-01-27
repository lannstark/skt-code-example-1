package com.group.libraryapp.domain.book

import javax.persistence.*

@Entity
class Book(
  val name: String,
  
  val adminId: Long,
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
) {
  
  init {
    if (name.isBlank()) {
      throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
    }
  }
  
  companion object {
    fun fixture(
      name: String = "책 이름",
      adminId: Long = 0L,
      id: Long? = null,
    ): Book {
      return Book(
        name = name,
        adminId = adminId,
        id = id,
      )
    }
  }
  
}
