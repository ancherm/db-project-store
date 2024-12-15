package ru.chermashentsev.dbproductstore.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class AuthenticationHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        System.out.println("Roles: " + authentication.getAuthorities());

        authorities.forEach(authority -> {
            if (authority.getAuthority().equals("ROLE_admin")) {
                try {
                    response.sendRedirect("/admin/index");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (authority.getAuthority().equals("ROLE_manager")) {
                try {
                    response.sendRedirect("/");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
