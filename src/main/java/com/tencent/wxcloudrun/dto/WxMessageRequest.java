package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class WxMessageRequest
{
    private Long MsgId;
    private String MsgType;
    private String FromUserName;
    private String ToUserName;
    private Long CreateTime;

    private String Content;
    private String MediaId;
    private String PicUrl;
    private String Format;
    private String Title;
    private String Description;

    private String Location_X;
    private String Location_Y;
    private String Scale;
    private String Label;
    private String Url;
}
