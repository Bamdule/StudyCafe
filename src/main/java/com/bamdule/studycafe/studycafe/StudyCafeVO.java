package com.bamdule.studycafe.studycafe;

import com.bamdule.studycafe.room.RoomVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class StudyCafeVO {

    public StudyCafeVO() {
    }

    public StudyCafeVO(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    private Integer id;

    private String name;

    private String address;

}
