package com.bamdule.studycafe.studycafe;

import com.bamdule.studycafe.room.RoomVO;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class StudyCafeVO {

    private Integer id;

    private String name;

    private List<RoomVO> rooms = new ArrayList<>();


}
