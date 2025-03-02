package com.project.officequiz.security.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHandler {

    public void removeMessageFromSession() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            HttpSession session = servletRequestAttributes.getRequest().getSession();
            session.removeAttribute("errorMessage");
        }catch (Exception e) {
            //TODO: Handle logging and exceptions.
        }
    }
}
