package com.org.hotelroommanagementsystem.repository;

import com.org.hotelroommanagementsystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Integer> {

}
