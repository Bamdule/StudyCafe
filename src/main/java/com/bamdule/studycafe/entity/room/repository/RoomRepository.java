package com.bamdule.studycafe.entity.room.repository;

import com.bamdule.studycafe.entity.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
