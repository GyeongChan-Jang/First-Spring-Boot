package com.example.firstspringboot.repository;

import com.example.firstspringboot.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

// CrudRepository 제네릭으로 1. Entity, 해당 Entity PK
// CrudRepository 에는 DB에 CRUD 하는 코드가 이미 구현되어 있음
public interface ArticleRepository extends CrudRepository<Article, Long> {
    // 메소드 오버라이드
    @Override
    ArrayList<Article> findAll();
}
