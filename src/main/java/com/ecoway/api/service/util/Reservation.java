package com.ecoway.api.service.util;

import com.ecoway.api.model.Booking;
import com.ecoway.api.model.Vehicle;
import com.ecoway.api.repository.VehicleRepository;
import lombok.extern.java.Log;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Reservation {
    private final static Logger log = Logger.getLogger(Reservation.class);
    private static VehicleRepository vehicleRepository;

    public Reservation(VehicleRepository vehicleRepository) {
        Reservation.vehicleRepository = vehicleRepository;
    }

    static Long duration(Booking booking) {
        return ChronoUnit.DAYS.between(booking.getCheckInDay(), booking.getCheckOutDay());
    }

    public static boolean isBookingPossible(Booking booking, List<Booking> bookingSaved) {
        log.info("bookings saved :" + bookingSaved.size());
        log.info("bookings duration:" + duration(booking).intValue());
        log.info(booking.getCategory().getId());

        if (duration(booking).intValue() <= 0) {
            return false;
        }
        long vehiclesAvailable = checkVehicles(booking.getCity().getId(), booking.getCategory().getId());
        log.info("vehiclesAvailable: " + vehiclesAvailable);
        if (vehiclesAvailable > 0 && bookingSaved.isEmpty()) {
            return true;
        }
        if(vehiclesAvailable == 0){
            return false;
        }

        Long[] days = new Long[duration(booking).intValue()];
        Arrays.fill(days, 0L);

        for (Booking userBooking : bookingSaved) {
            long begin;
            long end;

            if (ChronoUnit.DAYS.between(booking.getCheckInDay(), userBooking.getCheckInDay()) > 0) {
                begin = ChronoUnit.DAYS.between(booking.getCheckInDay(), userBooking.getCheckInDay());
            } else {
                begin = 0L;
            }
            if (ChronoUnit.DAYS.between(userBooking.getCheckOutDay(), booking.getCheckOutDay()) < duration(userBooking)) {
                end = ChronoUnit.DAYS.between(booking.getCheckInDay(), userBooking.getCheckOutDay());
            } else {
                end = duration(booking);
            }
            log.info("begin: " + begin + " end: " + end);

            for (int i = (int) begin; i < (int) end; i++) {
                days[i] += 1L;
            }
            for (long day : days) {
                if (day >= vehiclesAvailable) {
                    log.info("days : " + Arrays.toString(days));
                    return false;
                }
            }
        }
        log.info("days: " + days.length);
        return true;
    }

    public static List<Booking> checkBookings(List<Booking> founded, List<Booking> founded2, List<Booking> founded3, List<Booking> founded4, String categoryName) {
        List<Booking> list = new ArrayList<>(founded.stream().filter(a -> a.getStatus().equals(true)).toList());

        list.addAll(founded2.stream().filter(a -> a.getStatus().equals(true)).toList());
        list.addAll(founded3.stream().filter(a -> a.getStatus().equals(true)).toList());
        list.addAll(founded4.stream().filter(a -> a.getStatus().equals(true)).toList());

        if (list.isEmpty()) new Booking();

        log.info("Quantidade bookings :" + list.size());
        list.forEach(a -> log.info(a.getCategory().getName()));
        return list.stream().filter(a -> a.getCategory().getId().equals(categoryName)).toList();
    }

    public static long checkVehicles(String city, String categoryName) {
        List<Vehicle> vehicles = vehicleRepository.findVehicleByCity_Id(city);
        log.info("checkvehiclesFiltered :" + vehicles.stream().filter(a -> a.getCategory().getId().equals(categoryName)).count());
        return vehicles.stream().filter(a -> a.getCategory().getId().equals(categoryName)).count();
    }
}
