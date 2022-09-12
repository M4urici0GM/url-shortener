package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.dtos.PaginatedRequest;
import dev.mgbarbosa.urlshortner.dtos.PaginatedResponse;
import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.exceptios.EntityExists;

public interface UserService {
    PaginatedResponse<UserDto> getUsers(PaginatedRequest request);
    UserDto createUser(UserDto user) throws EntityExists;
}
