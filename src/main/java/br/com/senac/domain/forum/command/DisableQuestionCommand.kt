package br.com.senac.domain.forum.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class DisableQuestionCommand (
    @TargetAggregateIdentifier val questionId: UUID
)