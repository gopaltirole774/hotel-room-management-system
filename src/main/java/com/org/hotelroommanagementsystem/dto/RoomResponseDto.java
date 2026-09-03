package com.org.hotelroommanagementsystem.dto;

import com.org.hotelroommanagementsystem.enums.RoomStatus;
import com.org.hotelroommanagementsystem.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDto {

    private Integer id;

    private String hotelName;
    private String roomNumber;

    private RoomStatus roomStatus;

    private RoomType roomType;

    private Double price;
    private String city;
}
