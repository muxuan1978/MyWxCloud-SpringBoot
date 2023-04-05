package com.tencent.wxcloudrun.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.WxConstant;
import com.tencent.wxcloudrun.dao.OpenaiAnswerMapper;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.WxMessageRequest;
import com.tencent.wxcloudrun.dto.WxMessageResult;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.OpenaiAnswer;
import com.tencent.wxcloudrun.openai.OpenAIUtils;
import com.tencent.wxcloudrun.service.CounterService;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/**
 * 微信消息
 */
@RestController
public class WxMessageController
{
    @Autowired
    OpenAIUtils openAIUtils;
    @Autowired
    OpenaiAnswerMapper answerMapper;

    final Logger logger;

    public WxMessageController()
    {
        this.logger = LoggerFactory.getLogger( WxMessageController.class );
    }

    @PostMapping(value = "/wx/message")
    public void WxMessage(@RequestBody String str, HttpServletResponse response) throws Exception
    {
        logger.info( "/wx/message post request str : {}", str );

        WxMessageRequest request = JSONObject.parseObject( str, WxMessageRequest.class );
        String resultMessage = "不支持的消息类型";
        if (WxConstant.MsgType.TEXT.equals( request.getMsgType() ))
        {
            if (request.getContent().equals( "答案" ))
            {
                List<OpenaiAnswer> list = answerMapper.selectUnReaded( request.getFromUserName() );
                if (list != null && list.size() > 0)
                {
                    resultMessage = "";
                    for (OpenaiAnswer answer : list)
                    {
                        resultMessage = resultMessage + "\n您的问题是：" + answer.getQuestion();
                        resultMessage = resultMessage + "\n答案是：" + answer.getAnswer();
                    }
                    answerMapper.updateReadFlag( request.getFromUserName() );
                }
                else
                {
                    resultMessage = "抱歉，暂时没有答案，请继续提问，或者稍后再查询！";
                }
            }
            else
            {
                OpenaiAnswer answer = new OpenaiAnswer( IdUtil.getSnowflake().nextId(), request.getFromUserName(), request.getContent() );
                answerMapper.insertQuestion( answer );
                // 异步调用，先返回
                String aiResult = openAIUtils.invoke( answer, request.getFromUserName(), request.getContent() );

                resultMessage = "收到您的问题了，正在紧张思考，一会发【答案】会告诉您结果";
            }
        }

        WxMessageResult wxResult = new WxMessageResult();
        wxResult.setToUserName( request.getFromUserName() );
        wxResult.setFromUserName( request.getToUserName() );
        wxResult.setCreateTime( System.currentTimeMillis() / 1000 );
        wxResult.setMsgType( WxConstant.MsgType.TEXT );
        wxResult.setContent( resultMessage );
        String result = JSON.toJSONString( wxResult );

        logger.info( "/wx/message result : {}", result );
        // 输出
        response.setCharacterEncoding( "UTF-8" );
        PrintWriter out = response.getWriter();
        out.print( result );
        out.close();
    }

}