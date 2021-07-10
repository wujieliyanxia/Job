package com.buer.job.enums;

public enum Sex {
  MAN(1),
  FEMALE(2),
  ;
  public int code;

  Sex(int code) {
    this.code = code;
  }

  public static Sex fromCode(String code) {
    for (Sex sex : Sex.values()) {
      if (sex.code == Integer.valueOf(code)) {
        return sex;
      }
    }
    return null;
  }
}
