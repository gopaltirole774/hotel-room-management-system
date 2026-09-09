package com.org.hotelroommanagementsystem.service;

import com.org.hotelroommanagementsystem.dto.RoomPatchRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomResponseDto;

import java.util.List;

public interface    RoomService {

    RoomResponseDto addRoom(RoomRequestDto roomRequestDto);

    List<RoomResponseDto> getAllRooms();

    RoomResponseDto getRoomById(Integer id);

    RoomResponseDto updateRoomById(Integer id, RoomRequestDto roomRequestDto);

    void deleteRoomById(Integer id);

    RoomResponseDto patchRoomById(Integer id, RoomPatchRequestDto roomPatchRequestDto);

}
