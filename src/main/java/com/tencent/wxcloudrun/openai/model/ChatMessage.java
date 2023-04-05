package com.tencent.wxcloudrun.openai.model;

import lombok.Data;

@Data
public class ChatMessage
{
    private String role;
    private String content;
}
