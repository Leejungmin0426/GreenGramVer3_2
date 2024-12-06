package com.green.greengram.common;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IpLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(IpLoggingFilter.class);

    // IP와 마지막 로그 시간을 저장하는 ConcurrentHashMap
    private final ConcurrentHashMap<String, Long> ipLogTimestamps = new ConcurrentHashMap<>();

    // 로그 간격 (10초)
    private static final long LOG_INTERVAL = 10_000; // 10초를 밀리초로

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 작업 (필요시)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 클라이언트 IP 추출
        String clientIp = getClientIp(httpRequest);

        // 현재 시간
        long currentTime = System.currentTimeMillis();

        // IP의 마지막 로그 시간 가져오기
        Long lastLogTime = ipLogTimestamps.get(clientIp);

        // IP가 처음 요청하거나 10초 이상 경과한 경우에만 로그 기록
        if (lastLogTime == null || currentTime - lastLogTime > LOG_INTERVAL) {
            if (isValidIPv4(clientIp)) {
                // IPv4 주소만 로그 기록
                logger.info("Request from IPv4 address: {}", clientIp);

                // 현재 시간을 해당 IP에 대한 마지막 로그 시간으로 설정
                ipLogTimestamps.put(clientIp, currentTime);
            }
        }

        // 필터 체인 계속 진행
        chain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // IPv4 형식인지 확인하는 메서드
    private boolean isValidIPv4(String ip) {
        try {
            // InetAddress.getByName()은 IP를 검사하고, IPv4 형식이 맞으면 그대로 반환
            InetAddress inetAddress = InetAddress.getByName(ip);
            return inetAddress instanceof java.net.Inet4Address;
        } catch (UnknownHostException e) {
            // IP 주소가 잘못된 경우 예외 처리
            return false;
        }
    }

    @Override
    public void destroy() {
        // 필터 종료 시 작업 (필요시)
    }
}
