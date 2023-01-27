package com.group.libraryapp.domain.admin

import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, Long> {
  
  fun findByEmail(email: String): Admin
  
}