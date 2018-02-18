package my.test.iotanalyzer.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class RESTAuthenticationTokenProcessingFilter extends GenericFilterBean{
    private static final String HEADER_HASH = "X-Authority-Hash";

    @Value("${iot.token}")
    private String token;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String servletPath = ((HttpServletRequest) request).getServletPath();
        if (servletPath != null && servletPath.startsWith("/store")) {
            if (token == null || token.isEmpty()) {
                throw new SecurityException("Not authorized");
            }

            String tokenHeader = ((HttpServletRequest) request).getHeader(HEADER_HASH);
            if (tokenHeader == null || tokenHeader.isEmpty()) {
                throw new SecurityException("Not authorized");
            }

            if (!tokenHeader.equals(token)) {
                throw new SecurityException("Not authorized");
            }
        }

        chain.doFilter(request, response);
    }
}
