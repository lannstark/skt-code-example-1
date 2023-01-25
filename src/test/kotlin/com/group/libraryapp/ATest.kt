package com.group.libraryapp

import org.assertj.core.api.AssertProvider
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.api.StringAssert
import org.junit.jupiter.api.Test

class ATest {

  @Test
  fun test() {
    assertThat("123").isEqualTo("123")
    val person = Person("A")
    assertThat(person).isEqualTo("A")
  }
}

class Person(
  private val name: String
) : AssertProvider<StringAssert> {
  override fun assertThat(): StringAssert {
    return StringAssert(this.name)
  }
}
