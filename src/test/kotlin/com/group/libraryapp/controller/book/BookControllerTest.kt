package com.group.libraryapp.controller.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.group.libraryapp.domain.admin.Admin
import com.group.libraryapp.domain.admin.AdminRepository
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.dto.book.request.BookRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BookControllerTest @Autowired constructor(
  private val adminRepository: AdminRepository,
  private val bookRepository: BookRepository,
  private val mockMvc: MockMvc,
  private val objectMapper: ObjectMapper,
) {
  
  @AfterEach
  fun clean() {
    adminRepository.deleteAllInBatch()
    bookRepository.deleteAllInBatch()
  }
  
  @Test
  fun `도서 등록을 할 때에 어드민 정보가 같이 저장된다`() {
    // given
    val admin = adminRepository.save(Admin.fixture())
    
    // when
    mockMvc.perform(
      post("/book")
        .header("Authorization", "A")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(BookRequest(name = "클린 코드")))
    )
    
    // then
    val books = bookRepository.findAll()
    assertThat(books[0].adminId).isEqualTo(admin.id)
    assertThat(books[0].name).isEqualTo("클린 코드")
  }
  
}