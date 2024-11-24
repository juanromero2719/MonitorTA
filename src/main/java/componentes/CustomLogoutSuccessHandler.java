/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package componentes;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author juanr
 */

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

    private final VideoCache videoCache;

    public CustomLogoutSuccessHandler(VideoCache videoCache) {
        this.videoCache = videoCache;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        
        videoCache.getAll().clear();       
        response.sendRedirect("/login?logout");
        
    }
    
}
