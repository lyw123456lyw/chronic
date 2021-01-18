package com.hzsf.chronicanalysis.config.response;

import com.hzsf.chronicanalysis.response.FR;
import com.hzsf.chronicanalysis.response.R;
import com.hzsf.chronicanalysis.response.SR;
import freemarker.template.TemplateModelException;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * @Describtion 全局统一返回以及异常处理
 * 李依伟
 */
@RestControllerAdvice(basePackages = "com.hzsf")
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 通过条件判断决定对那些controller中返回的类型进行处理
     * @param methodParameter
     * @param httpMessageConvert
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> httpMessageConvert) {
        //如果返回类型是Response则不处理，交给zy-core封装的统一返回处理
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType ,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        // 此处获取到request 为特殊需要的时候处理使用
		HttpServletRequest request = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        // 下面处理统一返回结果（统一code、msg、sign 加密等）
        Boolean b  = selectedConverterType == MappingJackson2HttpMessageConverter.class;
        Boolean b1 = selectedContentType.equals(MediaType.APPLICATION_JSON) || selectedContentType.equals(MediaType.APPLICATION_JSON_UTF8);
        if (b && b1) {
            if (body == null) {
                return null;
            } else if (returnType.getExecutable().getDeclaringClass().isAssignableFrom(BasicErrorController.class)) {
                return body;
            }else{
                return SR.success(body);
            }
        }
        return body;
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            TemplateModelException.class,
            SQLException.class
    })
    public R handleHttpMessageNotReadableException(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   Exception e){
        R<String> fail = FR.fail(e.getCause().getMessage());
        return fail;
    }

}
