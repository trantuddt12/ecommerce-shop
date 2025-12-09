package com.ttl.core.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.ttl.common.constant.ITagCode;
import com.ttl.common.exception.ErrorMessageLoader;
import com.ttl.core.handler.JwtAuthenticationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ErrorMessageLoader mvErrorLoader; // inject messages

    public JwtAuthenticationEntryPoint(ErrorMessageLoader mvErrorLoader) {
        this.mvErrorLoader = mvErrorLoader;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String errorCode = ITagCode.TOKEN_INVALID; // default: invalid
        if (authException instanceof JwtAuthenticationException jwtEx) {
            errorCode = jwtEx.getErrorCode();
        } else if (authException.getMessage() != null) {
            // fallback if message contains code
            errorCode = authException.getMessage();
        }

        // map error code to http status (see section 6 for options)
        int httpStatus = mapErrorCodeToHttpStatus(errorCode);

        String message = mvErrorLoader.getMessage(errorCode);
        response.setStatus(httpStatus);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("""
            {
              "errorCode": "%s",
              "error": "%s",
              "status": "%d %s"
            }
        """.formatted(errorCode, message, httpStatus, HttpStatusText(httpStatus)));
    }

    private int mapErrorCodeToHttpStatus(String errorCode) {
        return switch (errorCode) {
            case ITagCode.TOKEN_EXPIRED -> 498; // optional: expired token (non-standard code)
            case ITagCode.TOKEN_SIGNATURE_INVALID -> 401; // signature invalid
            case ITagCode.TOKEN_INVALID, ITagCode.TOKEN_MALFORMED, ITagCode.TOKEN_UNSUPPORTED, ITagCode.TOKEN_EMPTY-> 401;
            default -> 401;
        };
    }

    private String HttpStatusText(int status) {
        // minimal mapping for human readable status
        return switch (status) {
            case 498 -> "TOKEN EXPIRED";
            case 401 -> "UNAUTHORIZED";
            default -> "UNAUTHORIZED";
        };
    }
}

