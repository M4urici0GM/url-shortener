package dev.mgbarbosa.urlshortner.services.interfaces;

import dev.mgbarbosa.urlshortner.dtos.requests.PaginatedRequest;
import dev.mgbarbosa.urlshortner.dtos.responses.PaginatedResponse;
import dev.mgbarbosa.urlshortner.dtos.UserDto;
import dev.mgbarbosa.urlshortner.exceptios.EntityExists;

public interface UserService {
    PaginatedResponse<UserDto> getUsers(PaginatedRequest request);
    UserDto createUser(UserDto user) throws EntityExists;
}
