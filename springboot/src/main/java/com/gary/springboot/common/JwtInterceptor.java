package com.gary.springboot.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gary.springboot.entity.Admin;
import com.gary.springboot.exception.ServiceException;
import com.gary.springboot.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 攔截器設置
 */

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String ERROR_CODE_401 = "401";

    @Autowired
    private IAdminService adminService;

    // 獲取token兩種方式，1.從Header 2.從Parameter(URL的參數 /admin?token=xxxx)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }

        // 執行認證
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(ERROR_CODE_401, "無token，請重新登入");
        }

        // 獲取 token 中的adminId
        String adminId;
        Admin admin;
        try {
            adminId = JWT.decode(token).getAudience().get(0);
            // 根據token中的userid查詢數據庫
            admin = adminService.getById(Integer.parseInt(adminId));
        } catch (Exception e) {
            String errMsg = "token驗證失敗，請重新登入";
            log.error(errMsg + ", token=" + token, e);
            throw new ServiceException(ERROR_CODE_401, errMsg);
        }
        if (admin == null) {
            throw new ServiceException(ERROR_CODE_401, "用户不存在，請重新登入");
        }

        try {
            // 用戶密碼加簽驗證 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
            jwtVerifier.verify(token); // 驗證token
        } catch (JWTVerificationException e) {
            throw new ServiceException(ERROR_CODE_401, "token驗證失敗，請重新登入");
        }
        return true;
    }
}
