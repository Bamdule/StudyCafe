package com.bamdule.studycafe.entity.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomVO {
    private Integer id;

    private String name;

    private Integer width;

    private Integer height;
}
