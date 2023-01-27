package com.group.libraryapp.config.web

import com.group.libraryapp.domain.admin.AdminRepository
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LogInUserArgumentResolver(
  private val adminRepository: AdminRepository,
) : HandlerMethodArgumentResolver {
  override fun supportsParameter(parameter: MethodParameter): Boolean {
    return parameter.hasParameterAnnotation(LogInAdminId::class.java) &&
            parameter.parameterType == Long::class.java
  }
  
  override fun resolveArgument(
    parameter: MethodParameter,
    mavContainer: ModelAndViewContainer?,
    webRequest: NativeWebRequest,
    binderFactory: WebDataBinderFactory?,
  ): Any? {
    val email = webRequest.getHeader("Authorization") ?: throw IllegalArgumentException("헤더에 이메일 정보가 없습니다.")
    return adminRepository.findByEmail(email).id
  }
  
}
