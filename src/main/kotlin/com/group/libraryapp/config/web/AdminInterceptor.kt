package com.group.libraryapp.config.web

import com.group.libraryapp.domain.admin.AdminRepository
import com.group.libraryapp.util.Log
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AdminInterceptor(
  private val adminRepository: AdminRepository,
) : HandlerInterceptor {
  
  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    val email = request.getHeader("Authorization") ?: throw IllegalArgumentException("헤더에 이메일 정보가 없습니다.")
    val admin = adminRepository.findByEmail(email) // Admin 정보를 가져와 로깅
    log.info("어드민 접근 : ${admin.id}")
    return super.preHandle(request, response, handler)
  }
  
  companion object : Log
  
}