package com.feliiks.gardons.feliiks.gardons.services;

import com.feliiks.gardons.entities.*;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.implementations.ReservationImpl;
import com.feliiks.gardons.repositories.ReservationRepository;
import com.feliiks.gardons.services.LocationService;
import com.feliiks.gardons.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ReservationServiceTests {
    @Mock
    ReservationRepository reservationRepository;

    @Mock
    UserService userService;

    @Mock
    LocationService locationService;
    ReservationEntity existingReservation = new ReservationEntity();
    ReservationStatusEnum existingReservationStatusEnum = ReservationStatusEnum.OPEN;
    LocationEntity existingLocation = new LocationEntity();
    UserEntity existingUser = new UserEntity();
    @InjectMocks
    private ReservationImpl service;

    @BeforeEach
    void setUp() {
        existingUser.setId(99999L);
        existingUser.setFirstname("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john.doe@gmail.com");
        existingUser.setPassword("Password1234!!");
        existingUser.setGoogle_id("1234ABCD");
        existingUser.setRole(new RoleEntity());

        existingLocation.setId(99999L);
        existingLocation.setName("Test Location");
        existingLocation.setImage(new FileEntity());
        existingLocation.setDescription("");
        existingLocation.setParking(true);
        existingLocation.setKitchen(true);
        existingLocation.setWifi(true);
        existingLocation.setSanitary(true);
        existingLocation.setHeater(true);
        existingLocation.setAir_conditioner(true);
        existingLocation.setTerrace(true);
        existingLocation.setBarbecue(true);
        existingLocation.setSurface(32);
        existingLocation.setMax_persons(6);
        existingLocation.setPrice_per_night(150);
        existingLocation.setBedrooms(2);
        existingLocation.setSlot_remaining(50);

        existingReservation.setId(99999L);
        existingReservation.setUser(existingUser);
        existingReservation.setLocation(existingLocation);
        existingReservation.setReservation_key("12345678");
        existingReservation.setAdult_nbr(1);
        existingReservation.setChild_nbr(1);
        existingReservation.setAnimal_nbr(1);
        existingReservation.setVehicle_nbr(1);
        existingReservation.setFrom(new Date());
        existingReservation.setTo(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));
        existingReservation.setNight_number(1);
        existingReservation.setTotal_price(100);
        existingReservation.setStatus(ReservationStatusEnum.OPEN);
        existingReservation.setUser_contact(null);
        existingReservation.setUser_comment(null);
    }

    @Test
    public void findAllSuccessfully() {
        List<ReservationEntity> target = List.of(existingReservation);

        Mockito.when(reservationRepository.findAll()).thenReturn(target);

        List<ReservationEntity> actual = service.findAll();

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findAllIsEmpty() {
        List<ReservationEntity> target = new ArrayList<>();

        Mockito.when(reservationRepository.findAll()).thenReturn(target);

        List<ReservationEntity> actual = service.findAll();

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByIdSuccessfully() {
        Optional<ReservationEntity> target = Optional.of(existingReservation);

        Mockito.when(reservationRepository.findById(existingReservation.getId())).thenReturn(target);

        Optional<ReservationEntity> actual = service.findById(existingReservation.getId());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByIdIsEmpty() {
        Optional<ReservationEntity> target = Optional.empty();

        Mockito.when(reservationRepository.findById(existingReservation.getId())).thenReturn(target);

        Optional<ReservationEntity> actual = service.findById(existingReservation.getId());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findBySessionIdSuccessfully() {
        Optional<ReservationEntity> target = Optional.of(existingReservation);

        Mockito.when(reservationRepository.findBySessionId(existingReservation.getStripe_session_id())).thenReturn(target);

        Optional<ReservationEntity> actual = service.findBySessionId(existingReservation.getStripe_session_id());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findBySessionIdIsEmpty() {
        Optional<ReservationEntity> target = Optional.empty();

        Mockito.when(reservationRepository.findBySessionId(existingReservation.getStripe_session_id())).thenReturn(target);

        Optional<ReservationEntity> actual = service.findBySessionId(existingReservation.getStripe_session_id());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByStatusSuccessfully() {
        List<ReservationEntity> target = List.of(existingReservation);

        Mockito.when(reservationRepository.findAll()).thenReturn(target);

        List<ReservationEntity> actual = service.findByStatus(existingReservationStatusEnum);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByStatusIsEmpty() {
        List<ReservationEntity> target = new ArrayList<>();

        Mockito.when(reservationRepository.findAll()).thenReturn(target);

        List<ReservationEntity> actual = service.findByStatus(existingReservationStatusEnum);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByReservationKeySuccessfully() {
        Optional<ReservationEntity> target = Optional.of(existingReservation);

        Mockito.when(reservationRepository.findByReservationKey(existingReservation.getReservation_key())).thenReturn(target);

        Optional<ReservationEntity> actual = service.findByReservationKey(existingReservation.getReservation_key());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByReservationKeyIsEmpty() {
        Optional<ReservationEntity> target = Optional.empty();

        Mockito.when(reservationRepository.findByReservationKey(existingReservation.getReservation_key())).thenReturn(target);

        Optional<ReservationEntity> actual = service.findByReservationKey(existingReservation.getReservation_key());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByLocationSuccessfully() {
        List<ReservationEntity> target = List.of(existingReservation);

        Mockito.when(reservationRepository.findByLocation(existingLocation)).thenReturn(target);

        List<ReservationEntity> actual = service.findByLocation(existingLocation);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByLocationIsEmpty() {
        List<ReservationEntity> target = new ArrayList<>();

        Mockito.when(reservationRepository.findByLocation(existingLocation)).thenReturn(target);

        List<ReservationEntity> actual = service.findByLocation(existingLocation);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void createSuccessfully() throws BusinessException {
        ReservationEntity target = existingReservation;

        Mockito.when(userService.findById(existingReservation.getUser().getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(locationService.findById(existingReservation.getLocation().getId())).thenReturn(Optional.of(existingLocation));
        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        ReservationEntity actual = service.create(existingReservation);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void createWithIncorrectDateException() {
        ReservationEntity target = existingReservation;
        existingReservation.setFrom(new Date());
        existingReservation.setTo(new Date(new Date().getTime() - (1000 * 60 * 60 * 24)));

        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.create(existingReservation));
    }

    @Test
    public void createWithIncorrectUserOrLocationException() {
        ReservationEntity target = existingReservation;

        Mockito.when(userService.findById(existingReservation.getUser().getId())).thenReturn(Optional.empty());
        Mockito.when(locationService.findById(existingReservation.getLocation().getId())).thenReturn(Optional.empty());
        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.create(existingReservation));
    }

    @Test
    public void createWithUnavailableLocationException() {
        ReservationEntity target = existingReservation;

        existingLocation.setSlot_remaining(0);

        Mockito.when(userService.findById(existingReservation.getUser().getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(locationService.findById(existingReservation.getLocation().getId())).thenReturn(Optional.of(existingLocation));
        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.create(existingReservation));
    }

    @Test
    public void editSuccessfully() throws BusinessException {
        ReservationEntity target = existingReservation;

        Mockito.when(service.findById(existingReservation.getId())).thenReturn(Optional.of(existingReservation));
        Mockito.when(userService.findById(existingReservation.getUser().getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(locationService.findById(existingReservation.getLocation().getId())).thenReturn(Optional.of(existingLocation));
        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        ReservationEntity actual = service.editReservation(existingReservation.getId(), existingReservation);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void editWithReservationDoesNotExistsException() {
        ReservationEntity target = existingReservation;

        Mockito.when(service.findById(existingReservation.getId())).thenReturn(Optional.empty());
        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.editReservation(existingReservation.getId(), existingReservation));
    }

    @Test
    public void editWithUserDoesNotExistsException() {
        ReservationEntity target = existingReservation;

        Mockito.when(service.findById(existingReservation.getId())).thenReturn(Optional.of(existingReservation));
        Mockito.when(userService.findById(existingReservation.getUser().getId())).thenReturn(Optional.empty());
        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.editReservation(existingReservation.getId(), existingReservation));
    }

    @Test
    public void editWithLocationDoesNotExistsException() {
        ReservationEntity target = existingReservation;

        ReservationEntity fakePatch = new ReservationEntity();
        LocationEntity fakeLocation = new LocationEntity();
        fakeLocation.setId(888L);
        fakePatch.setLocation(fakeLocation);

        Mockito.when(service.findById(existingReservation.getId())).thenReturn(Optional.of(existingReservation));
        Mockito.when(userService.findById(existingReservation.getUser().getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(locationService.findById(existingReservation.getLocation().getId())).thenReturn(Optional.empty());
        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.editReservation(existingReservation.getId(), fakePatch));
    }

    @Test
    public void editWithUnavailableLocationException() {
        ReservationEntity target = existingReservation;

        existingLocation.setSlot_remaining(0);

        ReservationEntity fakePatch = new ReservationEntity();
        LocationEntity fakeLocation = new LocationEntity();
        fakeLocation.setId(888L);
        fakePatch.setLocation(fakeLocation);

        ReservationEntity conflictualReservation = new ReservationEntity();
        conflictualReservation.setFrom(new Date());
        conflictualReservation.setTo(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));
        conflictualReservation.setStatus(ReservationStatusEnum.COMPLETE);

        Mockito.when(service.findById(existingReservation.getId())).thenReturn(Optional.of(existingReservation));
        Mockito.when(userService.findById(existingReservation.getUser().getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(locationService.findById(existingReservation.getLocation().getId())).thenReturn(Optional.of(existingLocation));
        Mockito.when(service.findByLocation(existingReservation.getLocation())).thenReturn(List.of(conflictualReservation));
        Mockito.when(reservationRepository.save(Mockito.any(ReservationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.editReservation(existingReservation.getId(), fakePatch));
    }

    @Test
    public void deleteByIdSuccessfully() {
        Optional<ReservationEntity> target = Optional.of(existingReservation);

        Mockito.when(service.findById(existingReservation.getId())).thenReturn(target);
        Mockito.doNothing().when(reservationRepository).deleteById(Mockito.anyLong());

        Optional<ReservationEntity> actual = service.deleteById(existingReservation.getId());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void deleteByIdIsEmpty() {
        Optional<ReservationEntity> target = Optional.empty();

        Mockito.when(service.findById(existingReservation.getId())).thenReturn(target);
        Mockito.doNothing().when(reservationRepository).deleteById(Mockito.anyLong());

        Optional<ReservationEntity> actual = service.deleteById(existingReservation.getId());

        Assertions.assertEquals(target, actual);
    }
}
