package com.ecoway.api.service;

import com.ecoway.api.exceptions.ResourceNotFoundException;
import com.ecoway.api.exceptions.SaveErrorException;
import com.ecoway.api.model.Booking;
import com.ecoway.api.model.SystemUser;
import com.ecoway.api.model.dto.BookingDTO;
import com.ecoway.api.repository.BookingRepository;
import com.ecoway.api.repository.UserRepository;
import com.ecoway.api.service.util.EmailSenderService;
import com.ecoway.api.service.util.Reservation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    private final BookingRepository repository;
    private final EmailSenderService emailSenderService;
    private final static Logger log = Logger.getLogger(BookingService.class);
    private final UserRepository userRepository;

    @Autowired
    public BookingService(BookingRepository repository, EmailSenderService emailSenderService,
                          UserRepository userRepository) {
        this.repository = repository;
        this.emailSenderService = emailSenderService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getAll() throws ResourceNotFoundException {
        checkForActives();
        List<Booking> founded = repository.findAll();
        if (founded.isEmpty()) throw new ResourceNotFoundException("None Bookings founded");
        return ResponseEntity.ok(founded);
    }

    //vai checar todas as reservas, se a data de checkout for dps do atual dia, vai marcar false
    private void checkForActives() {
        List<Booking> founded = repository.findAll();
        if (founded.isEmpty()) return;
        List<Booking> statusClose =
                founded.stream().filter(b ->
                        b.getCheckOutDay().isBefore(LocalDate.now())).toList();
        statusClose.forEach(status -> status.setStatus(Boolean.FALSE));
    }

    //vai contar todas as reservas ativas na cidade e datas
    public List<Booking> countBookingsActives(LocalDate dayIn, LocalDate dayOut, String categoryName, String city) throws ResourceNotFoundException {
        checkForActives();
        List<Booking> founded = repository.findBookingByCheckinGreaterCheckoutLess(dayIn, dayOut, city);
        List<Booking> founded2 = repository.findByCheckInGreaterCheckoutGreater(dayIn, dayOut, dayOut, city);
        List<Booking> founded3 = repository.findByCheckInLessCheckoutLessButGreaterCheckin(dayIn, dayOut, dayIn, city);
        List<Booking> founded4 = repository.findBookingByCheckInDayLessCheckoutDayGreater(dayIn, dayOut, city);
        log.info(founded.size());
        return Reservation.checkBookings(founded, founded2, founded3, founded4, categoryName);
    }

    public ResponseEntity<?> getById(UUID id) throws ResourceNotFoundException {
        Booking founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None Bookings founded"));
        BookingDTO bookingDTO = new BookingDTO(founded);
        return ResponseEntity.ok(bookingDTO);
    }

    public ResponseEntity<?> getByUserId(UUID id) throws ResourceNotFoundException {
        List<Booking> founded = repository.findBookingByUserId(id);
        if (founded.isEmpty()) throw new ResourceNotFoundException("None Bookings founded");
        return ResponseEntity.ok(founded);
    }

    //antes de salvar ele checa se possui estoque
    public ResponseEntity<?> save(Booking input) throws SaveErrorException, ResourceNotFoundException {

        if (input.getCheckInDay().isBefore(LocalDate.now())) {
            throw new SaveErrorException("A selected data has already been");
        }

        List<Booking> total = countBookingsActives(input.getCheckInDay(), input.getCheckOutDay(),
                input.getCategory().getId(), input.getCity().getId());

        log.info(input.getCheckInDay());
        log.info(input.getCheckOutDay());
        log.info(input.getCity().getId());
        log.info(total);

        if (!Reservation.isBookingPossible(input, total)) {
            throw new SaveErrorException("Not save, full stock");
        }
        try {
            repository.save(input);
        } catch (Exception e) {
            throw new SaveErrorException("Error, not save");
        }
        SystemUser user = userRepository.findById(input.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("None users founded"));
        emailSenderService.sendEmail(user.getEmail(), "Reserva Efetuada",
                "Sua reserva para o dia " + input.getCheckInDay().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")) + " foi confirmada!");

        return new ResponseEntity<>("Booking Created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<?> update(Booking booking) throws SaveErrorException {
        Booking saved;
        try {
            saved = repository.saveAndFlush(booking);
        } catch (Exception e) {
            throw new SaveErrorException("Error, not save");
        }
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> delete(UUID id) throws ResourceNotFoundException {
        Booking founded = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None bookings founded"));
        log.info(founded.getId());
        repository.deleteById(founded.getId());
        return ResponseEntity.ok("Booking" + founded.getCheckInDay() + " deleted");
    }
}
