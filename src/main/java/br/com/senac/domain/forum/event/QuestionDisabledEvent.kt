package br.com.senac.domain.forum.event

import java.util.*

data class QuestionDisabledEvent (
    val questionId: UUID
)