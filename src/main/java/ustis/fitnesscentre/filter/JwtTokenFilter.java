package ustis.fitnesscentre.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ustis.fitnesscentre.service.ClientService;
import ustis.fitnesscentre.util.JwtTokenUtil;

import java.io.IOException;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    private final ClientService clientService;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, ClientService clientService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.clientService = clientService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = null;

        try {
            userDetails = clientService
                    .loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
        } catch (UsernameNotFoundException ignored) {
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails == null ? List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}
