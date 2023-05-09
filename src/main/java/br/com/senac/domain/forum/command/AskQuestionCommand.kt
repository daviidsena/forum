package br.com.senac.domain.forum.command

import org.axonframework.commandhandling.RoutingKey
import java.util.UUID

import org.axonframework.modelling.command.TargetAggregateIdentifier
import javax.persistence.ElementCollection
import javax.persistence.FetchType
import javax.persistence.Id

class AskQuestionCommand (
	@TargetAggregateIdentifier val questionId: UUID,
	val description: String,
	val status: String,
	val userId: UUID,
	val chapterId: UUID
)
