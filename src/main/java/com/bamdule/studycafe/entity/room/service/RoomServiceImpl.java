package com.bamdule.studycafe.entity.room.service;

import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<RoomVO> getAllListRoom() {
        return roomRepository.findAll().stream().map(room -> {
            return RoomVO.builder()
                    .id(room.getId())
                    .name(room.getName())
                    .width(room.getWidth())
                    .height(room.getHeight())
                    .build();
        }).collect(Collectors.toList());
    }
}
