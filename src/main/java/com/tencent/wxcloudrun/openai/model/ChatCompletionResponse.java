package com.tencent.wxcloudrun.openai.model;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinitions;
import java.util.List;

@Data
public class ChatCompletionResponse
{
    String id;
    String object;
    Long created;
    List<ChatChoice> choices;
    ChatUsage usage;
}
