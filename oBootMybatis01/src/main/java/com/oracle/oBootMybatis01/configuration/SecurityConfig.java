 package com.oracle.oBootMybatis01.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//IoC 빈(bean)을 등록
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration // 환경작업설정
@EnableWebSecurity // 필터체인을 관리하는 어노테이션
public class SecurityConfig {
	
	@Bean   // 컨트롤 로직 어디서나 암호화를 사용할 수 있다.
	public BCryptPasswordEncoder encodePwd() { // security 모듈안에 있음
		return new BCryptPasswordEncoder(); // 인스턴스 형성
	}
	
	@Bean // chain 걸기
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 어떠한 request가 있어도 모두 허용
		http.csrf().disable();
		http.authorizeRequests() // 인가
			.anyRequest()
			.permitAll();
		
		return http.build();
	}
}
