package com.KoreaIT.java.AM.dao;

import com.KoreaIT.java.AM.dto.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberDao {
  public List<Member> members;

  public MemberDao() {
    members = new ArrayList<>();
  }
}
