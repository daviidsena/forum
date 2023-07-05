package br.com.senac.domain.forum.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class TagCommand (
	@TargetAggregateIdentifier val tagId: UUID,
	val name: String,
	val description: String,
	val status : String,
	val userId: UUID
)
