package com.feliiks.gardons.feliiks.gardons.services;

import com.feliiks.gardons.entities.FileEntity;
import com.feliiks.gardons.entities.LocationEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.implementations.LocationImpl;
import com.feliiks.gardons.repositories.LocationRepository;
import com.feliiks.gardons.repositories.ReservationRepository;
import com.feliiks.gardons.services.FileService;
import com.feliiks.gardons.services.StripeService;
import com.stripe.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class LocationServiceTests {
    @Mock
    LocationRepository locationRepository;

    @Mock
    StripeService stripeService;

    @Mock
    FileService fileService;

    String name = "file.txt";
    String originalFileName = "file.txt";
    String contentType = "text/plain";
    byte[] content = null;

    @Mock
    ReservationRepository reservationRepository;
    LocationEntity existingLocation = new LocationEntity();
    FileEntity existingFile = new FileEntity();
    Product existingProduct = new Product();
    MultipartFile targetFile = new MockMultipartFile(name, originalFileName, contentType, content);

    @InjectMocks
    private LocationImpl service;

    @BeforeEach
    void setUp() {
        existingLocation.setId(99999L);
        existingLocation.setName("Test Location");
        existingLocation.setImage(existingFile);
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

        existingFile.setId(99999L);
        existingFile.setName("test");
        existingFile.setPath("test");
        existingFile.setPublicUrl("test");
    }

    @Test
    public void findAllSuccessfully() {
        List<LocationEntity> target = List.of(existingLocation);

        Mockito.when(locationRepository.findAll()).thenReturn(target);

        List<LocationEntity> actual = service.findAll();

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findAllIsEmpty() {
        List<LocationEntity> target = new ArrayList<>();

        Mockito.when(locationRepository.findAll()).thenReturn(target);

        List<LocationEntity> actual = service.findAll();

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findAllByPeriodSuccessfully() {
        List<LocationEntity> target = List.of(existingLocation);
        Date from = new Date();
        Date to = new Date();

        Mockito.when(locationRepository.findAll()).thenReturn(target);

        List<LocationEntity> actual = service.findAllByPeriod(from, to);

        Assertions.assertEquals(actual, target);
    }

    @Test
    public void findAllByPeriodIsEmpty() {
        List<LocationEntity> target = new ArrayList<>();
        Date from = new Date();
        Date to = new Date();

        Mockito.when(locationRepository.findAll()).thenReturn(target);

        List<LocationEntity> actual = service.findAllByPeriod(from, to);

        Assertions.assertEquals(actual, target);
    }

    @Test
    public void findByIdSuccessfully() {
        Optional<LocationEntity> target = Optional.of(existingLocation);

        Mockito.when(locationRepository.findById(existingLocation.getId())).thenReturn(target);

        Optional<LocationEntity> actual = service.findById(existingLocation.getId());

        Assertions.assertEquals(actual, target);
    }

    @Test
    public void findByIdIsEmpty() {
        Optional<LocationEntity> target = Optional.empty();

        Mockito.when(locationRepository.findById(existingLocation.getId())).thenReturn(target);

        Optional<LocationEntity> actual = service.findById(existingLocation.getId());

        Assertions.assertEquals(actual, target);
    }

    @Test
    public void createSuccessfully() throws BusinessException {
        LocationEntity target = existingLocation;

        Mockito.when(stripeService.findProductById(existingLocation.getStripeProductId())).thenReturn(Optional.of(existingProduct));
        Mockito.when(locationRepository.save(Mockito.any(LocationEntity.class))).thenReturn(target);

        LocationEntity actual = service.create(existingLocation, targetFile);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void createWithFileEmptyException() {
        LocationEntity target = existingLocation;
        existingLocation.setImage(null);

        Mockito.when(locationRepository.save(Mockito.any(LocationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.create(existingLocation, targetFile));
    }

    @Test
    public void createWithProductDoesNotExistsException() throws BusinessException {
        LocationEntity target = existingLocation;

        Mockito.when(stripeService.findProductById(existingLocation.getStripeProductId())).thenReturn(Optional.empty());
        Mockito.when(locationRepository.save(Mockito.any(LocationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.create(existingLocation, targetFile));
    }

    @Test
    public void editSuccessfully() throws BusinessException {
        LocationEntity target = existingLocation;

        Mockito.when(service.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        Mockito.when(stripeService.findProductById(existingLocation.getStripeProductId())).thenReturn(Optional.of(existingProduct));
        Mockito.when(locationRepository.save(Mockito.any(LocationEntity.class))).thenReturn(target);

        LocationEntity actual = service.editLocation(existingLocation.getId(), existingLocation, targetFile);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void editWithLocationDoesNotExistsException() {
        LocationEntity target = existingLocation;

        Mockito.when(service.findById(existingLocation.getId())).thenReturn(Optional.empty());
        Mockito.when(locationRepository.save(Mockito.any(LocationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.create(existingLocation, targetFile));
    }

    @Test
    public void editWithProductDoesNotExistsException() throws BusinessException {
        LocationEntity target = existingLocation;

        Mockito.when(service.findById(existingLocation.getId())).thenReturn(Optional.of(existingLocation));
        Mockito.when(stripeService.findProductById(existingLocation.getStripeProductId())).thenReturn(Optional.empty());
        Mockito.when(locationRepository.save(Mockito.any(LocationEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.create(existingLocation, targetFile));
    }

    @Test
    public void deleteByIdSuccessfully() throws BusinessException {
        Optional<LocationEntity> target = Optional.of(existingLocation);

        Mockito.when(service.findById(existingLocation.getId())).thenReturn(target);
        Mockito.doNothing().when(locationRepository).deleteById(Mockito.anyLong());
        Mockito.doNothing().when(reservationRepository).deleteById(Mockito.anyLong());

        Optional<LocationEntity> actual = service.deleteById(existingLocation.getId());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void deleteByIdIsEmpty() throws BusinessException {
        Optional<LocationEntity> target = Optional.empty();

        Mockito.when(service.findById(existingLocation.getId())).thenReturn(Optional.empty());
        Mockito.doNothing().when(locationRepository).deleteById(Mockito.anyLong());
        Mockito.doNothing().when(reservationRepository).deleteById(Mockito.anyLong());

        Optional<LocationEntity> actual = service.deleteById(existingLocation.getId());

        Assertions.assertEquals(target, actual);
    }
}
