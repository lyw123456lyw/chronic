package com.hzsf.chronicanalysis.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzsf.chronicanalysis.ResponseResult;
import com.hzsf.chronicanalysis.ResponseStatusCode;
import com.hzsf.chronicanalysis.response.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class RequestUtils {

    public static String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders("Authorization");
        while (headers.hasMoreElements()) {
//            String value = headers.nextElement();
//            if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
//                String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
//                request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_TYPE,
//                        value.substring(0, OAuth2AccessToken.BEARER_TYPE.length()).trim());
//                int commaIndex = authHeaderValue.indexOf(',');
//                if (commaIndex > 0) {
//                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
//                }
//                return authHeaderValue;
//            }
        }

        return null;
    }

    public static void customResponse(HttpServletResponse response, ResponseStatusCode rsc, Object e) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(rsc.getCode());
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        out.write(objectMapper.writeValueAsString(e));
        out.flush();
        out.close();
    }
}
