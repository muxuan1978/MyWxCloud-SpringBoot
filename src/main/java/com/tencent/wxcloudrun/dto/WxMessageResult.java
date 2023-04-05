package com.tencent.wxcloudrun.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@Data
public class WxMessageResult implements Serializable
{
    @XStreamAlias("ToUserName")
    @JSONField(name = "ToUserName")
    private String toUserName;

    @XStreamAlias("FromUserName")
    @JSONField(name = "FromUserName")
    private String fromUserName;

    @XStreamAlias("CreateTime")
    @JSONField(name = "CreateTime")
    private Long createTime;

    @XStreamAlias("MsgType")
    @JSONField(name = "MsgType")
    private String msgType;

    @XStreamAlias("Content")
    @JSONField(name = "Content")
    private String content;

    @XStreamAlias("MsgId")
    @JSONField(name = "MsgId")
    private String msgId;
    //
    @XStreamAlias("Title")
    @JSONField(name = "Title")
    private String title;

    @XStreamAlias("Description")
    @JSONField(name = "Description")
    private String description;

    @XStreamAlias("Url")
    @JSONField(name = "Url")
    private String url;

    /**
     * 订阅或者取消订阅的事件
     */
    @XStreamAlias("Event")
    @JSONField(name = "Event")
    private String event;

    @XStreamAlias("EventKey")
    @JSONField(name = "EventKey")
    private String eventkey;

}
