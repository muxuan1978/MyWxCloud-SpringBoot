package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.WxMessageRequest;
import com.tencent.wxcloudrun.dto.WxMessageResult;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 微信消息
 */
@RestController
public class WxMessageController
{

  final CounterService counterService;
  final Logger logger;

  public WxMessageController(@Autowired CounterService counterService) {
    this.counterService = counterService;
    this.logger = LoggerFactory.getLogger( WxMessageController.class);
  }


  @PostMapping(value = "/wx/message")
  ApiResponse create(@RequestBody WxMessageRequest request) {
    logger.info("/wx/message post request : {}", request.toString());

    WxMessageResult result = new WxMessageResult();
    result.setToUserName(request.getFromUserName());
    result.setFromUserName(request.getToUserName());
    result.setCreateTime(System.currentTimeMillis() / 1000);
    result.setMsgType("text");
    result.setContent("我收到了：" + request.getContent());

    return ApiResponse.ok(result);
  }
  
}