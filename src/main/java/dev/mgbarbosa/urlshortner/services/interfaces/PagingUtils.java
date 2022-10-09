package dev.mgbarbosa.urlshortner.services.interfaces;

import org.springframework.data.domain.Sort;

public interface PagingUtils {
    /**
     * Parses string containing the order and the property.
     * @param sortBy The expected order, i.e: +id / -id
     * @return
     */
    Sort parseSorting(String sortBy);
}
