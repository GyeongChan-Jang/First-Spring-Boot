package com.example.firstspringboot.service;

import com.example.firstspringboot.dto.ArticleForm;
import com.example.firstspringboot.entity.Article;
import com.example.firstspringboot.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

//    public ArticleService(ArticleRepository articleRepository) {
//        this.articleRepository = articleRepository;
//    }

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        // 이미 존재하는 ID로 요청을 보낼 경우 처리
        Article article = dto.toEntity();
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();

        log.info(id + dto.toString());

        Article target = articleRepository.findById(id).orElse(null);

        if (target == null || article.getId() != id) {
            return null;
        }

        target.patch(article);
        return articleRepository.save(target);
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 Entity 묶음으로 변환
        List<Article> articleList = dtos.stream().map((dto) -> dto.toEntity()).collect(Collectors.toList());

        // entity 묶음을 DB로 저장
        articleList.stream().forEach(article -> articleRepository.save(article));

        // 강제 예외 발생
        articleRepository.findById(-1l).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );
        return articleList;
    }
}
