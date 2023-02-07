package com.group.libraryapp.service.lock

import com.group.libraryapp.repository.lock.NamedLockRepository
import org.springframework.stereotype.Service

@Service
class UserLockManager(
  private val namedLockRepository: NamedLockRepository,
) {
  
  fun execute(key: String, exec: () -> Unit) {
    try {
      namedLockRepository.getLock(key)
      exec()
    } finally {
      namedLockRepository.releaseLock(key)
    }
  }
  
}