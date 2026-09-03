package com.org.hotelroommanagementsystem.service;

import com.org.hotelroommanagementsystem.dto.RoomPatchRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomResponseDto;
import com.org.hotelroommanagementsystem.entity.Room;
import com.org.hotelroommanagementsystem.exception.RoomNotFoundException;
import com.org.hotelroommanagementsystem.repository.RoomRepository;
import com.org.hotelroommanagementsystem.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.org.hotelroommanagementsystem.enums.RoomStatus.AVAILABLE;
import static com.org.hotelroommanagementsystem.enums.RoomType.DELUXE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomServiceImpl;

    private Room room;
    private RoomRequestDto roomRequestDto;
    private RoomPatchRequestDto roomPatchRequestDto;

    @BeforeEach
    void setUp() {

        roomRequestDto = new RoomRequestDto();

        roomRequestDto.setRoomStatus(AVAILABLE);
        roomRequestDto.setCity("Khandwa");
        roomRequestDto.setRoomNumber("01");
        roomRequestDto.setHotelName("Maa Sharda");
        roomRequestDto.setRoomType(DELUXE);
        roomRequestDto.setPrice(2300.00);

        room = new Room();

        room.setId(1);
        room.setPrice(roomRequestDto.getPrice());
        room.setCity(roomRequestDto.getCity());
        room.setRoomStatus(roomRequestDto.getRoomStatus());
        room.setRoomType(roomRequestDto.getRoomType());
        room.setHotelName(roomRequestDto.getHotelName());
        room.setRoomNumber(roomRequestDto.getRoomNumber());

        roomPatchRequestDto = new RoomPatchRequestDto();

        roomPatchRequestDto.setPrice(roomRequestDto.getPrice());
        roomPatchRequestDto.setRoomStatus(roomRequestDto.getRoomStatus());
        roomPatchRequestDto.setRoomType(roomRequestDto.getRoomType());


    }

    @Test
    void addRoomTest() {
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomResponseDto roomResponseDto = roomServiceImpl.addRoom(roomRequestDto);
        assertNotNull(roomResponseDto);
        assertEquals(roomResponseDto.getRoomNumber(), roomRequestDto.getRoomNumber());

        verify(roomRepository).save(any(Room.class));


    }

    @Test
    void updateRoomByIdTest() {

        when(roomRepository.findById(1)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomResponseDto roomResponseDto = roomServiceImpl.updateRoomById(1, roomRequestDto);

        assertNotNull(roomResponseDto);
        assertEquals(roomResponseDto.getRoomNumber(), roomRequestDto.getRoomNumber());

        verify(roomRepository).save(any(Room.class));
        verify(roomRepository).findById(1);


    }

    @Test
    void updateRoomById_NotFoundTest() {
        when(roomRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomServiceImpl.updateRoomById(1, roomRequestDto));

        verify(roomRepository).findById(1);


    }

    @Test
    void deleteRoomByIdTest() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        roomServiceImpl.deleteRoomById(1);

        verify(roomRepository).findById(1);
        verify(roomRepository).deleteById(1);


    }

    @Test
    void deleteRoomById_NotFoundTest() {
        when(roomRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomServiceImpl.deleteRoomById(1));

        verify(roomRepository).findById(1);
    }

    @Test
    void getRoomByIdTest() {

        when(roomRepository.findById(1)).thenReturn(Optional.of(room));

        RoomResponseDto roomResponseDto = roomServiceImpl.getRoomById(1);

        assertNotNull(roomResponseDto);
        assertEquals(roomResponseDto.getRoomNumber(), roomRequestDto.getRoomNumber());

        verify(roomRepository).findById(1);


    }

    @Test
    void getRoomById_NotFoundTest() {

        when(roomRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RoomNotFoundException.class, () -> roomServiceImpl.getRoomById(1));
        verify(roomRepository).findById(1);
    }

    @Test
    void getAllRoomsTest() {
        when(roomRepository.findAll()).thenReturn(List.of(room));

        List<RoomResponseDto> roomResponseDtoList = roomServiceImpl.getAllRooms();

        assertNotNull(roomResponseDtoList);
        assertEquals(1,roomResponseDtoList.size());

        verify(roomRepository).findAll();


    }

    @Test
    void getAllRooms_EmptyListTest() {
        when(roomRepository.findAll()).thenReturn(List.of());

        List<RoomResponseDto> roomResponseDtoList = roomServiceImpl.getAllRooms();

        assertNotNull(roomResponseDtoList);
        assertEquals(0,roomResponseDtoList.size());

        verify(roomRepository).findAll();


    }

    @Test
    void patchRoomByIdTest() {
        when(roomRepository.findById(1)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomResponseDto roomResponseDto = roomServiceImpl.patchRoomById(1, roomPatchRequestDto);
        assertNotNull(roomResponseDto);
        assertEquals(roomResponseDto.getRoomNumber(), roomRequestDto.getRoomNumber());
        assertEquals(roomResponseDto.getRoomStatus(), roomPatchRequestDto.getRoomStatus());
        assertEquals(roomResponseDto.getPrice(), roomPatchRequestDto.getPrice());
        assertEquals(roomResponseDto.getRoomType(), roomPatchRequestDto.getRoomType());

        verify(roomRepository).save(any(Room.class));
        verify(roomRepository).findById(1);

    }

    @Test
    void patchRoomById_NotFoundTest() {
        when(roomRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RoomNotFoundException.class, () -> roomServiceImpl.patchRoomById(1, roomPatchRequestDto));
        verify(roomRepository).findById(1);

    }
}
