package br.com.senac.domain.forum.controller.model

data class AskQuestion (
    val description: String,
    val userId: String,
    val userName: String,
    val chapterId: String
)
