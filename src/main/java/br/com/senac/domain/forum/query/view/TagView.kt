package br.com.senac.domain.forum.query.view;

import java.time.LocalDateTime
import java.util.UUID;
import javax.persistence.*

@Entity
data class TagView (
	@Column(columnDefinition = "BINARY(16)")
	@Id val tagId: UUID,
	val name: String,
	val description: String,
	val status: String,
	@Column(columnDefinition = "BINARY(16)")
	val userId: UUID,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
) {
	constructor() : this(UUID.randomUUID(), String.toString(), String.toString(), String.toString(), UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now()) {
	}
}
