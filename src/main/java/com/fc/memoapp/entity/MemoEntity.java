package com.fc.memoapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class MemoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// タイトル部分
	@NotBlank(message = "タイトルを入力してください")
	@Size(max = 20, message = "タイトルは20文字以内で入力してください")
	private String title;

	// 内容部分
	@NotBlank(message = "内容を入力してください")
	@Size(max = 200, message = "内容は200文字以内で入力してください")
	private String content;

	public MemoEntity() {
	}

	public MemoEntity(String title, String content) {
		this.title = title;
		this.content = content;
	}

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
}
