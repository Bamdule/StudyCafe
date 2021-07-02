package com.bamdule.studycafe.config;

import com.bamdule.studycafe.room.RoomVO;
import com.bamdule.studycafe.studycafe.service.StudyCafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudyCafeConfig {
    private Integer studyCafeId = 1;
    private Map<Integer, RoomVO> roomMap = new IdentityHashMap<>();
    private List<RoomVO> rooms = new ArrayList<>();


    @Autowired
    private StudyCafeService studyCafeService;

    @PostConstruct
    private void initRooms() {
        this.rooms = studyCafeService.findAllRoom(studyCafeId);

        this.rooms.forEach(room -> {
            roomMap.put(room.getId(), room);
        });
    }

    public Integer getStudyCafeId() {
        return studyCafeId;
    }

    public Map<Integer, RoomVO> getRoomMap() {
        return roomMap;
    }

    public List<RoomVO> getRooms() {
        return rooms;
    }
}
