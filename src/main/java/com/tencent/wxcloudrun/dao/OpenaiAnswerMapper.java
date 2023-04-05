package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.OpenaiAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OpenaiAnswerMapper
{
    void insertQuestion(OpenaiAnswer answer);
    void updateAnswer(OpenaiAnswer answer);
    List<OpenaiAnswer> selectUnReaded(@Param("openId") String openId);
    void updateReadFlag(@Param("openId") String openId);
}
