package edu.icet.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    private CookieUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Cookie createAccessTokenCookie(String token, int maxAgeSeconds) {
        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);  // Prevents JavaScript access (XSS protection)
        cookie.setSecure(false);   // Set to true in production with HTTPS
        cookie.setPath("/");       // Available across entire application
        cookie.setMaxAge(maxAgeSeconds);
        return cookie;
    }

    public static Cookie createRefreshTokenCookie(String token, int maxAgeSeconds) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);  // Prevents JavaScript access (XSS protection)
        cookie.setSecure(false);   // Set to true in production with HTTPS
        cookie.setPath("/auth");   // Only accessible on auth endpoints (more restrictive)
        cookie.setMaxAge(maxAgeSeconds);
        return cookie;
    }

    public static Cookie createDeleteCookie(String cookieName, String path) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);   // Must match the original cookie's Secure flag
        cookie.setPath(path);
        cookie.setMaxAge(0);       // Delete immediately
        return cookie;
    }

    public static void addAccessTokenCookie(HttpServletResponse response, String token, int maxAgeSeconds) {
        response.addCookie(createAccessTokenCookie(token, maxAgeSeconds));
    }

    public static void addRefreshTokenCookie(HttpServletResponse response, String token, int maxAgeSeconds) {
        response.addCookie(createRefreshTokenCookie(token, maxAgeSeconds));
    }

    public static void deleteAuthCookies(HttpServletResponse response) {
        response.addCookie(createDeleteCookie("accessToken", "/"));
        response.addCookie(createDeleteCookie("refreshToken", "/auth"));
    }
}
