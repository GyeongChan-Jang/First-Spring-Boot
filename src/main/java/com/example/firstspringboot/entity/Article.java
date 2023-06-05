package com.example.firstspringboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식 가능! -> 클래스를 바탕으로 테이블을 만든다.
@AllArgsConstructor
@ToString
@NoArgsConstructor // JPA의 Entity 객체는 기본 생성자(파라미터가 없는)를 생성해줘야함
@Getter
public class Article {

    @Id
    // @GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id를 자동으로 생성
    private Long id;

    @Column
    private String title;
    @Column
    private String content;

    public void patch(Article article) {
        if (article.title != null) {
            this.title = article.title;
        }
        if (article.content != null) {
            this.content = article.content;
        }
    }

//    public Article(Long id, String title, String content) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }

//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
