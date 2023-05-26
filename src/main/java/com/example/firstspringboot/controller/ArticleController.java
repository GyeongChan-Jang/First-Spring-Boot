package com.example.firstspringboot.controller;
import com.example.firstspringboot.dto.ArticleForm;
import com.example.firstspringboot.entity.Article;
import com.example.firstspringboot.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@Slf4j // Simple Logging ~, 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링부트가 미리 생성해 놓은 객체를 가져다가 자동 연결!, 객체 주입
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());
        // System.out.println(form.toString()); -> 로깅으로 대체

        // 1. Dto를 Entity로 변환!
        Article article = form.toEntity();
        log.info(article.toString());
//        System.out.println(article.toString());

        // 2. Repository에게 Entity를 DB안에 저장하게 함!
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
        log.info(saved.toString());

        return "";
    }
}