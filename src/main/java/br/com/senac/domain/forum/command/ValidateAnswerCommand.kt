package br.com.senac.domain.forum.command

import java.util.UUID
import org.axonframework.commandhandling.RoutingKey

data class ValidateAnswerCommand (
	@RoutingKey
	val askQuestionId: UUID,
	val userId: UUID
)