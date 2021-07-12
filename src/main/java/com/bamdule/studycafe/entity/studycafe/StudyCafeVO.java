package com.bamdule.studycafe.entity.studycafe;

import lombok.Builder;
import lombok.Data;

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
