package com.tencent.wxcloudrun.openai.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChatCompletionRequestBody
{
    private String model;
    private List<ChatMessage> messages;
    private Double temperature;
    private Double top_p;
    private Integer n;

    private List<String> stop;
    private Integer max_tokens;
    private Double presence_penalty;
    private Double frequency_penalty;
    private Map<String, Integer> logit_bias;
    private String user;
}
