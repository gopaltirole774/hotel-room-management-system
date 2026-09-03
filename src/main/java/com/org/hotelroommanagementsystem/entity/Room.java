package com.org.hotelroommanagementsystem.entity;

import com.org.hotelroommanagementsystem.enums.RoomStatus;
import com.org.hotelroommanagementsystem.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String hotelName;
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private Double price;
    private String city;

}
