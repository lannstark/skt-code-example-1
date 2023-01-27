package com.group.libraryapp.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
  private val adminArgumentResolver: LogInUserArgumentResolver,
  private val adminInterceptor: AdminInterceptor
) : WebMvcConfigurer {
  
  override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
    resolvers.add(adminArgumentResolver)
    super.addArgumentResolvers(resolvers)
  }
  
  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addInterceptor(adminInterceptor)
    super.addInterceptors(registry)
  }
  
}