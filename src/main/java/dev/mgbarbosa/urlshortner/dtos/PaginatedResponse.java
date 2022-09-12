package dev.mgbarbosa.urlshortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.List;

@AllArgsConstructor
public class PaginatedResponse<T> {
    @Min(0)
    @Getter
    @Setter
    int page;

    @Getter
    @Setter
    @Min(1)
    int pageSize;

    @Getter
    @Setter
    List<T> records;
}
