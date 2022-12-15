package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.dtos.requests.PaginatedRequest;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@DisplayName("Paging util tests.")
@ExtendWith(MockitoExtension.class)
public class PagingUtilsImplTests {

    @InjectMocks
    PagingUtilsImpl pagingUtils;

    @Test
    public void shouldParseCorrectly() {
        // Arrange
        var paginatedRequest = new PaginatedRequest(0, "+id", 20);

        // Act
        var result = pagingUtils.parseSorting(paginatedRequest.getSortBy());

        // Assert
        assert Objects.equals(result, Sort.by("id").ascending());
    }

    @Test
    public void shouldParseCorrectlyDescending() {
        // Arrange
        var paginatedRequest = new PaginatedRequest(0, "-id", 20);

        // Act
        var result = pagingUtils.parseSorting(paginatedRequest.getSortBy());

        // Assert
        assert Objects.equals(result, Sort.by("id").descending());
    }

    @Test
    public void shouldParseCorrectlyIfOrderingNotSpecified() {
        // Arrange
        var paginatedRequest = new PaginatedRequest(0, "id", 20);

        // Act
        var result = pagingUtils.parseSorting(paginatedRequest.getSortBy());

        // Assert
        assert Objects.equals(result, Sort.by("id").ascending());
    }
}
