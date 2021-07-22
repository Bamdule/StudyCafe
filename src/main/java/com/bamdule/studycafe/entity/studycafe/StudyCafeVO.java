package com.bamdule.studycafe.entity.studycafe;

import com.bamdule.studycafe.entity.room.RoomVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class StudyCafeVO {

    private Integer id;

    private String name;

    private String address;

    private List<RoomVO> rooms = new ArrayList<>();

}
