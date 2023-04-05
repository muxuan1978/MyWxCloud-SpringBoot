package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.WxConstant;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.WxMessageRequest;
import com.tencent.wxcloudrun.dto.WxMessageResult;
import com.tencent.wxcloudrun.model.Counter;
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
import java.util.Optional;

/**
 * 微信消息
 */
@RestController
public class WxMessageController
{
  @Autowired
  OpenAIUtils openAIUtils;

  final Logger logger;
  public WxMessageController() {
    this.logger = LoggerFactory.getLogger( WxMessageController.class);
  }

  @PostMapping(value = "/wx/message")
  public void WxMessage(@RequestBody String str, HttpServletResponse response) throws Exception
  {
    logger.info("/wx/message post request str : {}", str);

    WxMessageRequest request = JSONObject.parseObject( str, WxMessageRequest.class );
    String result = "不支持的消息类型";
    if (WxConstant.MsgType.TEXT.equals( request.getMsgType()) )
    {
      String aiResult = openAIUtils.invoke( request.getFromUserName(), request.getContent() );
//      WxMessageResult wxResult = new WxMessageResult();
//      wxResult.setToUserName( request.getFromUserName() );
//      wxResult.setFromUserName( request.getToUserName() );
//      wxResult.setCreateTime( System.currentTimeMillis() / 1000 );
//      wxResult.setMsgType( WxConstant.MsgType.TEXT );
//      wxResult.setContent( aiResult );
//
//      result = JSON.toJSONString( wxResult );
      result = "success";
    }
    logger.info( "/wx/message result : {}", result );
    // 输出
    response.setCharacterEncoding( "UTF-8" );
    PrintWriter out = response.getWriter();
    out.print( result );
    out.close();
  }
  
}