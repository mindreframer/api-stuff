package com.wordnik.test.swagger.core;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum TestEnum {
  @XmlEnumValue("PRIVATE")PRIVATE,
  @XmlEnumValue("PUBLIC")PUBLIC,
  @XmlEnumValue("SYSTEM")SYSTEM,
  @XmlEnumValue("INVITE_ONLY")INVITE_ONLY;

  public String toString() {
    switch (this) {
      case PRIVATE:
        return "PRIVATE";
      case PUBLIC:
        return "PUBLIC";
      case SYSTEM:
        return "SYSTEM";
      case INVITE_ONLY:
        return "INVITE_ONLY";
    }
    throw new RuntimeException("Unknown Type " + this.getClass().getName());
  }
}
