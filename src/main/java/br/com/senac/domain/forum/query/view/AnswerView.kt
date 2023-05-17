package br.com.senac.domain.forum.query.view

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class AnswerView (
    @Column(columnDefinition = "BINARY(16)")
    val questionId: UUID,

    @Column(columnDefinition = "BINARY(16)")
    @Id val answerId: UUID,

    @Column(columnDefinition = "BINARY(16)")
    val answerParentsId: UUID,

    val description: String,
    val status: String,

    @Column(columnDefinition = "BINARY(16)")
    val userId: UUID,

    val likeAnswer: Int,

    val unlikeAnswer: Int,
) {
    constructor() : this(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),"","", UUID.randomUUID(), 0, 0) {
    }
}

