package com.group.libraryapp.domain.admin

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Admin(
  val email: String,
  
  val encryptPassword: String,
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
) {
  
  companion object {
    fun fixture(
      email: String = "A",
      encryptPassword: String = "1234",
    ): Admin {
      return Admin(
        email,
        encryptPassword,
      )
    }
  }
  
}