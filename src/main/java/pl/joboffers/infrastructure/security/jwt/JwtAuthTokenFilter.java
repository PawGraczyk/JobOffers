package pl.joboffers.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private final JwtConfigurationProperties properties;

    private final HandlerExceptionResolver exceptionResolver;

    public JwtAuthTokenFilter(JwtConfigurationProperties properties, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.properties = properties;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            var usernamePasswordAuthenticationToken = getUsernamePasswordAuthenticationToken(authorization);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }

    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String authorizationToken) {
        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(authorizationToken.substring(7));
        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), null, Collections.emptyList());
    }
}
