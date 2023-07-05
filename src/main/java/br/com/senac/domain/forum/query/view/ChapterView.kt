package br.com.senac.domain.forum.query.view;

import java.time.LocalDateTime
import java.util.UUID;
import javax.persistence.*

@Entity
data class ChapterView (
	@Column(columnDefinition = "BINARY(16)")
	@Id val chapterId: UUID,
	val name: String,
	val description: String,
	val status: String,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
) {
	constructor() : this(UUID.randomUUID(), String.toString(), String.toString(), String.toString(), LocalDateTime.now(), LocalDateTime.now()) {
	}
}
