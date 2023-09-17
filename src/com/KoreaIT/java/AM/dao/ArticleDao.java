package com.KoreaIT.java.AM.dao;

import com.KoreaIT.java.AM.dto.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
  public List<Article> articles;

  public ArticleDao() {
    articles = new ArrayList<>();
  }
}
