package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class WxMessageResult
{
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;
}
