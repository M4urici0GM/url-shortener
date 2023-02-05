package dev.mgbarbosa.urlshortner.dtos.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedResponse<T> {
    int page;
    int pageSize;
    List<T> records;
}
