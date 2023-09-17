package com.KoreaIT.java.AM.dao;

import com.KoreaIT.java.AM.dto.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberDao extends Dao {
  public List<Member> members;

  public MemberDao() {
    members = new ArrayList<>();
  }

  public void add(Member member) {
    members.add(member);
    lastId++;
  }

  public int getNewId() {
    return lastId + 1;
  }
}
