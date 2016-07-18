package com.jsyh.xjd.umeng.share;

import android.app.Activity;

import com.jsyh.xjd.R;
import com.jsyh.xjd.config.ConfigValue;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * Created by sks on 2015/10/16.
 */
public class Share {
    private Activity context;
    private String title;  //分享的标题
    private String content;//分享的内容
    private String url;    //跳转的url
    private String image;  //分享时显示的图片
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService(ConfigValue.DESCRIPTOR);

    public Share(Activity context) {
        this.context = context;
        mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
        //mController.openShare(context, false);
        // 设置分享的内容

        // 配置需要分享的相关平台
        configPlatforms();
    }

    //设置分享内容
    public void setContent(String title, String content, String url, String image){
        this.title = title;
        this.content = content;
        this.url = url;
        this.image = image;
        setShareContent();
    }


    /**
     * 配置分享平台参数</br>
     */
    private void configPlatforms() {
        // 添加微信、微信朋友圈平台
        addWXPlatform();
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加腾讯微博SSO授权
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
        // 添加QQ、QZone平台
        addQQQZonePlatform();

    }

    /**
     * @return
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     * image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 .网页地址必须以"http://"开头 .
     * 网页地址必须以"http://"开头 .重要的事情要说三遍    title :
     * 要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     * : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     */
    private void addQQQZonePlatform() {
        /*String appId = ConfigValue.QQAPP_ID;
        String appKey = ConfigValue.QQAPP_KEY;*/
        String appId = context.getResources().getString(R.string.QQ_APP_ID);
        String appKey = context.getResources().getString(R.string.QQ_AppSecret);
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(context,
                appId, appKey);
        qqSsoHandler.setTitle(title);
        qqSsoHandler.setTargetUrl(url);
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(context, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }

    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    public void addWXPlatform() {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        /*String appId = ConfigValue.WXAPP_ID;
        String appSecret = ConfigValue.WXAPP_SECRET;*/
        String appId = context.getResources().getString(R.string.WX_APP_ID);
        String appSecret = context.getResources().getString(R.string.WX_APPSecret);
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }

    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent() {
        // 配置SSO
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

        UMImage urlImage = new UMImage(context,
                image);

        String appId = context.getResources().getString(R.string.QQ_APP_ID);
        String appKey = context.getResources().getString(R.string.QQ_AppSecret);
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(context,
                appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareContent(content);

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent
                .setShareContent(content);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(url);
        weixinContent.setShareMedia(urlImage);
        mController.setShareMedia(weixinContent);
        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia
                .setShareContent(content);
        circleMedia.setTitle(title);
        circleMedia.setShareMedia(urlImage);
        // circleMedia.setShareMedia(uMusic);
        // circleMedia.setShareMedia(video);
        circleMedia.setTargetUrl(url);
        mController.setShareMedia(circleMedia);

        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(content);
        qzone.setTargetUrl(url);
        qzone.setTitle(title);
        qzone.setShareMedia(urlImage);
        // qzone.setShareMedia(uMusic);
        mController.setShareMedia(qzone);

        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(content);
        qqShareContent.setTitle(title);
        qqShareContent.setShareMedia(urlImage);
        qqShareContent.setTargetUrl(url);
        mController.setShareMedia(qqShareContent);
    }
}
