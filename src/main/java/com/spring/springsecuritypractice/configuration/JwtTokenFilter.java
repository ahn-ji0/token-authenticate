package com.spring.springsecuritypractice.configuration;

import com.spring.springsecuritypractice.service.UserService;
import com.spring.springsecuritypractice.token.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

   private final UserService userService;
   private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request의 header의 authorization 부분을 가져와 final String에 저장
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("1");
        //토큰이 없거나, Bearer 로 시작하지 않는 경우 다음 필터 호출
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        log.info("2");

        String token;
        try {
            token = authorizationHeader.split(" ")[1];
        } catch (Exception e){
            // 토큰 분리가 되지 않으면(적절하지 않은 토큰이면) 다음 필터 호출
            filterChain.doFilter(request,response);
            return;
        }
        log.info("3");

        if(JwtTokenUtils.isExpired(token,secretKey)){
            filterChain.doFilter(request,response);
            return;
        }
        log.info("4");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(JwtTokenUtils.getUserName(token,secretKey), null, List.of(new SimpleGrantedAuthority("User")));
        log.info("5");

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        log.info("6");

        SecurityContextHolder.getContext().setAuthentication(authenticationToken); //권한 부여

        log.info("7");
        filterChain.doFilter(request, response); //다음 체인으로 넘어간다.
        log.info("8");
    }
}
