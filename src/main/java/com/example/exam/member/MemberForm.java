package com.example.exam.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "아이디를 입력하세요")
    private String username;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password1;
    @NotEmpty(message = "비밀번호 확인을 입력하세요")
    private String password2;
    @NotEmpty(message = "닉네임을 입력하세요")
    private String nickname;
}
