package com.fc.memoapp.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class MemoEntity {

	// 主キー
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// メモの内容
	private String title;
	private String content;

	// 作成時に自動で設定される日時
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	// 更新時に自動で設定される日時
	@LastModifiedDate
	private LocalDateTime updatedAt;

	// JPA用のデフォルトコンストラクタ
	public MemoEntity() {
	}

	// 新規作成時に使うコンストラクタ
	public MemoEntity(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// getter / setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
