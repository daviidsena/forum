package br.com.senac.domain.forum.event

import java.util.UUID

data class ChapterUpdatedEvent (val chapterId: UUID, val name: String, val description: String, val status: String)
