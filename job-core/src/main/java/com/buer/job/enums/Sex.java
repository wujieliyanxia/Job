package com.buer.job.enums;

public enum Sex {
  MAN(1),
  FEMALE(2),
  ;
  public int code;

  Sex(int code) {
    this.code = code;
  }
}
