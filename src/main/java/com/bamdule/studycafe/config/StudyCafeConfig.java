package com.bamdule.studycafe.config;

import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.studycafe.service.StudyCafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudyCafeConfig {
    private Integer studyCafeId;
    private Map<Integer, RoomVO> roomMap = new IdentityHashMap<>();
    private List<RoomVO> rooms = new ArrayList<>();


    @Autowired
    private StudyCafeService studyCafeService;

    public void init(Integer studyCafeId) {
        this.studyCafeId = studyCafeId;
        this.rooms = studyCafeService.findAllRoom(studyCafeId);

        this.rooms.forEach(room -> {
            roomMap.put(room.getId(), room);
        });
    }

    public void destroy() {
        this.studyCafeId = null;
        this.roomMap = new IdentityHashMap<>();
        this.rooms = new ArrayList<>();
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
