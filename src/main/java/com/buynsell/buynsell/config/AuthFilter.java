package com.buynsell.buynsell.config;

import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.UserInfo;
import com.buynsell.buynsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class AuthFilter implements Filter {

    private static final Logger log = Logger.getLogger(AuthFilter.class.getName());

    @Autowired
    AuthKeys authKeys;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationTokenUtil authenticationTokenUtil;

    @Autowired
    UserInfo userInfo;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Initiating AuthFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("Authenticating User from AuthFilter");
        try{
            String token = request.getHeader("token");

            String usernameOrEmail = authenticationTokenUtil.getUsernameOrEmailFromToken(token, authKeys.getTokenSecretKey());
            Optional<User> user = userService.findByUsernameOrEmail(usernameOrEmail);
            if (!user.isPresent()) {
                log.info("User not logged in or invalid token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            } else {
                log.info("User logged in");
                userInfo.setEmail(user.get().getEmail());
                userInfo.setUsername(user.get().getUsername());
                filterChain.doFilter(request, response);
            }
        } catch (NullPointerException ex){
            log.info("No Auth token found");
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not found");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
