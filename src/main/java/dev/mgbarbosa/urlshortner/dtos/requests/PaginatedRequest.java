package dev.mgbarbosa.urlshortner.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class  PaginatedRequest {
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
