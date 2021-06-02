package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    // 특정 파라미터를 지원하는지 판단함.
    public boolean supportsParameter(MethodParameter parameter) {//(1)

        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) !=null;
        // class가 SessionUser.class인 경우 ture를 반환함
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;

    }

    @Override
    // 파라미터에 전달할 객체를 생성, 여기에서는 세션에서 객체를 가져옴
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}