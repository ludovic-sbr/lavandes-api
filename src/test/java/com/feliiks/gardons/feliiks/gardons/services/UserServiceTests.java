package com.feliiks.gardons.feliiks.gardons.services;

import com.feliiks.gardons.entities.RoleEntity;
import com.feliiks.gardons.entities.RoleEnum;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.implementations.UserImpl;
import com.feliiks.gardons.repositories.ReservationRepository;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceTests {
    @Mock
    UserRepository userRepository;

    @Mock
    ReservationRepository reservationRepository;
    UserEntity existingUser = new UserEntity();
    RoleEnum existingRoleEnum = RoleEnum.USER;
    RoleEntity existingRole = new RoleEntity(existingRoleEnum.name());
    @Mock
    private RoleService roleService;
    @InjectMocks
    private UserImpl service;

    @BeforeEach
    void setUp() {
        existingUser.setId(99999L);
        existingUser.setFirstname("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john.doe@gmail.com");
        existingUser.setPassword("Password1234!!");
        existingUser.setGoogle_id("1234ABCD");
        existingUser.setRole(existingRole);
    }

    @Test
    public void findAllSuccessfully() {
        List<UserEntity> target = List.of(existingUser);

        Mockito.when(userRepository.findAll()).thenReturn(target);

        List<UserEntity> actual = service.findAll();

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findAllIsEmpty() {
        List<UserEntity> target = new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(target);

        List<UserEntity> actual = service.findAll();

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByIdSuccessfully() {
        Optional<UserEntity> target = Optional.of(existingUser);

        Mockito.when(service.findById(existingUser.getId())).thenReturn(target);

        Optional<UserEntity> actual = service.findById(existingUser.getId());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByIdIsEmpty() {
        Optional<UserEntity> target = Optional.empty();

        Mockito.when(service.findById(existingUser.getId())).thenReturn(target);

        Optional<UserEntity> actual = service.findById(existingUser.getId());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByEmailSuccessfully() {
        Optional<UserEntity> target = Optional.of(existingUser);

        Mockito.when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(target);

        Optional<UserEntity> actual = service.findByEmail(existingUser.getEmail());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByEmailIsEmpty() {
        Optional<UserEntity> target = Optional.empty();

        Mockito.when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(target);

        Optional<UserEntity> actual = service.findByEmail(existingUser.getEmail());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByGoogleIdSuccessfully() {
        Optional<UserEntity> target = Optional.of(existingUser);

        Mockito.when(userRepository.findByGoogleId(existingUser.getGoogle_id())).thenReturn(target);

        Optional<UserEntity> actual = service.findByGoogleId(existingUser.getGoogle_id());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void findByGoogleIdIsEmpty() {
        Optional<UserEntity> target = Optional.empty();

        Mockito.when(userRepository.findByGoogleId(existingUser.getGoogle_id())).thenReturn(target);

        Optional<UserEntity> actual = service.findByGoogleId(existingUser.getGoogle_id());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void createSuccessfully() throws BusinessException {
        UserEntity target = existingUser;

        Mockito.when(service.findByEmail(existingUser.getEmail())).thenReturn(Optional.empty());
        Mockito.when(roleService.findByName(existingUser.getRole().getName())).thenReturn(Optional.of(existingRole));
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(target);

        UserEntity actual = service.register(existingUser);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void createWithUserAlreadyExistsException() {
        Mockito.when(service.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        Assertions.assertThrows(BusinessException.class, () -> service.register(existingUser));
    }

    @Test
    public void createWithIncorrectPasswordException() {
        UserEntity target = existingUser;
        existingUser.setPassword("123");

        Mockito.when(service.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(service.findByEmail(existingUser.getEmail())).thenReturn(Optional.empty());
        Mockito.when(roleService.findByName(existingUser.getRole().getName())).thenReturn(Optional.of(existingRole));
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(target);

        Assertions.assertThrows(BusinessException.class, () -> service.register(existingUser));
    }

    @Test
    public void editUserSuccessfully() throws BusinessException {
        UserEntity target = existingUser;

        Mockito.when(service.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(service.findByEmail(existingUser.getEmail())).thenReturn(Optional.empty());
        Mockito.when(roleService.findByName(existingUser.getRole().getName())).thenReturn(Optional.of(existingRole));
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(target);

        UserEntity actual = service.editUser(existingUser.getId(), existingUser);

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void editUserWithUserDoesNotExistsException() {
        Mockito.when(service.findById(existingUser.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(BusinessException.class, () -> service.editUser(existingUser.getId(), existingUser));
    }

    @Test
    public void editUserWithEmailAlreadyExistsException() {
        UserEntity patch = new UserEntity();
        patch.setEmail("johnny.doeeh@gmail.com");

        Mockito.when(service.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(service.findByEmail(patch.getEmail())).thenReturn(Optional.of(existingUser));

        Assertions.assertThrows(BusinessException.class, () -> service.editUser(existingUser.getId(), patch));
    }

    @Test
    public void editUserWithRoleDoesNotExistsException() {
        Mockito.when(service.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(roleService.findByName(existingUser.getRole().getName())).thenReturn(Optional.empty());

        Assertions.assertThrows(BusinessException.class, () -> service.editUser(existingUser.getId(), existingUser));
    }

    @Test
    public void deleteByIdSuccessfully() throws BusinessException {
        Optional<UserEntity> target = Optional.of(existingUser);

        Mockito.when(service.findById(existingUser.getId())).thenReturn(target);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());
        Mockito.doNothing().when(reservationRepository).deleteById(Mockito.anyLong());

        Optional<UserEntity> actual = service.deleteById(existingUser.getId());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void deleteByIdIsEmpty() throws BusinessException {
        Optional<UserEntity> target = Optional.empty();

        Mockito.when(service.findById(existingUser.getId())).thenReturn(target);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());
        Mockito.doNothing().when(reservationRepository).deleteById(Mockito.anyLong());

        Optional<UserEntity> actual = service.deleteById(existingUser.getId());

        Assertions.assertEquals(target, actual);
    }

    @Test
    public void deleteByIdWithSpecialUser() {
        Optional<UserEntity> target = Optional.of(existingUser);
        existingUser.setRole(new RoleEntity("DEVELOPER"));

        Mockito.when(service.findById(existingUser.getId())).thenReturn(target);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyLong());
        Mockito.doNothing().when(reservationRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(BusinessException.class, () -> service.deleteById(existingUser.getId()));
    }
}
