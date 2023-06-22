package com.example.firstspringboot.service;

import com.example.firstspringboot.dto.CommentDto;
import com.example.firstspringboot.entity.Comment;
import com.example.firstspringboot.repository.ArticleRepository;
import com.example.firstspringboot.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
        // 조회: 댓글 목록 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 변환: 엔티티 -> DTO
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }

        // 반환
        return null;
    }
}
