package com.bamdule.studycafe.entity.seatusage;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatUsageVO {

    private Integer id;

    private Integer seatId;

    private Integer number;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SeatStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer memberId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memberName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime startDt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime endDt;

}
