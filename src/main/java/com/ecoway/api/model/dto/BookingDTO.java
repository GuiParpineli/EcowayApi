package com.ecoway.api.model.dto;

import com.ecoway.api.model.Booking;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingDTO {
    private LocalDate checkInDay;
    private LocalTime checkInTime;
    private LocalDate checkOutDay;
    private LocalTime checkOutTime;
    private Boolean status;
    private SystemUserDTO user;
    private String city;
    private String category;

    public BookingDTO(Booking booking) {
        ObjectMapper mapper = new ObjectMapper();
        this.checkInDay = booking.getCheckInDay();
        this.checkInTime = booking.getCheckInTime();
        this.checkOutDay = booking.getCheckOutDay();
        this.checkOutTime = booking.getCheckOutTime();
        this.status = booking.getStatus();
        this.user = mapper.convertValue(booking.getUser(), SystemUserDTO.class);
        this.city = booking.getCity().getCityName();
        this.category = booking.getCategory().getName();
    }

}
