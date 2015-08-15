package cn.szzsi.controller;

import cn.szzsi.util.SessionUtil;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;

public class WeixinApiController extends ApiController{

    public ApiConfig getApiConfig(){
        return SessionUtil.getApiConfig(this);
    }

    /**
     * 获取公众号菜单
     */
    public void getMenu(){
        ApiResult apiResult = MenuApi.getMenu();
        if(apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 创建菜单
     */
    public void createMenu(){
        String str = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"进入理财\",\n" +
                "            \"url\": \"http://m.bajie8.com/bajie/enter\",\n" +
                "            \"type\": \"view\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"安全保障\",\n" +
                "            \"key\": \"112\",\n" +
                "\t    \"type\": \"click\"\n" +
                "        },\n" +
                "        {\n" +
                "\t    \"name\": \"使用帮助\",\n" +
                "\t    \"url\": \"http://m.bajie8.com/footer/cjwt\",\n" +
                "\t    \"type\": \"view\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        ApiResult apiResult = MenuApi.createMenu(str);
        if(apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 获取公众号关注用户
     */
    public void getFollowers(){
        ApiResult apiResult = UserApi.getFollows();
        renderText(apiResult.getJson());
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(){
        ApiResult apiResult = UserApi.getUserInfo("ohbweuNYB_heu_buiBWZtwgi4xzU");
        renderText(apiResult.getJson());
    }

    /**
     * 发送模板消息
     */
//    public void sendMsg(){
//        String str = " {\n" +
//                "           \"touser\":\"ohbweuNYB_heu_buiBWZtwgi4xzU\",\n" +
//                "           \"template_id\":\"9SIa8ph1403NEM3qk3z9-go-p4kBMeh-HGepQZVdA7w\",\n" +
//                "           \"url\":\"http://www.sina.com\",\n" +
//                "           \"topcolor\":\"#FF0000\",\n" +
//                "           \"data\":{\n" +
//                "                   \"first\": {\n" +
//                "                       \"value\":\"恭喜你购买成功！\",\n" +
//                "                       \"color\":\"#173177\"\n" +
//                "                   },\n" +
//                "                   \"keyword1\":{\n" +
//                "                       \"value\":\"去哪儿网发的酒店红包（1个）\",\n" +
//                "                       \"color\":\"#173177\"\n" +
//                "                   },\n" +
//                "                   \"keyword2\":{\n" +
//                "                       \"value\":\"1元\",\n" +
//                "                       \"color\":\"#173177\"\n" +
//                "                   },\n" +
//                "                   \"remark\":{\n" +
//                "                       \"value\":\"欢迎再次购买！\",\n" +
//                "                       \"color\":\"#173177\"\n" +
//                "                   }\n" +
//                "           }\n" +
//                "       }";
//        ApiResult apiResult = TemplateMsgApi.send(str);
//        renderText(apiResult.getJson());
//    }

    /**
     * 获取参数二维码
     */
    public void getQrcode(){
        String str = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
        ApiResult apiResult = QrcodeApi.create(str);
        renderText(apiResult.getJson());
        //        String str = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"123\"}}}";
        //        ApiResult apiResult = QrcodeApi.create(str);
        //        renderText(apiResult.getJson());
    }

    /**
     * 长链接转成短链接
     */
    public void getShorturl(){
        String str = "{\"action\":\"long2short\"," + "\"long_url\":\"http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}";
        ApiResult apiResult = ShorturlApi.getShorturl(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取客服聊天记录
     */
    public void getRecord(){
        String str = "{\n" +
                "    \"endtime\" : 987654321,\n" +
                "    \"pageindex\" : 1,\n" +
                "    \"pagesize\" : 10,\n" +
                "    \"starttime\" : 123456789\n" +
                " }";
        ApiResult apiResult = CustomServiceApi.getRecord(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取微信服务器IP地址
     */
    public void getCallbackIp(){
        ApiResult apiResult = CallbackIpApi.getCallbackIp();
        renderText(apiResult.getJson());
    }

    //	/**
    //	 * 实现父类抽方法，处理文本消息
    //	 * 本例子中根据消息中的不同文本内容分别做出了不同的响应，同时也是为了测试 jfinal weixin sdk的基本功能：
    //	 *     本方法仅测试了 OutTextMsg、OutNewsMsg、OutMusicMsg 三种类型的OutMsg，
    //	 *     其它类型的消息会在随后的方法中进行测试
    //	 */
    //	protected void processInTextMsg(InTextMsg inTextMsg) {
    //		String msgContent = inTextMsg.getContent().trim();
    //		// 帮助提示
    //		if ("help".equalsIgnoreCase(msgContent) || "帮助".equals(msgContent)) {
    //			OutTextMsg outMsg = new OutTextMsg(inTextMsg);
    //			outMsg.setContent(helpStr);
    //			render(outMsg);
    //		}
    //		// 图文消息测试
    //		else if ("news".equalsIgnoreCase(msgContent) || "新闻".equalsIgnoreCase(msgContent)) {
    //			OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
    //			outMsg.addNews("JFinal 2.0 发布,JAVA 极速 WEB+ORM 框架", "本星球第一个极速开发框架", "https://mmbiz.qlogo.cn/mmbiz/KJoUl0sqZFS0fRW68poHoU3v9ulTWV8MgKIduxmzHiamkb3yHia8pCicWVMCaFRuGGMnVOPrrj2qM13u9oTahfQ9A/0?wx_fmt=png", "http://mp.weixin.qq.com/s?__biz=MzA4NjM4Mjk2Mw==&mid=211063163&idx=1&sn=87d54e2992237a3f791f08b5cdab7990#rd");
    //			outMsg.addNews("JFinal 1.8 发布,JAVA 极速 WEB+ORM 框架", "现在就加入 JFinal 极速开发世界，节省更多时间去跟女友游山玩水 ^_^", "http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq1ibBkhSA1BibMuMxLuHIvUfiaGsK7CC4kIzeh178IYSHbYQ5eg9tVxgEcbegAu22Qhwgl5IhZFWWXUw/0", "http://mp.weixin.qq.com/s?__biz=MjM5ODAwOTU3Mg==&mid=200313981&idx=1&sn=3bc5547ba4beae12a3e8762ababc8175#rd");
    //			outMsg.addNews("JFinal 1.6 发布,JAVA 极速 WEB+ORM 框架", "JFinal 1.6 主要升级了 ActiveRecord 插件，本次升级全面支持多数源、多方言、多缓", "http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq0fcR8VmNCgugHXv7gVlxI6w95RBlKLdKUTjhOZIHGSWsGvjvHqnBnjIWHsicfcXmXlwOWE6sb39kA/0", "http://mp.weixin.qq.com/s?__biz=MjM5ODAwOTU3Mg==&mid=200121522&idx=1&sn=ee24f352e299b2859673b26ffa4a81f6#rd");
    //			render(outMsg);
    //		}
    //		// 音乐消息测试
    //		else if ("music".equalsIgnoreCase(msgContent) || "音乐".equals(msgContent)) {
    //			OutMusicMsg outMsg = new OutMusicMsg(inTextMsg);
    //			outMsg.setTitle("When The Stars Go Blue-Venke Knutson");
    //			outMsg.setDescription("建议在 WIFI 环境下流畅欣赏此音乐");
    //			outMsg.setMusicUrl("http://www.jfinal.com/When_The_Stars_Go_Blue-Venke_Knutson.mp3");
    //			outMsg.setHqMusicUrl("http://www.jfinal.com/When_The_Stars_Go_Blue-Venke_Knutson.mp3");
    //			outMsg.setFuncFlag(true);
    //			render(outMsg);
    //		}
    //		else if ("美女".equalsIgnoreCase(msgContent)) {
    //			OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
    //			outMsg.addNews(
    //					"JFinal 宝贝更新喽",
    //					"jfinal 宝贝更新喽，我们只看美女 ^_^",
    //					"https://mmbiz.qlogo.cn/mmbiz/KJoUl0sqZFRHa3VrmibqAXRfYPNdiamFnpPTOvXoxsFlRoOHbVibGhmHOEUQiboD3qXWszKuzWpibFxsVW1RmNB9hPw/0?wx_fmt=jpeg",
    //					"http://mp.weixin.qq.com/s?__biz=MzA4NjM4Mjk2Mw==&mid=211356950&idx=1&sn=6315a1a2848aa8cb0694bf1f4accfb07#rd");
    //			// outMsg.addNews("秀色可餐", "JFinal Weixin 极速开发就是这么爽，有木有 ^_^", "http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq2GJLC60ECD7rE7n1cvKWRNFvOyib4KGdic3N5APUWf4ia3LLPxJrtyIYRx93aPNkDtib3ADvdaBXmZJg/0", "http://mp.weixin.qq.com/s?__biz=MjM5ODAwOTU3Mg==&mid=200987822&idx=1&sn=7eb2918275fb0fa7b520768854fb7b80#rd");
    //
    //			render(outMsg);
    //		}
    //		else if ("视频教程".equalsIgnoreCase(msgContent) || "视频".equalsIgnoreCase(msgContent)) {
    //			renderOutTextMsg("\thttp://pan.baidu.com/s/1nt2zAT7  \t密码:824r");
    //		}
    //		// 其它文本消息直接返回原值 + 帮助提示
    //		else {
    //			renderOutTextMsg("\t文本消息已成功接收，内容为： " + inTextMsg.getContent() + "\n\n" + helpStr);
    //		}
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理图片消息
    //	 */
    //	protected void processInImageMsg(InImageMsg inImageMsg) {
    //		OutImageMsg outMsg = new OutImageMsg(inImageMsg);
    //		// 将刚发过来的图片再发回去
    //		outMsg.setMediaId(inImageMsg.getMediaId());
    //		render(outMsg);
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理语音消息
    //	 */
    //	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
    //		OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
    //		// 将刚发过来的语音再发回去
    //		outMsg.setMediaId(inVoiceMsg.getMediaId());
    //		render(outMsg);
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理视频消息
    //	 */
    //	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
    //		/* 腾讯 api 有 bug，无法回复视频消息，暂时回复文本消息代码测试
    //		OutVideoMsg outMsg = new OutVideoMsg(inVideoMsg);
    //		outMsg.setTitle("OutVideoMsg 发送");
    //		outMsg.setDescription("刚刚发来的视频再发回去");
    //		// 将刚发过来的视频再发回去，经测试证明是腾讯官方的 api 有 bug，待 api bug 却除后再试
    //		outMsg.setMediaId(inVideoMsg.getMediaId());
    //		render(outMsg);
    //		*/
    //		OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
    //		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inVideoMsg.getMediaId());
    //		render(outMsg);
    //	}
    //
    //	@Override
    //	protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg)
    //	{
    //		OutTextMsg outMsg = new OutTextMsg(inShortVideoMsg);
    //		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inShortVideoMsg.getMediaId());
    //		render(outMsg);
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理地址位置消息
    //	 */
    //	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
    //		OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
    //		outMsg.setContent("已收到地理位置消息:" +
    //							"\nlocation_X = " + inLocationMsg.getLocation_X() +
    //							"\nlocation_Y = " + inLocationMsg.getLocation_Y() +
    //							"\nscale = " + inLocationMsg.getScale() +
    //							"\nlabel = " + inLocationMsg.getLabel());
    //		render(outMsg);
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理链接消息
    //	 * 特别注意：测试时需要发送我的收藏中的曾经收藏过的图文消息，直接发送链接地址会当做文本消息来发送
    //	 */
    //	protected void processInLinkMsg(InLinkMsg inLinkMsg) {
    //		OutNewsMsg outMsg = new OutNewsMsg(inLinkMsg);
    //		outMsg.addNews("链接消息已成功接收", "链接使用图文消息的方式发回给你，还可以使用文本方式发回。点击图文消息可跳转到链接地址页面，是不是很好玩 :)" , "http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq1ibBkhSA1BibMuMxLuHIvUfiaGsK7CC4kIzeh178IYSHbYQ5eg9tVxgEcbegAu22Qhwgl5IhZFWWXUw/0", inLinkMsg.getUrl());
    //		render(outMsg);
    //	}
    //
    //	@Override
    //	protected void processInCustomEvent(InCustomEvent inCustomEvent)
    //	{
    //		System.out.println("processInCustomEvent() 方法测试成功");
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理关注/取消关注消息
    //	 */
    //	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
    //		OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
    //		outMsg.setContent("感谢关注 JFinal Weixin 极速开发服务号，为您节约更多时间，去陪恋人、家人和朋友 :) \n\n\n " + helpStr);
    //		// 如果为取消关注事件，将无法接收到传回的信息
    //		render(outMsg);
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理扫描带参数二维码事件
    //	 */
    //	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
    //		OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
    //		outMsg.setContent("processInQrCodeEvent() 方法测试成功");
    //		render(outMsg);
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理上报地理位置事件
    //	 */
    //	protected void processInLocationEvent(InLocationEvent inLocationEvent) {
    //		OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
    //		outMsg.setContent("processInLocationEvent() 方法测试成功");
    //		render(outMsg);
    //	}
    //
    //	@Override
    //	protected void processInMassEvent(InMassEvent inMassEvent)
    //	{
    //		System.out.println("processInMassEvent() 方法测试成功");
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理自定义菜单事件
    //	 */
    //	protected void processInMenuEvent(InMenuEvent inMenuEvent) {
    //		renderOutTextMsg("processInMenuEvent() 方法测试成功");
    //	}
    //
    //	/**
    //	 * 实现父类抽方法，处理接收语音识别结果
    //	 */
    //	protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
    //		renderOutTextMsg("语音识别结果： " + inSpeechRecognitionResults.getRecognition());
    //	}
    //
    //	// 处理接收到的模板消息是否送达成功通知事件
    //	protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
    //		String status = inTemplateMsgEvent.getStatus();
    //		renderOutTextMsg("模板消息是否接收成功：" + status);
    //	}
}

