package com.tencent.wxcloudrun.openai.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig
{
    @Value( "${openai.baseUrl}" )
    String baseUrl;

    @Value( "${openai.apiKey}" )
    String apiKey;
}
