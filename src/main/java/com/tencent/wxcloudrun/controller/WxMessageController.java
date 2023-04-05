package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.WxMessageRequest;
import com.tencent.wxcloudrun.dto.WxMessageResult;
import com.tencent.wxcloudrun.model.Counter;
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
  final Logger logger;

  public WxMessageController() {
    this.logger = LoggerFactory.getLogger( WxMessageController.class);
  }

  @PostMapping(value = "/wx/message")
  public void WxMessage(@RequestBody String str, HttpServletResponse response) throws IOException
  {
    logger.info("/wx/message post request str : {}", str);
    WxMessageRequest request = JSONObject.parseObject( str, WxMessageRequest.class );
    logger.info("/wx/message post request obj : {}", JSON.toJSONString(request));

    WxMessageResult result = new WxMessageResult();
    result.setToUserName(request.getFromUserName());
    result.setFromUserName(request.getToUserName());
    result.setCreateTime(System.currentTimeMillis() / 1000);
    result.setMsgType("text");
    result.setContent("我收到了：" + request.getContent());

    XStream xstream = new XStream();
    xstream.alias( "xml", WxMessageResult.class );
    xstream.processAnnotations(WxMessageResult.class);
    xstream.setClassLoader(WxMessageResult.class.getClassLoader());
    String xml = request.getContent().contains( "xml" ) ? xstream.toXML(result) : JSON.toJSONString( result );

    logger.info("/wx/message result : {}", xml);
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.print(xml);
    out.close();
  }
  
}