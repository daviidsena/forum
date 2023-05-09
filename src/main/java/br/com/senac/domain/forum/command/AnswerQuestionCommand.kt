package br.com.senac.domain.forum.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class AnswerQuestionCommand (
	@TargetAggregateIdentifier val questionId: UUID,
	val answerId : UUID,
	val answerParentsId : UUID,
	val description: String,
	val status : String,
	val userId: UUID
)
