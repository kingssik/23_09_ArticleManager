package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {
  private Scanner sc;
  private List<Member> members;
  private String cmd;
  private String actionMethodName;
  private Member loginedMember;

  public MemberController(Scanner sc) {
    this.sc = sc;
    members = new ArrayList<Member>();
  }

  @Override
  public void doAction(String cmd, String actionMethodName) {
    this.cmd = cmd;
    this.actionMethodName = actionMethodName;

    switch (actionMethodName) {
      case "join":
        doJoin();
        break;
      case "login":
        doLogin();
        break;
      default:
        System.out.println("존재하지 않는 명령어입니다.");
        break;
    }
  }

  private void doLogin() {
    String loginId = null;
    String loginPw = null;

    System.out.printf("로그인 아이디 : ");
    loginId = sc.nextLine();

    System.out.printf("로그인 비밀번호 : ");
    loginPw = sc.nextLine();

//    사용자가 입력한 아이디에 해당하는 회원 정보 존재 여부 확인
    Member member = getMemberByLoginId(loginId);

    if (member == null) {
      System.out.println("존재하지 않는 회원입니다.");
      return;
    }

    if (member.loginPw.equals(loginPw) == false) {
      System.out.println("비밀번호를 다시 입력하세요.");
      return;
    }

    loginedMember = member;
    System.out.printf("%s님이 로그인 했습니다.\n", loginedMember.name);
  }

  private void doJoin() {
    int id = members.size() + 1;

    String regDate = Util.getNowDate();

    String loginId = null;
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = sc.nextLine();

      if (isJoinableLoginId(loginId) == false) {
        System.out.printf("%s(은)는 사용 중인 아이디 입니다.\n", loginId);
        continue;
      }
      break;
    }

    String loginPw = null;
    String loginPwCheck = null;

    while (true) {
      System.out.printf("로그인 비밀번호 : ");
      loginPw = sc.nextLine();
      System.out.printf("로그인 비밀번호 확인 : ");
      loginPwCheck = sc.nextLine();

      if (loginPw.equals(loginPwCheck) == false) {
        System.out.println("비밀번호를 다시 입력하세요");
        continue;
      }
      break;
    }

    System.out.printf("이름 : ");
    String name = sc.nextLine();

    Member member = new Member(id, regDate, loginId, loginPw, name);
    members.add(member);

    System.out.printf("%d번 회원이 가입 했습니다\n", id);
  }

  private boolean isJoinableLoginId(String loginId) {
    int idx = getMemberIndexByLoginId(loginId);
    if (idx == -1) {
      return true;
    }

    return false;
  }

  private int getMemberIndexByLoginId(String loginId) {
    int i = 0;

    for (Member member : members) {
      if (member.loginId.equals(loginId)) {
        return i;
      }
      i++;
    }

    return -1;
  }

  private Member getMemberByLoginId(String loginId) {
    int idx = getMemberIndexByLoginId(loginId);

    if (idx == -1) {
      return null;
    }
    return members.get(idx);
  }
}
