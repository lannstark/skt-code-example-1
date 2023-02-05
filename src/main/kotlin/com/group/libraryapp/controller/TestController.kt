package com.group.libraryapp.controller

import com.group.libraryapp.external.booksearch.BookSearchServiceV1
import com.group.libraryapp.external.booksearch.BookSearchServiceV2
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
  private val bookSearchServiceV1: BookSearchServiceV2,
) {
  
  @GetMapping("/test/v1")
  fun test1() {
    println(bookSearchServiceV1.search("클린"))
  }
  
}