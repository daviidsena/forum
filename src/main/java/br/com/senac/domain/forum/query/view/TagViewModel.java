package br.com.senac.domain.forum.query.view;

import java.time.LocalDateTime;
import java.util.UUID;

public class TagViewModel {
	private UUID tagId;
	private String name;
	private String description;
	private UUID userId;
	private String userName;
	private String status;
	private LocalDateTime createdAt;

	public LocalDateTime getCreatedAt(LocalDateTime createdAt) {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public UUID getTagId() {
		return tagId;
	}

	public void setTagId(UUID tagId) {
		this.tagId = tagId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}