package dev.mgbarbosa.urlshortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
@Setter
public class PaginatedRequest {
    @Min(0)
    int offset;

    @NotEmpty
    String sortBy;

    @Min(0)
    int pageSize;

    public PaginatedRequest() {
        this.offset = 0;
        this.pageSize = 20;
        this.sortBy = "+id";
    }
}
