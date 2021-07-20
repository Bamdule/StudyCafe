package com.bamdule.studycafe.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberTO {
    private Integer id;

    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;

    @NotBlank
    @Pattern(regexp = "(010|011)[0-9]{3,4}[0-9]{4}", message = "잘못된 휴대폰 번호입니다. ('-' 생략)")
    private String phone;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String password;

    @NotBlank(message = "이메일은 필수 값입니다.")
    private String email;

    @NotBlank(message = "이메일 코드는 필수 값 입니다.")
    private String emailCode;

    private LocalDateTime lastUsedDt;
    private LocalDateTime joinDt;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailCode() {
        return emailCode;
    }
}
