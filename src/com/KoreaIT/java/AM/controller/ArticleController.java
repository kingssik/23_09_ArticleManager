package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
  private Scanner sc;
  private List<Article> articles;
  private String cmd;
  private String actionMethodName;

  public ArticleController(Scanner sc) {
    this.sc = sc;
    articles = Container.articleDao.articles;
  }

  @Override
  public void doAction(String cmd, String actionMethodName) {
    this.cmd = cmd;
    this.actionMethodName = actionMethodName;

    switch (actionMethodName) {
      case "write":
        doWrite();
        break;
      case "list":
        showList();
        break;
      case "detail":
        showDetail();
        break;
      case "modify":
        doModify();
        break;
      case "delete":
        doDelete();
        break;
      default:
        System.out.println("존재하지 않는 명령어입니다.");
        break;
    }
  }

  private void doWrite() {
    int id = Container.articleDao.getNewId();

    String regDate = Util.getNowDate();
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    Article article = new Article(id, regDate, loginedMember.id, title, body);
    Container.articleDao.add(article);

    System.out.printf("%d번 글이 생성 되었습니다\n", id);
  }

  private void showList() {
    String searchKeyword = cmd.substring("article list".length()).trim();

    if (articles.size() == 0) {
      System.out.println("게시글이 없습니다");
      return;
    } else {

      List<Article> forPrintArticles = articles;

      if (searchKeyword.length() > 0) {
        forPrintArticles = new ArrayList<>();

        for (Article article : articles) {
          if (article.title.contains(searchKeyword)) {
            forPrintArticles.add(article);
          }
        }

        if (forPrintArticles.size() == 0) {
          System.out.println("검색결과가 없습니다");
          return;
        }
      }

      System.out.println("번호   |   작성자   |   제목     |   조회수");
      for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
        Article article = forPrintArticles.get(i);

        String writerName = null;
        List<Member> members = Container.memberDao.members;
        for (Member member : members) {
          if (article.memberId == member.id) {
            writerName = member.name;
            break;
          }
        }

        System.out.printf("%4d   |     %s      |   %s   |   %d\n", article.id, writerName, article.title, article.viewCnt);
      }
    }
  }

  private void showDetail() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
      return;
    }
    foundArticle.increaseViewCnt();

    System.out.printf("번호 : %d\n", foundArticle.id);
    System.out.printf("날짜 : %s\n", foundArticle.regDate);
    System.out.printf("작성자 : %d\n", foundArticle.memberId);
    System.out.printf("제목 : %s\n", foundArticle.title);
    System.out.printf("내용 : %s\n", foundArticle.body);
    System.out.printf("조회 : %d\n", foundArticle.viewCnt);
  }

  private void doModify() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
      return;
    }

    if (foundArticle.memberId != loginedMember.id) {
      System.out.println("권한이 없습니다.");
      return;
    }

    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    foundArticle.title = title;
    foundArticle.body = body;

    System.out.printf("%d번 게시물이 수정 되었습니다\n", id);
  }

  private void doDelete() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다\n", id);
      return;
    }

    if (foundArticle.memberId != loginedMember.id) {
      System.out.println("권한이 없습니다.");
      return;
    }

    articles.remove(foundArticle);
    System.out.printf("%d번 게시물이 삭제 되었습니다\n", id);
  }

  private Article getArticleById(int id) {
    int idx = getArticleIndexById(id);
    if (idx != -1) {
      return articles.get(idx);
    }
    return null;
  }

  private int getArticleIndexById(int id) {
    int i = 0;

    for (Article article : articles) {
      if (article.id == id) {
        return i;
      }
      i++;
    }
    return -1;
  }

  public void makeTestData() {
    System.out.println("게시물 테스트데이터를 생성 합니다");

    Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDate(), 1, "title1", "body1", 11));
    Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDate(), 2, "title2", "body2", 22));
    Container.articleDao.add(new Article(Container.articleDao.getNewId(), Util.getNowDate(), 3, "title3", "body3", 33));
  }
}
