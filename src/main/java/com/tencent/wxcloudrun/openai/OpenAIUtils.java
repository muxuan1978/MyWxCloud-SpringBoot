package com.tencent.wxcloudrun.openai;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.WxConstant;
import com.tencent.wxcloudrun.dto.WxMessageResult;
import com.tencent.wxcloudrun.openai.common.ChatConfig;
import com.tencent.wxcloudrun.openai.common.ChatConstant;
import com.tencent.wxcloudrun.openai.model.ChatCompletionRequestBody;
import com.tencent.wxcloudrun.openai.model.ChatCompletionResponse;
import com.tencent.wxcloudrun.openai.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIUtils
{
    @Autowired
    ChatConfig config;
    Logger logger = LoggerFactory.getLogger( OpenAIUtils.class);

    // 暂不考虑上下文
    @Async
    public String invoke(String openId, String content) throws Exception
    {
        ChatCompletionRequestBody request = new ChatCompletionRequestBody();
        request.setModel( ChatConstant.Model.GPT_35_TURBO );
        request.setMessages( newChatMessageList(content) );
        String requestStr = JSON.toJSONString( request );

        HttpRequest http = HttpUtil.createPost( config.getBaseUrl() );
        http.header( "Content-Type", "application/json" );
        http.bearerAuth( config.getApiKey() );

        logger.info( "openai request : " + requestStr );
        String resultStr = http.body( requestStr ).execute().body();
        logger.info( "openai result : " + resultStr );

        ChatCompletionResponse result = JSONObject.parseObject( resultStr, ChatCompletionResponse.class );
        if (result != null && result.getChoices() != null && result.getChoices().size() > 0) {
            String message =  result.getChoices().get( 0 ).getMessage().getContent();
            if (StrUtil.isNotEmpty(message))
            {
                String wxMessage = JSONUtil.createObj()
                        .putOnce( "touser", openId )
                        .putOnce( "msgtype", WxConstant.MsgType.TEXT )
                        .putOnce( "text", JSONUtil.createObj().putOnce( "content", message ) )
                        .toString();
                logger.info( "wechat request : " + wxMessage );
                String wxResult = HttpRequest.post( "http://api.weixin.qq.com/cgi-bin/message/custom/send" )
                        .body( wxMessage )
                        .execute()
                        .body();
                logger.info( "wechat result : " + wxResult );
            }
            return "";
        }
        return "没有得到回复";
    }


    private List<ChatMessage> newChatMessageList(String content)
    {
        List<ChatMessage> list = new ArrayList<>(  );
        ChatMessage msg = new ChatMessage();
        msg.setRole( "user" );
        msg.setContent( content );
        list.add( msg );
        return list;
    }
}
