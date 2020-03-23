package drkeller.mail.mailapi.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity(debug = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.cors().and() // ADDED-DKR : disable this line to reproduce the CORS 401
	        .authorizeRequests()
	            .mvcMatchers("/api/**").hasAuthority("SCOPE_user")
	            .mvcMatchers("/swagger-ui.html").anonymous()
	            .mvcMatchers("/swagger-ui/**").anonymous()
	            .mvcMatchers("/v3/api-docs/**").anonymous()
	            .anyRequest().denyAll()
       .and()
        	.oauth2ResourceServer()
        	.jwt();
    }

}