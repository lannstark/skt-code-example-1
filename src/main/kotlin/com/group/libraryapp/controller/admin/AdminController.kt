package com.group.libraryapp.controller.admin

import com.group.libraryapp.dto.admin.SignupRequest
import com.group.libraryapp.service.admin.AdminService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AdminController(
  private val adminService: AdminService,
) {
  
  @PostMapping("/signup")
  fun signup(@RequestBody request: SignupRequest) {
    adminService.signup(request)
  }
  
}


