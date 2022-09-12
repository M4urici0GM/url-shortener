package dev.mgbarbosa.urlshortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
public class PaginatedRequest {

    @Getter
    @Setter
    @Min(0)
    int offset;

    @Getter
    @Setter
    int pageSize;

    @Getter
    @Setter
    @NotEmpty
    String sortBy;

    public PaginatedRequest() {
        this.offset = 0;
        this.pageSize = 20;
        this.sortBy = "+id";
    }
}
