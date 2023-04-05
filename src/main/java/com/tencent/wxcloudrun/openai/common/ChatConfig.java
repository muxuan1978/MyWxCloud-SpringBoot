package com.tencent.wxcloudrun.openai.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ChatConfig
{
    @Value( "${openai.baseUrl:}" )
    String baseUrl;

    @Value( "${openai.apiKey:}" )
    String apiKey;
}
