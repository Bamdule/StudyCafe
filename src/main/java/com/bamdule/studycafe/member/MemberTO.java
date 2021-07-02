package com.bamdule.studycafe.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberTO {
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    private LocalDateTime lastUsedDt;
    private LocalDateTime joinDt;
}
