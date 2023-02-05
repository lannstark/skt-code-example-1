package com.group.libraryapp.external.dto

data class NaverBookSearchResponse(
  val lastBuildDate: String,
  val total: Int,
  val start: Int,
  val display: Int,
  val items: List<NaverBookSearchItem>,
)

data class NaverBookSearchItem(
  val title: String,
  val link: String,
  val image: String,
  val author: String,
  val publisher: String,
  val isbn: String,
  val description: String,
)