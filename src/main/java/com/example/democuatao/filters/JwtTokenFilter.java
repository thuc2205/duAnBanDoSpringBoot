package com.example.democuatao.filters;

import com.example.democuatao.component.JwtTokenUtil;
import com.example.democuatao.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter{
    @Value("${api.prefix}")
    private String api;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isByPassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            String authenticationHeader = request.getHeader("Authorization");

            if(authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            final String token = authenticationHeader.substring(7);
            String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);

            if (phoneNumber == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);

            if (userDetails == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            }

            // Tạo đối tượng Authentication từ UserDetails
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // Đặt Authentication vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // Tiếp tục chuỗi filter
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isByPassToken(@NotNull HttpServletRequest request) {
        final List<Map<String, String>> byPassTokens = new ArrayList<>();
        byPassTokens.add(Map.of("api/thuc/layout/product", "GET"));
        byPassTokens.add(Map.of("api/thuc/productDetail/images", "GET"));
        byPassTokens.add(Map.of("api/thuc/layout/size", "GET"));
        byPassTokens.add(Map.of("api/thuc/layout/xuatxu", "GET"));
        byPassTokens.add(Map.of("api/thuc/layout/color", "GET"));
        byPassTokens.add(Map.of("api/thuc/layout", "GET"));
        byPassTokens.add(Map.of("api/thuc/layout/addProduct", "GET"));
        byPassTokens.add(Map.of("api/thuc/layout/login", "GET"));
        byPassTokens.add(Map.of("api/thuc/us/home", "GET"));
        byPassTokens.add(Map.of("api/thuc/us/dssp", "GET"));
        byPassTokens.add(Map.of("api/thuc/us/login/go", "POST"));
        byPassTokens.add(Map.of("api/thuc/us/logout", "GET"));
        byPassTokens.add(Map.of("api/thuc/layout/xuatxu", "GET"));
        byPassTokens.add(Map.of("api/thuc/layout/brand", "GET"));
        byPassTokens.add(Map.of("api/thuc/categories", "GET"));

        for (Map<String, String> token : byPassTokens) {
            String path = token.keySet().iterator().next();
            String method = token.get(path);
            if (request.getServletPath().contains(path) && request.getMethod().equals(method)) {
                return true;
            }
        }
        return false;
    }
}
