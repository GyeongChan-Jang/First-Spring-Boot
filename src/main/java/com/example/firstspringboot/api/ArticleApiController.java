package com.example.firstspringboot.api;

import com.example.firstspringboot.dto.ArticleForm;
import com.example.firstspringboot.entity.Article;
import com.example.firstspringboot.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article articleById(@PathVariable Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        return target;
    }

    // POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) {
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        // 1: 수정용 엔티티 생성
        Article article = dto.toEntity();

        // 2: 대상 엔티티를 조회
        Article target = articleRepository.findById(id).orElse(null);
        log.info("id: {}, article: {}", id, target);

        // 3: 잘못된 요청 처리(대상이 없거나, id가 다른 경우)

        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 4: 업데이트 및 정상 응답(200)
        // 부분 수정의 경우 처리
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }

    // DELETE
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
