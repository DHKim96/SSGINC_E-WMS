package com.ssginc.ewms.util.validator;

import com.ssginc.ewms.exception.InvalidFormatException;
import com.ssginc.ewms.member.dto.MemberInsertRequest;
import com.ssginc.ewms.util.ErrorCode;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * 유저 정보를 검증하는 클래스
 */
@Component
public class MemberValidator implements Validator<MemberInsertRequest> {

    private final String idReg;
    private final String passwordReg;
    private final String emailReg;
    private final String phoneReg;
    private final String birthReg;

    public MemberValidator() {
        idReg = "^[a-z0-9]{4,20}$";
        passwordReg = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,20}$";
        emailReg = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        phoneReg = "^01[0-9]-\\d{3,4}-\\d{4}$";
        birthReg = "^\\d{4}-\\d{2}-\\d{2}$";
    }

    @Override
    public boolean validate(MemberInsertRequest object) {
        if (object == null) {
            throw new InvalidFormatException(ErrorCode.NULL_POINT_ERROR);
        }

        validateId(object.getMemberId());
        validatePassword(object.getMemberPw());
        validateEmail(object.getMemberEmail());
        validatePhone(object.getMemberPhone());
        validateBirth(object.getMemberBirth());

        return true;
    }

    public void validateId(String id) {
        validateWithRegex(id, idReg, ErrorCode.INVALID_MEMBER_FORMAT);
    }

    public void validatePassword(String password) {
        validateWithRegex(password, passwordReg, ErrorCode.INVALID_MEMBER_FORMAT);
    }

    public void validateEmail(String email) {
        validateWithRegex(email, emailReg, ErrorCode.INVALID_MEMBER_FORMAT);
    }

    public void validatePhone(String phone) {
        validateWithRegex(phone, phoneReg, ErrorCode.INVALID_MEMBER_FORMAT);
    }

    private void validateBirth(String birth) {
        if (birth == null || !birth.matches(birthReg)) {
            throw new InvalidFormatException(ErrorCode.INVALID_MEMBER_FORMAT);
        }

        LocalDate birthDate = LocalDate.parse(birth);
        LocalDate today = LocalDate.now();

        if (birthDate.isAfter(today) || birthDate.isBefore(today.minusYears(150))) {
            throw new InvalidFormatException(ErrorCode.INVALID_MEMBER_FORMAT);
        }
    }

    private void validateWithRegex(String value, String regex, ErrorCode errorCode) {
        if (value == null || !value.matches(regex)) {
            throw new InvalidFormatException(errorCode);
        }
    }
}
