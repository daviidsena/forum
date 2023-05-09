package br.com.senac.domain.forum.event

import java.util.UUID

data class QuestionCreatedEvent (val questionId: UUID, val description: String, val status: String, val userId: UUID, val chapterId: UUID, val like: Int,val unlike: Int)
