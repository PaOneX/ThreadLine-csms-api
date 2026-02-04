package edu.icet.util.jwt;

import edu.icet.util.CookieUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String ERR_USER_DISABLED = "USER_DISABLED";
    private static final String MSG_USER_DISABLED = "User account is disabled";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        String token = extractTokenFromRequest(req);

        // Only attempt authentication if a token exists and the request has not already been authenticated.
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                Claims claims = jwtService.parseClaims(token);
                String username = claims.getSubject();
                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Option 2 behavior: return a specific, catchable error for disabled accounts.
                    // This makes "disable user" kick in immediately even if their access token is still valid.
                    if (!userDetails.isEnabled()) {
                        SecurityContextHolder.clearContext();
                        CookieUtil.deleteAuthCookies(res);
                        writeDisabledResponse(res);
                        return;
                    }

                    if (jwtService.isTokenValid(token, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (Exception ex) {
                // Invalid token: leave request unauthenticated and continue the filter chain.
            }
        }
        chain.doFilter(req, res);
    }

    private void writeDisabledResponse(HttpServletResponse res) throws IOException {
        if (res.isCommitted()) {
            return;
        }
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        // Keep it intentionally small + stable for frontend checks.
        res.getWriter().write(
                "{\"error\":\"forbidden\",\"code\":\"" + ERR_USER_DISABLED + "\",\"message\":\"" + MSG_USER_DISABLED + "\"}"
        );
    }

    private String extractTokenFromRequest(HttpServletRequest req) {
        // First try to get token from cookie
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // Fallback to Authorization header
        final String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }
}
