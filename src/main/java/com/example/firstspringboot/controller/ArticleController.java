package com.example.firstspringboot.controller;

import com.example.firstspringboot.dto.ArticleForm;
import com.example.firstspringboot.entity.Article;
import com.example.firstspringboot.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j // Simple Logging Facade for Java, 로깅을 위한 어노테이션
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

        // redirect
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    // URL 요청을 Parameter로 받으려면 @PathVariable을 사용
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1: id로 데이터를 가져옴!
        // Optional<Article> articleEntity = articleRepository.findById(id);
        // orElse는 데이터가 없는경우 처리
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2: 가져온 데이터를 모델에 등록! -> 뷰에서 쓸 수 있도록(모델은 뷰에서 쓰기위한 형태)
        model.addAttribute("article", articleEntity);

        // 3: 보여줄 페이지를 설정!

        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1: 모든 Article을 가져온다!

        // Iterable<Article> articleEntityList = articleRepository.findAll();
        // ArrayList가 반환되지만 더 상위의 개념인 List로도 받을 수 있음
        List<Article> articleEntityList = articleRepository.findAll();

        // 2: 가져온 Article 묶음을 뷰로 전달!
        model.addAttribute("articleList", articleEntityList);

        // 3: 뷰 페이지를 설정!

        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터를 가져오기!

        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 뷰 페이지 설정!
        model.addAttribute("article", articleEntity);

        log.info("Article" + articleEntity);

        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1: DTO를 엔티티로 변경한다!
        Article articleEntity = form.toEntity();

        // 2: 엔티티를 DB에 저장한다!
        // 2-1: DB에서 기존 데이터를 가져온다!
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        log.info(target.toString());
        if (target != null) {
            articleRepository.save(articleEntity);
        }
        // 2-2:

        // 3: 수정 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");
        //  articleRepository.deleteById((id));

        // 1: 삭제 대상을 가져온다!
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2: 대상을 삭제 한다!
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료 되었습니다!");
        }

        // 3: 결과 페이지로 리다이렉트 한다!
        return "redirect:/articles";
    }
}