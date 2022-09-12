package dev.mgbarbosa.urlshortner.services.interfaces;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


public interface PagingUtils {
    Sort parseSorting(String sortBy);
}
