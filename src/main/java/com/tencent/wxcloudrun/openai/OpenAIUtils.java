package com.tencent.wxcloudrun.openai;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.openai.common.ChatConfig;
import com.tencent.wxcloudrun.openai.common.ChatConstant;
import com.tencent.wxcloudrun.openai.model.ChatCompletionRequestBody;
import com.tencent.wxcloudrun.openai.model.ChatCompletionResponse;
import com.tencent.wxcloudrun.openai.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class OpenAIUtils
{
    private static String  baseUrl = "http://ec2-52-15-239-174.us-east-2.compute.amazonaws.com:9877";
    private static String  apiKey = "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";

    // 暂不考虑上下文
    public static String invoke(String openId, String content) throws Exception
    {
        ChatCompletionRequestBody request = new ChatCompletionRequestBody();
        request.setModel( ChatConstant.Model.GPT_35_TURBO );
        request.setMessages( newChatMessageList(content) );
        String requestStr = JSON.toJSONString( request );

        HttpRequest http = HttpUtil.createPost( baseUrl );
        http.header( "Content-Type", "application/json" );
        http.bearerAuth( apiKey );
        String resultStr = http.body( requestStr ).execute().body();

        ChatCompletionResponse result = JSONObject.parseObject( resultStr, ChatCompletionResponse.class );
        if (result != null && result.getChoices().size() > 0) {
            return result.getChoices().get( 0 ).getMessage().getContent();
        }
        return "没有得到回复";
    }


    private static List<ChatMessage> newChatMessageList(String content)
    {
        List<ChatMessage> list = new ArrayList<>(  );
        ChatMessage msg = new ChatMessage();
        msg.setRole( "user" );
        msg.setContent( content );
        list.add( msg );
        return list;
    }
}
