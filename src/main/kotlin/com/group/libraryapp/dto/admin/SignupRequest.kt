package com.group.libraryapp.dto.admin

data class SignupRequest(
  val email: String,
  val plainPassword: String,
)