package br.com.senac.domain.forum.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime
import java.util.UUID

data class AnswerQuestionCommand (
	@TargetAggregateIdentifier val answerId : UUID,
	val questionId: UUID,
	val answerParentsId : UUID,
	val description: String,
	val status : String,
	val userId: UUID,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime
)
