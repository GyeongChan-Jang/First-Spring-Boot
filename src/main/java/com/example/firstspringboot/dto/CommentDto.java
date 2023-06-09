package com.example.firstspringboot.dto;

import com.example.firstspringboot.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 모든 생성자 자동완성
@NoArgsConstructor // default 생성자 자동완성
@Getter
@ToString
public class CommentDto {
    private Long id;
    @JsonProperty("article_id") // json에서 데이터 받을경우 자동으로 articleId로 변환해줌
    private Long articleId;
    private String nickname;
    private String body;

    // static -> 클래스 메소드 생성할 때 선언해주는 것
    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getArticle().getId(), comment.getNickname(), comment.getBody());
    }
}
