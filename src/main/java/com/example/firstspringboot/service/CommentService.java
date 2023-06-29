package com.example.firstspringboot.service;

import com.example.firstspringboot.dto.CommentDto;
import com.example.firstspringboot.entity.Article;
import com.example.firstspringboot.entity.Comment;
import com.example.firstspringboot.repository.ArticleRepository;
import com.example.firstspringboot.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {
//        // 조회: 댓글 목록 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//
//        // 변환: 엔티티 -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for (int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }

        // 반환
        return commentRepository.findByArticleId(articleId).stream().map(comment -> CommentDto.createCommentDto(comment)).collect(Collectors.toList());
    }

    @Transactional // DB에 접근하기 때문에 중간에 문제가 생길경우 롤백하기 위해 사용
    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패 대상 게시글이 없습니다."));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        // DTO로 변경하여 변환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 댓글이 존재하지 않습니다!"));

        // 댓글 수정
        target.patch(dto);

        // DB 갱신
        Comment updated = commentRepository.save(target);

        // 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외 발생
        // ?: commentRepository.deleteById(id) -> 쓰지 않는 이유? => 예외처리를 위해?
        Comment target = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 해당 댓글이 존재하지 않습니다."));

        // 댓글 삭제
        commentRepository.delete(target);

        // 삭제 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
