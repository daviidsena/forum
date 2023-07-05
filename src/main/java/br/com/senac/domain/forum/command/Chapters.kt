package br.com.senac.domain.forum.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateChapterCommand(@TargetAggregateIdentifier val chapterId: UUID, val name: String, val description: String, val status: String)
data class UpdateChapterCommand(@TargetAggregateIdentifier val chapterId: UUID, val name: String, val description: String, val status: String)
data class DeleteChapterCommand(@TargetAggregateIdentifier val chapterId: UUID)