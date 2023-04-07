package com.pki.example.config;

import com.pki.example.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDao userDao;


    @Override
    protected void doFilterInternal(

            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException{
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String userEmail;
        final String jwtToken;



        //ako je header prazan ili ne sadrzi token bearer ne radi nista
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            //ako ovo zakomentarisem onda radi
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = authHeader.substring(
                7
        );
        userEmail = jwtUtils.extractUsername(jwtToken);

        if(userEmail!= null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDao.findUserByEmail(userEmail);
                if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }

        }
        filterChain.doFilter(request,response);
    }

}
