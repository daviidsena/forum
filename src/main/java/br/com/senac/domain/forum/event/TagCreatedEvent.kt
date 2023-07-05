package br.com.senac.domain.forum.event

import java.util.UUID

data class TagCreatedEvent (val tagId: UUID, val name: String,val description: String, val status: String, val userId: UUID)
