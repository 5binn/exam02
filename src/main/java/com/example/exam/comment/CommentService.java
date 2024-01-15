package com.example.exam.comment;

import com.example.exam.article.Article;
import com.example.exam.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void create(String content, Article article, Member member) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setArticle(article);
        comment.setMember(member);
        this.commentRepository.save(comment);
    }
}
