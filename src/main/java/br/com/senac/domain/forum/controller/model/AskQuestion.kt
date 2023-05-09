package br.com.senac.domain.forum.controller.model

import org.jetbrains.annotations.NotNull
import java.util.*

data class AskQuestion (
    val description: String,
    val userId: String,
    val chapterId: String
)
