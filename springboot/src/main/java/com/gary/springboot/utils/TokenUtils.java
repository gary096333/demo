package com.gary.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gary.springboot.entity.Admin;
import com.gary.springboot.service.IAdminService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j //報錯通過日誌打印
public class TokenUtils {

    private static IAdminService staticAdminService;


    @Resource
    private IAdminService adminService;

    //為了在靜態方法中使用SpringBoot中的類(賦值才能使用)
    //@PostConstruct 確保初始化代碼在依赖注入完成后執行
    @PostConstruct
    public void setUserService() {
        staticAdminService = adminService;
    }

    /**
     * 生成token
     *
     * @return
     */
    public static String genToken(String adminId, String sign) {
        return JWT.create().withAudience(adminId) // 將 user id 保存到 token 裡面,作為載荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 24)) // 24小時後token過期，(無token跳轉到/login)
                .sign(Algorithm.HMAC256(sign)); // 以 password 作為 token 的密鑰
    }

    public static String genTokens(String adminId, String sign) { //無過期時間
        return JWT.create().withAudience(adminId) // 將 user id 保存到 token 裡面,作為載荷
                .sign(Algorithm.HMAC256(sign)); // 以 password 作為 token 的密鑰
    }


    /**
     * 獲取當前登入的用戶信息
     *
     * @return user對象
     *  /admin?token=xxxx
     */
    public static Admin getCurrentAdmin() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //獲取token兩種方式，1.從Header 2.從Parameter(URL的參數 /admin?token=xxxx)
            token = request.getHeader("token");//從Header獲取
            if (StrUtil.isBlank(token)) { //header沒有參數，就從參數獲取
                token = request.getParameter("token");//從參數獲取
            }

            if (StrUtil.isBlank(token)) {
                log.error("獲取當前登入的token失敗， token: {}", token);  //當前面獲取token，兩種方式都失敗
                return null;
            }
            String adminId = JWT.decode(token).getAudience().get(0); //decode解開token，獲取Audience中 adminId的信息
            return staticAdminService.getById(Integer.valueOf(adminId));

        } catch (Exception e) {
            log.error("獲取當前登錄的管理員信息失敗, token={}", token,  e);
            return null;
        }
    }
}


