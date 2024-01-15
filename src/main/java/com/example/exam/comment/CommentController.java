package com.example.exam.comment;

import com.example.exam.article.Article;
import com.example.exam.article.ArticleService;
import com.example.exam.member.Member;
import com.example.exam.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final ArticleService articleService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@PathVariable("id")Integer id, Principal principal,
                         @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "article_detail";
        }
        Article article = this.articleService.getArticle(id);
        Member member = this.memberService.getMember(principal.getName());

        this.commentService.create(commentForm.getContent(),article,member);
        return String.format("redirect:/article/detail/%s",article.getId());
    }
}
