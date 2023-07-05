package br.com.senac.domain.forum.event

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime
import java.util.UUID

data class AnswerQuestionEvent (
	val questionId: UUID,
	val answerId : UUID,
	val answerParentsId : UUID,
	val description: String,
	val status : String,
	val userId: UUID,
	val like: Int,
	val unlike: Int,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime
)
