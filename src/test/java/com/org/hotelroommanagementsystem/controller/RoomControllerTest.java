package com.org.hotelroommanagementsystem.controller;

import com.org.hotelroommanagementsystem.dto.RoomPatchRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomResponseDto;
import com.org.hotelroommanagementsystem.enums.RoomStatus;
import com.org.hotelroommanagementsystem.enums.RoomType;
import com.org.hotelroommanagementsystem.exception.RoomNotFoundException;
import com.org.hotelroommanagementsystem.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
 class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    private RoomResponseDto roomResponseDto;
    private RoomRequestDto roomRequestDto;
    private RoomPatchRequestDto roomPatchRequestDto;

    @BeforeEach
     void setUp() {
        roomRequestDto = new RoomRequestDto();
        roomRequestDto.setCity("Delhi");
        roomRequestDto.setHotelName("Hotel 1");
        roomRequestDto.setRoomNumber("101");
        roomRequestDto.setRoomStatus(RoomStatus.AVAILABLE);
        roomRequestDto.setRoomType(RoomType.SINGLE);
        roomRequestDto.setPrice(100.0);


        roomPatchRequestDto = new RoomPatchRequestDto();
        roomPatchRequestDto.setRoomStatus(roomRequestDto.getRoomStatus());
        roomPatchRequestDto.setRoomType(roomRequestDto.getRoomType());
        roomPatchRequestDto.setPrice(roomRequestDto.getPrice());

        roomResponseDto = new RoomResponseDto();

        roomResponseDto.setRoomStatus(roomRequestDto.getRoomStatus());
        roomResponseDto.setRoomType(roomRequestDto.getRoomType());
        roomResponseDto.setPrice(roomRequestDto.getPrice());
        roomResponseDto.setCity(roomRequestDto.getCity());
        roomResponseDto.setHotelName(roomRequestDto.getHotelName());
        roomResponseDto.setRoomNumber(roomRequestDto.getRoomNumber());
        roomResponseDto.setId(1);
    }

    @Test
    void addRoomTest() throws Exception {

        when(roomService.addRoom(any(RoomRequestDto.class))).thenReturn(roomResponseDto);
        mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(roomResponseDto.getId()))
                .andExpect(jsonPath("$.hotelName").value(roomResponseDto.getHotelName()))
                .andExpect(jsonPath("$.roomNumber").value(roomResponseDto.getRoomNumber()))
                .andExpect(jsonPath("$.roomStatus").value(roomResponseDto.getRoomStatus().toString()))
                .andExpect(jsonPath("$.roomType").value(roomResponseDto.getRoomType().toString()))
                .andExpect(jsonPath("$.price").value(roomResponseDto.getPrice()))
                .andExpect(jsonPath("$.city").value(roomResponseDto.getCity()));
        verify(roomService).addRoom(any(RoomRequestDto.class));
    }

    @Test
    void getAllRoomsTest() throws Exception {
        when(roomService.getAllRooms()).thenReturn(List.of(roomResponseDto));

        mockMvc.perform(get("/rooms/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                        .andExpect(jsonPath("$[0].id").value(roomResponseDto.getId()));
        verify(roomService).getAllRooms();
    }

    @Test
    void getAllRooms_EmptyTest() throws Exception {
        when(roomService.getAllRooms()).thenReturn(List.of());
        mockMvc.perform(get("/rooms/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
        verify(roomService).getAllRooms();
    }

    @Test
    void getRoomByIdTest() throws Exception {

        when(roomService.getRoomById(1)).thenReturn(roomResponseDto);

        mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomResponseDto.getId()))
                .andExpect(jsonPath("$.hotelName").value(roomResponseDto.getHotelName()))
                .andExpect(jsonPath("$.roomNumber").value(roomResponseDto.getRoomNumber()))
                .andExpect(jsonPath("$.roomStatus").value(roomResponseDto.getRoomStatus().toString()))
                .andExpect(jsonPath("$.roomType").value(roomResponseDto.getRoomType().toString()))
                .andExpect(jsonPath("$.price").value(roomResponseDto.getPrice()))
                .andExpect(jsonPath("$.city").value(roomResponseDto.getCity()));

        verify(roomService).getRoomById(1);


    }

    @Test
    void getRoomByIdNotFoundTest() throws Exception {

        when(roomService.getRoomById(1)).thenThrow(new RoomNotFoundException("Room not found"));

        mockMvc.perform(get("/rooms/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRoomByIdTest() throws Exception {
        when(roomService.updateRoomById(eq(1),any(RoomRequestDto.class))).thenReturn(roomResponseDto);

           mockMvc.perform(put("/rooms/1")

        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(roomRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(roomResponseDto.getId()))
        .andExpect(jsonPath("$.hotelName").value(roomResponseDto.getHotelName()));

        verify(roomService).updateRoomById(eq(1),any(RoomRequestDto.class));

    }

    @Test
    void updateRoomNotFoundTest() throws Exception {
        when(roomService.updateRoomById(eq(1),any(RoomRequestDto.class)))
                .thenThrow(new RoomNotFoundException("Room not found"));

        mockMvc.perform(put("/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRoomByIdTest() throws Exception {
        doNothing().when(roomService).deleteRoomById(1);

        mockMvc.perform(delete("/rooms/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteRoomByIdNotFoundTest() throws Exception {
        doThrow(new RoomNotFoundException("Room not found")).when(roomService).deleteRoomById(1);
        mockMvc.perform(delete("/rooms/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchRoomByIdTest() throws Exception {
        when(roomService.patchRoomById(eq(1),any(RoomPatchRequestDto.class))).thenReturn(roomResponseDto);

        mockMvc.perform(patch("/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomPatchRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomResponseDto.getId()))
                .andExpect(jsonPath("$.hotelName").value(roomResponseDto.getHotelName()))
                .andExpect(jsonPath("$.roomNumber").value(roomResponseDto.getRoomNumber()))
                .andExpect(jsonPath("$.roomStatus").value(roomResponseDto.getRoomStatus().toString()))
                .andExpect(jsonPath("$.roomType").value(roomResponseDto.getRoomType().toString()))
                .andExpect(jsonPath("$.price").value(roomResponseDto.getPrice()))
                .andExpect(jsonPath("$.city").value(roomResponseDto.getCity()));

        verify(roomService).patchRoomById(eq(1),any(RoomPatchRequestDto.class));

    }

    @Test
    void patchRoomByIdNotFoundTest() throws Exception {
        when(roomService.patchRoomById(eq(1),any(RoomPatchRequestDto.class)))
                .thenThrow(new RoomNotFoundException("Room not found"));

        mockMvc.perform(patch("/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomPatchRequestDto)))
                .andExpect(status().isNotFound());
    }
}
