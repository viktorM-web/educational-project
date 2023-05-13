package org.viktor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.viktor.security.Identificational;
import org.viktor.security.UserSecurity;
import org.viktor.service.UserService;

import java.lang.reflect.Proxy;
import java.util.Set;

import static org.viktor.entity.Role.ADMIN;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login","/users", "/users/registration",
                                "/clients/registration", "/clients/**").permitAll()
                        .antMatchers("/cars/create").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/cars"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/cars")

                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())
                        )
                );
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");
            UserSecurity user = userService.loadUserByUsername(email);
            var defaultOidcUser = new DefaultOidcUser(user.getAuthorities(), userRequest.getIdToken());

            var userDetailsMethods = Set.of(UserSecurity.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class, Identificational.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(user, args)
                            : method.invoke(defaultOidcUser, args));
        };
    }
}
