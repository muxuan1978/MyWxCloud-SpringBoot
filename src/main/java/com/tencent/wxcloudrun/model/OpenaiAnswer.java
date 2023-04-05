package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OpenaiAnswer implements Serializable {

  private Long id;

  private String openId;
  private String question;
  private String answer;
  private Integer readed;
  private LocalDateTime createTime;
  private LocalDateTime readTime;

  public OpenaiAnswer(Long id, String openId, String question)
  {
    this.id = id;
    this.openId = openId;
    this.question = question;
  }
}
