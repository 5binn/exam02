package com.example.exam.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/")
    private String root() {
        return "redirect:/article/list";
    }
    @GetMapping("/list")
    private String list(Model model) {
        List<Article> articleList = this.articleService.findAll();
        model.addAttribute("articleList",articleList);
        return "article_list";
    }

    @GetMapping("/create")
    private String create() {
        return "article_form";
    }
    @PostMapping("/create")
    private String create(@RequestParam("title")String title, @RequestParam("content")String content) {
        articleService.create(title, content);
        return "redirect:/article/list";
    }
}
