package br.com.senac.domain.forum.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class ApproveQuestionCommand (
	@TargetAggregateIdentifier val askQuestionId: UUID,
	val userId: UUID
)
