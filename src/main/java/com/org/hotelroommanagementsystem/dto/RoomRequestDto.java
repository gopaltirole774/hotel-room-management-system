package com.org.hotelroommanagementsystem.dto;

import com.org.hotelroommanagementsystem.enums.RoomStatus;
import com.org.hotelroommanagementsystem.enums.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDto {

    @NotBlank(message = "Hotel name is required")
    private String hotelName;

    @NotBlank(message = "Room number is required")
    private String roomNumber;


    @NotNull(message = "Room status is required")
    private RoomStatus roomStatus;


    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "City is required")
    private String city;
}
