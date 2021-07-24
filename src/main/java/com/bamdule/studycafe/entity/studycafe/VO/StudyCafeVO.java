package com.bamdule.studycafe.entity.studycafe.VO;

import com.bamdule.studycafe.entity.room.RoomVO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StudyCafeVO {

    private List<RoomVO> rooms;


}
