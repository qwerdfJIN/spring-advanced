package org.example.expert.config.interceptor;

import jakarta.servlet.http.HttpServletRequest; // HTTP 요청을 다루는 기능
import jakarta.servlet.http.HttpServletResponse; // HTTP 응답을 다루는 기능
import lombok.extern.slf4j.Slf4j; // 로그를 기록하는 기능
import org.example.expert.config.JwtUtil; // JWT 관련 기능을 처리하는 도구
import org.example.expert.domain.common.exception.ServerException; // 서버에서 발생하는 오류를 다루는 기능
import org.springframework.stereotype.Component; // 이 파일을 Spring에서 자동으로 관리하게 하는 기능
import org.springframework.web.servlet.HandlerInterceptor; // 요청을 가로채서 검사하는 역할
import org.springframework.beans.factory.annotation.Autowired; // 객체를 자동으로 연결하는 기능
import io.jsonwebtoken.Claims; // JWT에서 데이터를 가져오는 기능

import java.time.LocalDateTime; // 현재 시간을 가져오는 기능

@Slf4j // 로그를 남기는 기능을 추가하는 코드
@Component // 이 클래스를 Spring이 자동으로 관리하도록 설정
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired // JwtUtil을 자동으로 연결해줌
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try { // 요청에서 JWT 토큰 가져오기
            String token = request.getHeader("Authoriczation"); // "Authorization" 헤더에서 토큰을 가져옴

            if (token == null || !token.startsWith("Bearer ")) { // 토큰이 없거나 "Bearer "로 시작하지 않으면
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패: 토큰이 없습니다."); // 401 오류 반환
                return false; // 요청을 중단
            }

            // "Bearer " 부분을 제거하여 실제 토큰 값만 추출
            token = jwtUtil.substringToken(token);

            // 토큰에서 클레임(Claims, 정보)을 가져옴
            Claims claims = jwtUtil.extractClaims(token);

            // 사용자의 역할을 가져옴
            String userRole = claims.get("userRole", String.class);

            if (userRole == null || !userRole.equals("ADMIN")) { // 사용자가 관리자가 아니면
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "인증 실패: 관리자만 접근할 수 있습니다."); // 403 오류 반환
                return false; // 요청을 중단
            }

            // 인증 성공 + 요청 정보 로깅
            log.info("Admin Access || Time: {} || URL: {}", LocalDateTime.now(), request.getRequestURI()); // 현재 시간과 요청한 URL을 기록
            return true; // 요청을 계속 진행

        } catch (ServerException e) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패: 잘못된 토큰입니다."); // 401 오류 반환
            return false; // 요청 중단
        }
    }
}
