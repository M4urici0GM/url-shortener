package dev.mgbarbosa.urlshortner.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.PaginatedRequest;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.exceptios.EntityExists;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.services.interfaces.PagingUtils;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DisplayName("User Service Tests")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    SecurityService securityService;

    @Mock
    PagingUtils pagingUtils;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Should map User to UserDto correctly.")
    public void shouldMapCorrectlyToUserDto() {
        // Arrange
        var request = new PaginatedRequest(0, "+id", 20);
        var sort = Sort.by("id").ascending();
        var pageRequest = PageRequest.of(request.getOffset(), request.getPageSize(), sort);
        var expectedUserList = createUserList(10);

        when(pagingUtils.parseSorting("+id")).thenReturn(sort);
        when(userRepository.findAll(ArgumentMatchers.eq(pageRequest))).thenReturn(
                new PageImpl<>(expectedUserList, Pageable.ofSize(expectedUserList.size()), expectedUserList.size()));

        // Act
        var result = userService.getUsers(request);

        // Assert
        assert result.getRecords().size() == 10;
        assert result.getRecords().stream().noneMatch(x -> x.getPassword() != null);
    }

    @Test
    @DisplayName("Should Throw exception if user email already exists.")
    public void shouldThrowExceptionIfUserEmailExists() {
        // Arrange
        var faker = new Faker();
        var randomEmail = faker.internet().emailAddress();
        var user = new UserDto(
            UUID.randomUUID(),
            faker.name().fullName(),
            randomEmail,
            faker.lorem().fixedString(15),
            "");

        when(userRepository.existsByEmail(ArgumentMatchers.eq(randomEmail))).thenReturn(true);

        // Act
        assertThrows(EntityExists.class, () -> userService.createUser(user));
    }

    @Test
    @DisplayName("Should Throw exception if username already exists.")
    public void shouldThrowExceptionIfUsernameExists() {
        // Arrange
        var faker = new Faker();
        var randomEmail = faker.internet().emailAddress();
        var user = new UserDto(
            UUID.randomUUID(),
            faker.name().fullName(),
            randomEmail,
            faker.lorem().fixedString(15),
            "");

        when(userRepository.existsByEmail(ArgumentMatchers.eq(randomEmail))).thenReturn(true);

        // Act
       assertThrows(EntityExists.class, () -> userService.createUser(user));
    }

    @Test
    @DisplayName("Should create user successfully.")
    public void shouldCreateUserCorrectly() {
        // Arrange
        var faker = new Faker();
        var randomPassword = faker.lorem().fixedString(15);
        var randomEmail = faker.internet().emailAddress();
        var passwordHash = BCrypt.hashpw(randomPassword, BCrypt.gensalt());

        var userDto = new UserDto(
            UUID.randomUUID(),
            faker.name().fullName(),
            randomEmail,
            randomPassword,
            faker.internet().avatar());

        var expectedUser = new User(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getUsername(),
                passwordHash);

        when(userRepository.existsByEmail(ArgumentMatchers.eq(randomEmail)))
                .thenReturn(false);

        when(userRepository.existsByUsername(ArgumentMatchers.eq(userDto.getUsername())))
                .thenReturn(false);

        when(securityService.hashPassword(ArgumentMatchers.anyString()))
                .thenReturn(faker.lorem().fixedString(5));

        when(userRepository.save(ArgumentMatchers.any())).thenReturn(expectedUser);

        // Act
        var createdUser = userService.createUser(userDto);

        // Assert
        assert Objects.equals(createdUser.getUsername(), userDto.getUsername());
        assert Objects.equals(createdUser.getName(), userDto.getName());
        assert Objects.equals(createdUser.getEmail(), userDto.getEmail());
        assert Objects.equals(createdUser.getPassword(), null);
    }

    List<User> createUserList(int count) {
        var faker = new Faker();
        var userList = new ArrayList<User>();

        for (int i = 0; i < count; i++) {
            userList.add(new User(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.internet().avatar(),
                    faker.lorem().fixedString(5)));
        }

        return userList;
    }
}
