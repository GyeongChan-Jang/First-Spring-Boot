package com.example.firstspringboot.service;

import com.example.firstspringboot.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 테스팅된다
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 예상


        // 실제
        List<Article> articles = articleService.index();

        // 비교
    }

    @Test
    @Transactional
        // 테스트 종료후 변경된 데이터를 롤백 처리
//    void show_성공_존재하는_id_입력() {
//        // 예상
//        Long id = 1L;
//        Article expected = new Article(id, "asdf", "asdf");
//
//        // 실제
//        Article article = articleService.show(id);
//
//        // 비교
//        assertEquals(new Article(1L, "asdf", "asdf")
//    }

    void show_실패() {

    }
}