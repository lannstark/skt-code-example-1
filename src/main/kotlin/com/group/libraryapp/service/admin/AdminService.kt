package com.group.libraryapp.service.admin

import com.group.libraryapp.domain.admin.Admin
import com.group.libraryapp.domain.admin.AdminRepository
import com.group.libraryapp.dto.admin.SignupRequest
import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
  private val adminRepository: AdminRepository,
) {
  
  @Transactional
  fun signup(request: SignupRequest) {
    adminRepository.save(
      Admin(
        email = request.email,
        encryptPassword = BCrypt.hashpw(request.plainPassword, SALT)
      )
    )
  }
  
  companion object {
    /**
     * properties로도 뺄 수도 있고, AWS Secrets Manager 혹은 Spring Cloud Config 등으로 뺄 수도 있다.
     */
    const val SALT = "\$2a\$10\$81kF6U8mS3Bl6087K9xRG.96JClSCEsMzHKGdbCVRXZZTzgIHb5Oq"
  }
  
}