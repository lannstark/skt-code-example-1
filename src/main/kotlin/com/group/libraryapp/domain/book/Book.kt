package com.group.libraryapp.domain.book

import javax.persistence.*

@Entity
@Table(
  uniqueConstraints = [UniqueConstraint(name = "uni_book_1", columnNames = ["name"])]
)
class Book(
  val name: String,
  
  val adminId: Long,
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
) {
  
  var count: Int = 0
  
//  @Version
//  val version: Long = 0L
  
  init {
    if (name.isBlank()) {
      throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
    }
  }
  
  fun addInventory() {
    this.count += 1
  }
  
  fun minusInventory() {
    require(this.count >= 1)
    this.count -= 1
  }
  
  companion object {
    fun fixture(
      name: String = "책 이름",
      adminId: Long = 0L,
      count: Int = 1,
      id: Long? = null,
    ): Book {
      return Book(
        name = name,
        adminId = adminId,
        id = id,
      ).apply {
        this.count = count
      }
    }
  }
  
}
