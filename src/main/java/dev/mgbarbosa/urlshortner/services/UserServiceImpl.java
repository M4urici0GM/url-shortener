package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.dtos.requests.PaginatedRequest;
import dev.mgbarbosa.urlshortner.dtos.responses.PaginatedResponse;
import dev.mgbarbosa.urlshortner.entities.User;
import dev.mgbarbosa.urlshortner.exceptios.EntityExists;
import dev.mgbarbosa.urlshortner.repositories.interfaces.UserRepository;
import dev.mgbarbosa.urlshortner.services.interfaces.PagingUtils;
import dev.mgbarbosa.urlshortner.services.interfaces.SecurityService;
import dev.mgbarbosa.urlshortner.services.interfaces.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final PagingUtils pagingUtils;

    public UserServiceImpl(
            UserRepository userRepository,
            SecurityService securityService,
            PagingUtils pagingUtils) {
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.pagingUtils = pagingUtils;
    }

    @Override
    public PaginatedResponse<UserDto> getUsers(PaginatedRequest request) {
        var sort = pagingUtils.parseSorting(request.getSortBy());
        var pageRequest = PageRequest.of(request.getOffset(), request.getPageSize(), sort);
        var users = userRepository
                .findAll(pageRequest)
                .toList()
                .stream()
                .map(UserDto::new)
                .toList();

        return new PaginatedResponse<>(
                request.getOffset(),
                request.getPageSize(),
                users);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto user) throws EntityExists {
        if (userExists(user.getEmail()) || userExistsByUsername(user.getUsername())) {
            throw new EntityExists("user", user.getEmail());
        }

        var passwordHash = securityService.hashPassword(user.getPassword());
        var createdUser = userRepository.save(new User(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                passwordHash));

        return new UserDto(createdUser);
    }

    boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    boolean userExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
