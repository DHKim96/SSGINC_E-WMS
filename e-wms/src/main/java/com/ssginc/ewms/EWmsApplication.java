package com.ssginc.ewms;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@RequiredArgsConstructor
public class EWmsApplication implements CommandLineRunner {

    private final Environment environment;
    // Environment 객체를 통해 설정값을 읽음

    public static void main(String[] args) {
        SpringApplication.run(EWmsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //... : 가변 매개변수, 매개변수를 여러개 넣어도 됨.
        //매개변수가 여러개인 경우, 맨 끝에 넣어야함.
        // (int x, String... s2) ==> (100 "감사"), (100, "감사", "땡큐")도 가능
        // 내부에서는 String[] s2로 인식
        // s2[0] = "감사", s2[1] = "땡큐"로 인식
        // 가변 매개 변수의 값은 배열로 들어온다.
        // s2.length는 이때 2
        // application.properties에서 server.port 값 읽기
        String port = environment.getProperty("server.port");
        // 애플리케이션 시작 후 콘솔에 링크 출력
        System.out.println("========================================");
        System.out.println("Application is running at:");
        System.out.println("http://localhost:" + port);
        System.out.println("========================================");
    }
}
