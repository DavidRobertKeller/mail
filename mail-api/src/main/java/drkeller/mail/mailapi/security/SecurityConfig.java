package drkeller.mail.mailapi.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		
//    	http.cors().and()
//        .authorizeRequests()
//            .mvcMatchers("/api/**").hasAuthority("SCOPE_user")
//            .mvcMatchers("/swagger-ui.html").anonymous()
//            .mvcMatchers("/swagger-ui/**").anonymous()
//            .mvcMatchers("/v3/api-docs/**").anonymous()
//            .anyRequest().denyAll()
//   .and()
//    	.oauth2ResourceServer()
//    	.jwt();

		
		ServerHttpSecurity.AuthorizeExchangeSpec security = http.csrf().disable()
			.httpBasic().disable()
			.formLogin().disable()
			.logout().disable()
			.cors().and()
//			.addFilterAfter(apiKeyClaimFilter(), SecurityWebFiltersOrder.AUTHORIZATION)
//			.addFilterAfter(checkSaaSAccess(), SecurityWebFiltersOrder.AUTHORIZATION)
			.authorizeExchange()
			.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.pathMatchers("/swagger-ui.html").permitAll()
			.pathMatchers("/",
					"/api/**",
					"/webjars/**",
					"/v3/api-docs/**",
					"/swagger-ui/**",
					"/swagger-ui.html").permitAll();
		
//		security = pathMatchersConfig.addAppRules(security);
		security.and()
			.oauth2ResourceServer()
			.jwt();
//			.jwtAuthenticationConverter(grantedAuthoritiesExtractor());
		
		return http.build();
	}
	
	private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
		GrantedAuthoritiesExtractor extractor = new GrantedAuthoritiesExtractor();
		return new ReactiveJwtAuthenticationConverterAdapter(extractor);
	}

	static class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter
	{
		@Override
		protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
			Map<String, Object> claims = jwt.getClaims();
			JSONObject realmAccess = (JSONObject) claims.get("realm_access");
			if (Objects.isNull(realmAccess)) return Collections.emptySet();

			JSONArray roles = (JSONArray) realmAccess.get("roles");
			if(Objects.isNull(roles)) return Collections.emptySet();

			return roles.stream()
					.map(Object::toString)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet());
		}
	}

}
