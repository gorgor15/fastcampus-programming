package com.fastcampus.programming.dmaker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DMakerErrorCode {
    //에러처리문 생성
    NO_DEVELOPER("해당되는 개발자가 없습니다."),
    DUPLICATED_MEMBER_ID("MemberId가 중복되는 개발자가 있습니다."),
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다."),
    ERROR("에러가발생 하였습니다."),
    LEVEL_EXPERIENCE_YEAR_NOT_MATCHED("개발자 레벨과 연차가 맞지않습니다.");


    private final String message;
}
