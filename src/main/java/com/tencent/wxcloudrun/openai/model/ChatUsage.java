package com.tencent.wxcloudrun.openai.model;

import lombok.Data;

@Data
public class ChatUsage
{
    Integer prompt_tokens;
    Integer completion_tokens;
    Integer total_tokens;
}
