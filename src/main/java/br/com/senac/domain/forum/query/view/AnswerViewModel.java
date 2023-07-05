package br.com.senac.domain.forum.query.view;

import java.time.LocalDateTime;
import java.util.UUID;

public class AnswerViewModel {
	private UUID questionId;
	private UUID answerId;
	private UUID answerParentsId;
	private String description;
	private String status;
	private UUID userId;
	private String userName;
	private int likeAnswer;
	private int unlikeAnswer;
	private LocalDateTime createdAt;

	public LocalDateTime getCreatedAt(LocalDateTime createdAt) {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public UUID getQuestionId() {
		return questionId;
	}

	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}

	public UUID getAnswerId() {
		return answerId;
	}

	public void setAnswerId(UUID answerId) {
		this.answerId = answerId;
	}

	public UUID getAnswerParentsId() {
		return answerParentsId;
	}

	public void setAnswerParentsId(UUID answerParentsId) {
		this.answerParentsId = answerParentsId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLikeAnswer() {
		return likeAnswer;
	}

	public void setLikeAnswer(int likeAnswer) {
		this.likeAnswer = likeAnswer;
	}

	public int getUnlikeAnswer() {
		return unlikeAnswer;
	}

	public void setUnlikeAnswer(int unlikeAnswer) {
		this.unlikeAnswer = unlikeAnswer;
	}
}