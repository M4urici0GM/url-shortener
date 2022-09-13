package dev.mgbarbosa.urlshortner.services.interfaces;

import org.springframework.data.domain.Sort;

public interface PagingUtils {
    Sort parseSorting(String sortBy);
}
