package com.org.hotelroommanagementsystem.service.impl;

import com.org.hotelroommanagementsystem.dto.RoomPatchRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomResponseDto;
import com.org.hotelroommanagementsystem.entity.Room;
import com.org.hotelroommanagementsystem.exception.RoomNotFoundException;
import com.org.hotelroommanagementsystem.repository.RoomRepository;
import com.org.hotelroommanagementsystem.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private static final String ROOM_NOT_FOUND = "Room not found with id: ";
    private final RoomRepository roomRepository;

    public Room toEntity(RoomRequestDto roomRequestDto) {

        Room room = new Room();
        room.setRoomNumber(roomRequestDto.getRoomNumber());
        room.setRoomType(roomRequestDto.getRoomType());
        room.setPrice(roomRequestDto.getPrice());
        room.setRoomStatus(roomRequestDto.getRoomStatus());
        room.setCity(roomRequestDto.getCity());
        room.setHotelName(roomRequestDto.getHotelName());
        return room;

    }

    public RoomResponseDto toResponse(Room room) {

        RoomResponseDto roomResponseDto = new RoomResponseDto();
        roomResponseDto.setId(room.getId());
        roomResponseDto.setHotelName(room.getHotelName());
        roomResponseDto.setRoomNumber(room.getRoomNumber());
        roomResponseDto.setRoomType(room.getRoomType());
        roomResponseDto.setPrice(room.getPrice());
        roomResponseDto.setRoomStatus(room.getRoomStatus());
        roomResponseDto.setCity(room.getCity());
        return roomResponseDto;


    }

    @Override
    public RoomResponseDto addRoom(RoomRequestDto roomRequestDto) {
        Room savedRoom = roomRepository.save(toEntity(roomRequestDto));
        return toResponse(savedRoom);
    }

    @Override
    public RoomResponseDto getRoomById(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(ROOM_NOT_FOUND));
        return toResponse(room);
    }

    @Override
    public RoomResponseDto updateRoomById(Integer id, RoomRequestDto roomRequestDto) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(ROOM_NOT_FOUND));

        room.setRoomNumber(roomRequestDto.getRoomNumber());
        room.setRoomType(roomRequestDto.getRoomType());
        room.setPrice(roomRequestDto.getPrice());
        room.setRoomStatus(roomRequestDto.getRoomStatus());
        room.setCity(roomRequestDto.getCity());
        room.setHotelName(roomRequestDto.getHotelName());

        Room updatedRoom = roomRepository.save(room);
        return toResponse(updatedRoom);
    }

    @Override
    public void deleteRoomById(Integer id) {
        roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(ROOM_NOT_FOUND));
        roomRepository.deleteById(id);
    }

    @Override
    public RoomResponseDto patchRoomById(Integer id, RoomPatchRequestDto roomPatchRequestDto) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(ROOM_NOT_FOUND));

        if (roomPatchRequestDto.getRoomStatus() != null) {
            room.setRoomStatus(roomPatchRequestDto.getRoomStatus());
        }
        if (roomPatchRequestDto.getPrice() != null) {
            room.setPrice(roomPatchRequestDto.getPrice());
        }
        if (roomPatchRequestDto.getRoomType() != null) {
            room.setRoomType(roomPatchRequestDto.getRoomType());
        }
        return toResponse(roomRepository.save(room));
    }

    @Override

    public List<RoomResponseDto> getAllRooms() {
        List<Room> roomsList = roomRepository.findAll();

        List<RoomResponseDto> roomsResponseDtoList = new ArrayList<>();
        for (Room room : roomsList) {
            roomsResponseDtoList.add(toResponse(room));
        }

        return roomsResponseDtoList;
    }


}

