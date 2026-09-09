package com.org.hotelroommanagementsystem.controller;

import com.org.hotelroommanagementsystem.dto.RoomPatchRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomRequestDto;
import com.org.hotelroommanagementsystem.dto.RoomResponseDto;
import com.org.hotelroommanagementsystem.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Tag(name = "Room Management")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @Operation(summary = "Add a new room")
    @ApiResponse(responseCode = "201", description = "Room created successfully")
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<RoomResponseDto> addRoom(@Valid @RequestBody RoomRequestDto roomRequestDto) {
        return new ResponseEntity<>(roomService.addRoom(roomRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all rooms")
    @ApiResponse(responseCode = "200", description = "Rooms retrieved successfully")
            @ApiResponse(responseCode = "404", description = "Rooms not found")
    public ResponseEntity<List<RoomResponseDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by id")
    @ApiResponse(responseCode = "200", description = "Room retrieved successfully")
            @ApiResponse(responseCode = "404", description = "Room not found")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update room by id")
    @ApiResponse(responseCode = "200", description = "Room updated successfully")
            @ApiResponse(responseCode = "404", description = "Room not found")
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<RoomResponseDto> updateRoomById(@PathVariable Integer id, @Valid @RequestBody RoomRequestDto roomRequestDto) {
        return ResponseEntity.ok(roomService.updateRoomById(id, roomRequestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete room by id")
    @ApiResponse(responseCode = "200", description = "Room deleted successfully")
            @ApiResponse(responseCode = "404", description = "Room not found")
    public ResponseEntity<String> deleteRoomById(@PathVariable Integer id) {
        roomService.deleteRoomById(id);
        return ResponseEntity.ok("Room deleted successfully");
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update room by id")
    @ApiResponse(responseCode = "200", description = "Room updated successfully")
    @ApiResponse(responseCode = "404", description = "Room not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<RoomResponseDto> patchRoomById(@PathVariable Integer id,@Valid @RequestBody RoomPatchRequestDto roomPatchRequestDto) {
        return ResponseEntity.ok(roomService.patchRoomById(id, roomPatchRequestDto));
    }
}
