package com.tencent.wxcloudrun.openai.model;

import lombok.Data;

@Data
public class ChatChoice
{
    Integer index;
    ChatMessage message;
    String finish_reason;
}
