package br.com.senac.domain.forum.query.view;

import java.util.UUID;
import javax.persistence.*

@Entity
data class QuestionView (
	@Column(columnDefinition = "BINARY(16)")
	@Id val questionId: UUID,
	@ElementCollection(fetch = FetchType.EAGER) val answers: List<UUID>,
	val description: String,
	val status: String,
	@Column(columnDefinition = "BINARY(16)")
	val userId: UUID,
	@Column(columnDefinition = "BINARY(16)")
	val chapterId: UUID,
) {
	constructor() : this(UUID.randomUUID(), ArrayList<UUID>(), String.toString(), String.toString(), UUID.randomUUID(), UUID.randomUUID()) {
	}
}
