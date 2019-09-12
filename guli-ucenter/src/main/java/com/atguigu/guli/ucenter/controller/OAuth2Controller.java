package com.atguigu.guli.ucenter.controller;

import com.atguigu.guli.exception.GuliException;
import com.atguigu.guli.ucenter.config.OAuth2Properties;
import com.atguigu.guli.ucenter.entity.Member;
import com.atguigu.guli.ucenter.service.MemberService;
import com.atguigu.guli.ucenter.util.HttpclientUtil;
import com.atguigu.guli.ucenter.util.JwtUtils;
import com.atguigu.guli.ucenter.vo.LoginInfoVo;
import com.atguigu.guli.vo.ResultSet;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class OAuth2Controller {

    private final OAuth2Properties oAuth2Properties;

    private final MemberService memberService;

    @Autowired
    public OAuth2Controller(OAuth2Properties oAuth2Properties, MemberService memberService) {
        this.oAuth2Properties = oAuth2Properties;
        this.memberService = memberService;
    }

    /**
     * 微信开放平台回调的地址
     * 可以获取到code;
     * 通过code,appid,appsecret再次发送请求获取access_token和openid
     * 通过access_token和openid可以获取个人详细信息
     * <p>
     * session中存储state.用于核对回调的地址是否是真的,防止csrf攻击（跨站请求伪造攻击）
     */
    @GetMapping("login")
    public String OAuth2Login(HttpSession session) {

        String codeUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=%S&scope=%s&state=%s#wechat_redirect";
        try {
            String redirectUrl = URLEncoder.encode(oAuth2Properties.getRedirectUrl(), "utf-8");
            codeUrl = String.format(codeUrl, oAuth2Properties.getAppId(), redirectUrl, "code", "snsapi_login", "guli");
            session.setAttribute("state", "guli");
            String stateOfSession = (String) session.getAttribute("state");
            System.out.println(stateOfSession + " " + session.getId());
            return "redirect:" + codeUrl;
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20002, "redirectUrl格式化发生异常");
        }
    }

    /**
     * @return jwt口令
     */
    @GetMapping("callback")
    public String getInfo(String code, String state, HttpSession session) {
        code = code.trim();
        state = state.trim();
        String stateOfSession = (String) session.getAttribute("state");
        System.out.println(session.getId());
        System.out.println(code + " " + state + " " + stateOfSession);
        if (!StringUtils.isEmpty(code)) {
            // 使用code和appid和secret和grant_type再次请求微信开放平台,获取到access_token和openid
            String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
            accessTokenUrl = String.format(accessTokenUrl, oAuth2Properties.getAppId(), oAuth2Properties.getAppSecret(), code);
            try {
                String accessTokenJson = HttpclientUtil.doGet(accessTokenUrl);
                Gson gson = new Gson();
                HashMap resultMap = gson.fromJson(accessTokenJson, HashMap.class);
                if (resultMap.get("errcode") != null) {
                    throw new GuliException(20002, "获取数据错误");
                }
                String accessToken = (String) resultMap.get("access_token");
                String openid = (String) resultMap.get("openid");
                if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openid)) {
                    throw new GuliException(20002, "返回值为空");
                }

                // 判断是新添加用户还是已存在用户
                Member member = memberService.getByOpenid(openid);
                if (member == null) {
                    // 新登录用户,再次获取用户详细信息
                    String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
                    userInfoUrl = String.format(userInfoUrl, accessToken, openid);
                    String userInfoJson = HttpclientUtil.doGet(userInfoUrl);
                    HashMap userInfoMap = gson.fromJson(userInfoJson, HashMap.class);
                    // 保存到数据库
                    Member member1 = new Member();
                    member1.setAvatar((String) userInfoMap.get("headimgurl"));
                    member1.setOpenid(openid);
                    member1.setNickname((String) userInfoMap.get("nickname"));
                    memberService.save(member1);
                    member = member1;
                }
                // 生成jwt口令并重定向到登录页面
                String webToken = JwtUtils.geneJsonWebToken(member);
                return "redirect:http://localhost:3000?webToken=" + webToken;
            } catch (Exception e) {
                throw new GuliException(20002, "获取accessToken时发生异常");
            }
        } else {
            throw new GuliException(20002, "callback未获取到任何有效参数");
        }
    }

    @ResponseBody
    @GetMapping("getInfo")
    public ResultSet getUserInfoByToken(String webToken) {

        Claims claims = JwtUtils.checkJWT(webToken);
        String id = (String) claims.get("id");
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");

        LoginInfoVo loginInfoVo = new LoginInfoVo();
        loginInfoVo.setId(id);
        loginInfoVo.setNickname(nickname);
        loginInfoVo.setAvatar(avatar);
        return ResultSet.ok().data("loginInfo", loginInfoVo);
    }
}
