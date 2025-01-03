package com.ssginc.ewms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
//프로젝트 시작할 때 어노테이션들을 다 scan해서
//설정파일이라고 인식.
@ImportResource("classpath:applicationContext.xml")
//xml로 설정한 것 여기 자바에서 등록해주어야함.
//여러개인 경우 {설정xml, 설정xml2} 배열을 사용함.
public class AppConfig {
    //설정하나당 메서드하나씩 설정~!!!!!
}
