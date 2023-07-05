package br.com.senac.domain.forum.query.view;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class QuestionViewModel {
	private UUID questionId;
	private List<UUID> answers;
	private String description;
	private String status;
	private UUID userId;
	private String userName;
	private UUID chapterId;
	private LocalDateTime createdAt;

	public LocalDateTime getCreatedAt(LocalDateTime createdAt) {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public QuestionViewModel(UUID questionId, List<UUID> answers, String description, String status, UUID userId, String userName, UUID chapterId) {
		this.questionId = questionId;
		this.answers = answers;
		this.description = description;
		this.status = status;
		this.userId = userId;
		this.userName = userName;
		this.chapterId = chapterId;
	}

	public QuestionViewModel() {
		this(UUID.randomUUID(), new ArrayList<UUID>(), "", "", UUID.randomUUID(), "", UUID.randomUUID());
	}

	public UUID getQuestionId() {
		return questionId;
	}

	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}

	public List<UUID> getAnswers() {
		return answers;
	}

	public void setAnswers(List<UUID> answers) {
		this.answers = answers;
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

	public UUID getChapterId() {
		return chapterId;
	}

	public void setChapterId(UUID chapterId) {
		this.chapterId = chapterId;
	}
}