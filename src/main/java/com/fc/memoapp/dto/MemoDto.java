package com.fc.memoapp.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class MemoDto {
    private Long id;
    @NotBlank(message = "タイトルを入力してください")
    @Size(max = 20, message = "20文字以内で入力してください")
    private String title;
    @NotBlank(message = "内容を入力してください")
    @Size(max = 200, message = "200文字以内で入力してください")
    private String content;
}