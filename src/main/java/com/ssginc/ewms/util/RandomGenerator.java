package com.ssginc.ewms.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * 난수 혹은 랜덤한 문자열을 생성해주는 클래스
 */
@Component
public class RandomGenerator {

    // 난수 자릿수 (000000 형식)
    private final int numLen = 6;

    // 랜덤 문자열 생성 시 사용할 문자 조합
    private final String charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*";

    // 랜덤 문자열 길이
    private final int strLen = 8;

    // SecureRandom 객체 (재사용 가능)
    private final SecureRandom random = new SecureRandom();

    /**
     * 지정된 길이의 숫자, 영문 대소문자, 특수문자로 이루어진 랜덤 문자열을 생성합니다.
     * @return 랜덤 문자열
     */
    public String generateRandomStr() {
        StringBuilder sb = new StringBuilder(strLen);
        for (int i = 0; i < strLen; i++) {
            int index = random.nextInt(charSet.length());
            sb.append(charSet.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 정해진 자릿수의 난수를 생성합니다. (000000 형식)
     * @return 난수 문자열
     */
    public String generateRandomNum() {
        int upperBound = (int) Math.pow(10, numLen); // 상한값 (10^length)
        int randomNumber = random.nextInt(upperBound); // 상한값 미만의 난수 생성
        return String.format("%0" + numLen + "d", randomNumber); // 자릿수 패딩
    }
}
