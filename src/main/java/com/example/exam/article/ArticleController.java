package com.example.exam.article;

import com.example.exam.member.Member;
import com.example.exam.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/")
    private String root() {
        return "redirect:/article/list";
    }

    @GetMapping("/list")
    private String list(Model model , @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<Article> articlePage = this.articleService.getArticlePage(page, keyword);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("keyword",keyword);
        return "article_list";
    }

    @GetMapping("/create")
    private String create(ArticleForm articleForm) {
        return "article_form";
    }

    @PostMapping("/create")
    private String create(@Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        articleService.create(articleForm.getTitle(), articleForm.getContent(), member);
        return "redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    private String detail(@PathVariable("id") Integer id, Model model) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    private String modify(@PathVariable("id") Integer id, ArticleForm articleForm) {
        return "article_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    private String modify(@PathVariable("id") Integer id, @Valid ArticleForm articleForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "article_form";
        }
        Article article = this.articleService.getArticle(id);
        if (!principal.getName().equals(article.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.articleService.modify(article, articleForm.getTitle(), articleForm.getContent());
        return String.format("redirect:/article/detail/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    private String delete(@PathVariable("id") Integer id, Principal principal) {
        Article article = this.articleService.getArticle(id);
        if (!principal.getName().equals(article.getMember().getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.articleService.delete(article);
        return "redirect:/article/list";
    }
}
