package com.ecoway.api.repository;

import com.ecoway.api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findBookingByUserId(UUID id);
    List<Booking> findBookingByCheckInDayIsGreaterThanEqualAndCheckOutDayIsLessThanEqualAndCityId(LocalDate checkInDay, LocalDate checkOutDay, String city_cityName);

    List<Booking> findBookingByCheckInDayIsGreaterThanEqualAndCheckInDayIsLessThanEqualAndCheckOutDayIsGreaterThanAndCityId(LocalDate checkInDay, LocalDate checkOut, LocalDate checkIn, String city_cityName);

    List<Booking> findBookingByCheckInDayIsLessThanAndCheckOutDayIsLessThanEqualAndCheckOutDayIsGreaterThanAndCityId(LocalDate checkInDay, LocalDate checkOutDay, LocalDate checkIn, String city_cityName);
    List<Booking> findBookingByCheckInDayIsLessThanAndCheckOutDayIsGreaterThanEqualAndCityId(LocalDate checkInDay, LocalDate checkOutDay, String city_cityName);

    //checkin maior e checkout menor
    default List<Booking> findBookingByCheckinGreaterCheckoutLess(LocalDate checkInDay, LocalDate checkOutDay, String city_cityName) {
        return findBookingByCheckInDayIsGreaterThanEqualAndCheckOutDayIsLessThanEqualAndCityId(checkInDay, checkOutDay, city_cityName);
    }
    //checkin menor e checkout maior
    default List<Booking> findBookingByCheckInDayLessCheckoutDayGreater(LocalDate checkInDay, LocalDate checkOutDay, String city_cityName){
        return findBookingByCheckInDayIsLessThanAndCheckOutDayIsGreaterThanEqualAndCityId(checkInDay, checkOutDay, city_cityName);
    }

    //checkin maior ou igual mas menor que checkout, checkout maior
    default List<Booking> findByCheckInGreaterCheckoutGreater(LocalDate checkInDay, LocalDate checkOut, LocalDate checkOutDay, String city_cityName) {
        return findBookingByCheckInDayIsGreaterThanEqualAndCheckInDayIsLessThanEqualAndCheckOutDayIsGreaterThanAndCityId(checkInDay, checkOut, checkOutDay, city_cityName);
    }

    //checkin menor e chekcout menor , checkout depois do checkin
    default List<Booking> findByCheckInLessCheckoutLessButGreaterCheckin(LocalDate checkInDay, LocalDate checkOutDay, LocalDate checkIn, String city_cityName) {
        return findBookingByCheckInDayIsLessThanAndCheckOutDayIsLessThanEqualAndCheckOutDayIsGreaterThanAndCityId(checkInDay, checkOutDay, checkIn, city_cityName);
    }
}
