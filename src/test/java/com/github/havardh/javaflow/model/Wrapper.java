package com.github.havardh.javaflow.model;

import com.github.havardh.javaflow.model.packaged.PackagedMember;

public class Wrapper {
  Member member;
  PackagedMember packagedMember;

  public Member getMember() {
    return member;
  }

  public PackagedMember getPackagedMember() {
    return packagedMember;
  }
}
