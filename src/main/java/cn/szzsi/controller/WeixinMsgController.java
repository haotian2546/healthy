/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cn.szzsi.controller;

import cn.szzsi.model.Consulter;
import cn.szzsi.model.Customer;
import cn.szzsi.model.Message;
import cn.szzsi.model.Order;
import cn.szzsi.util.HuanxinUtil;
import cn.szzsi.util.SessionUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.log.Logger;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.MsgController;
import com.jfinal.weixin.sdk.jfinal.MsgInterceptor;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutCustomMsg;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;

public class WeixinMsgController extends MsgController{

    private static final Logger logger = Logger.getLogger(WeixinMsgController.class);

    @Clear
    @Before({MsgInterceptor.class})
    public void index(){
        super.index();
    }

    public ApiConfig getApiConfig(){
        return SessionUtil.getApiConfig(this);
    }

    protected void processInTextMsg(InTextMsg inTextMsg){
        Consulter consulter = Consulter.getByOpenId(inTextMsg.getFromUserName());
        Order order = Order.getServeringOrderByConId(consulter.getInt("id"));
        Message message = Message.createConsulterMessage(order,inTextMsg.getContent());
        Customer customer = order.getCustomer();
        if(customer != null){
            HuanxinUtil.sendMsg(customer,message);
        }
        renderText("");
    }

    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg){
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inVoiceMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInVideoMsg(InVideoMsg inVideoMsg){
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inVideoMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg){
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inShortVideoMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInLocationMsg(InLocationMsg inLocationMsg){
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inLocationMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInLinkMsg(InLinkMsg inLinkMsg){
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inLinkMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInCustomEvent(InCustomEvent inCustomEvent){
        logger.debug("测试方法：processInCustomEvent()");
        renderNull();
    }

    protected void processInImageMsg(InImageMsg inImageMsg){
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inImageMsg);
        render(outCustomMsg);
    }

    /**
     * 实现父类抽方法，处理关注/取消关注消息
     */
    protected void processInFollowEvent(InFollowEvent inFollowEvent){
        if(InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent())){
            logger.debug("关注：" + inFollowEvent.getFromUserName());
            OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
            outMsg.setContent("欢迎关注医疗咨询公众平台");
            render(outMsg);
            Consulter consulter = Consulter.getByOpenId(inFollowEvent.getFromUserName());
            ApiResult apiResult = UserApi.getUserInfo(inFollowEvent.getFromUserName());
            if(apiResult.getInt("subscribe") == 1){
                consulter.update(apiResult);
            }
        }
        // 如果为取消关注事件，将无法接收到传回的信息
        if(InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent())){
            renderText("");
            logger.debug("取消关注：" + inFollowEvent.getFromUserName());
        }
    }

    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent){
        if(InQrCodeEvent.EVENT_INQRCODE_SUBSCRIBE.equals(inQrCodeEvent.getEvent())){
            logger.debug("扫码未关注：" + inQrCodeEvent.getFromUserName());
            OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
            outMsg.setContent("感谢您的关注，二维码内容：" + inQrCodeEvent.getEventKey());
            render(outMsg);
        }
        if(InQrCodeEvent.EVENT_INQRCODE_SCAN.equals(inQrCodeEvent.getEvent())){
            logger.debug("扫码已关注：" + inQrCodeEvent.getFromUserName());
        }
    }

    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent){
        logger.debug("发送地理位置事件：" + inLocationEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
        outMsg.setContent("地理位置是：" + inLocationEvent.getLatitude());
        render(outMsg);
    }

    @Override
    protected void processInMassEvent(InMassEvent inMassEvent){
        logger.debug("测试方法：processInMassEvent()");
        renderNull();
    }

    /**
     * 实现父类抽方法，处理自定义菜单事件
     */
    protected void processInMenuEvent(InMenuEvent inMenuEvent){
        logger.debug("菜单事件：" + inMenuEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
        outMsg.setContent("菜单事件内容是：" + inMenuEvent.getEventKey());
        render(outMsg);
    }

    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults){
        logger.debug("语音识别事件：" + inSpeechRecognitionResults.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inSpeechRecognitionResults);
        outMsg.setContent("语音识别内容是：" + inSpeechRecognitionResults.getRecognition());
        render(outMsg);
    }

    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent){
        logger.debug("测试方法：processInTemplateMsgEvent()");
        renderNull();
    }

}






