package dev.mgbarbosa.urlshortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginatedResponse<T> {
    int page;
    int pageSize;
    List<T> records;
}
