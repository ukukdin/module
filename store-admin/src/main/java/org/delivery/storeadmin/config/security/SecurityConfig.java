package org.delivery.storeadmin.config.security;

import java.util.List;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //security 활성화
public class SecurityConfig {

  private List<String> SWAGGER = List.of(
      "/swagger-ui.html",
      "/swagger-ui/**",
      "/v3/api-docs/**"
  );
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
      httpSecurity
          .csrf().disable()
          .authorizeHttpRequests(it ->{
              it
                  .requestMatchers(
                      PathRequest.toStaticResources().atCommonLocations()
                  ).permitAll() //resource에 대해서는 모든 요청 허용
                  //swagger 는 인증 없이 통과
                  .mvcMatchers(
                      SWAGGER.toArray(new String[0])
                  ).permitAll()

                  // open-api/** 하위 모든 주소는 인증 없이 통과
                  .mvcMatchers(
                      "/open-api/**"
                  ).permitAll()
                    //그 외의 모든 요청은 인증 사용
                    .anyRequest().authenticated();
          })
          .formLogin(Customizer.withDefaults());

      return httpSecurity.build();
  }
  @Bean
  public PasswordEncoder passwordEncoder(){
      //hash 방식으로 암호화 -> sort 를 넣는다. 비번이 들어오면 해쉬를 시켜서 값이 동일한지 비교한다. 디코딩 불가 오로지 인코딩만 가능.
      return new BCryptPasswordEncoder();
  }
}
