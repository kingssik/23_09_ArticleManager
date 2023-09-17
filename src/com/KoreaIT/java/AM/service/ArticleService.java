package com.KoreaIT.java.AM.service;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.controller.ArticleController;
import com.KoreaIT.java.AM.dto.Article;

import java.util.List;

public class ArticleService {
  public ArticleController articleDao;

  public List<Article> getForPrintArticles(String searchKeyword) {
    return Container.articleDao.getArticles(searchKeyword);
  }
}
