package com.KoreaIT.java.AM.dao;

import com.KoreaIT.java.AM.dto.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleDao extends Dao {
  private List<Article> articles;

  public ArticleDao() {
    articles = new ArrayList<>();
  }

  public void add(Article article) {
    articles.add(article);
    lastId++;
  }

  public int getNewId() {
    return lastId + 1;
  }

  public List<Article> getArticles(String searchKeyword) {
    if (searchKeyword != null && searchKeyword.length() != 0) {
      List<Article> forPrintArticles = new ArrayList<>();

      if (searchKeyword.length() > 0) {
        for (Article article : articles) {
          if (article.title.contains(searchKeyword)) {
            forPrintArticles.add(article);
          }
        }
      }
      return forPrintArticles;
    }
    return articles;
  }
}
