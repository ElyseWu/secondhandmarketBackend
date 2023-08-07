package team3.secondhand.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team3.secondhand.filter.JwtFilter;

import javax.sql.DataSource;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    private final JwtFilter jwtFilter;

    public SecurityConfig(DataSource dataSource, JwtFilter jwtFilter) {
        this.dataSource = dataSource;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.POST,"/authenticate").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers(HttpMethod.GET,"/item/*").permitAll()
                .antMatchers(HttpMethod.GET,"/items").permitAll()
                .antMatchers(HttpMethod.GET,"/items/my").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET,"/user/{username}").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, "/username").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET,"/items/{category}").permitAll()
                .antMatchers(HttpMethod.POST,"/item").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PUT,"/item/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.DELETE,"/item/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.POST, "/favorite/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.DELETE, "/favorite/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, "/my_favorites").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.POST, "/ask/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.POST, "reply/*").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, "/chats").hasAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, "/messages/*").hasAuthority("ROLE_USER")

                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM authority WHERE username = ?");
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
