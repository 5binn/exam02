package com.example.exam.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("select "
            + "distinct A "
            + "from Article A "
            + "left outer join Member M on A.member=M "
            + "where "
            + "   A.title like %:keyword% "
            + "   or A.content like %:keyword% "
            + "   or M.username like %:keyword% ")
    Page<Article> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
