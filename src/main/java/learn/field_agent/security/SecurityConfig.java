//package learn.field_agent.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final JwtConverter converter;
//
//    public SecurityConfig(JwtConverter converter) {
//        this.converter = converter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//        http.cors();
//
//        http.authorizeRequests()
//                .antMatchers("/api/authenticate").permitAll()
//                .antMatchers("/api/create_account").permitAll()
//                .antMatchers(HttpMethod.GET,
//                        "/api/field_agent", "/api/field_agent/*").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.POST,
//                        "/api/field_agent").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.PUT,
//                        "/api/field_agent/*").hasAnyRole("USER", "ADMIN")
//                .antMatchers(HttpMethod.DELETE,
//                        "/api/field_agent/*").hasAnyRole("ADMIN")
//                .antMatchers("/**").denyAll()
//                .and()
//                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//
//    @Override
//    @Bean
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder getEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//
