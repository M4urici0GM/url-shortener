package dev.mgbarbosa.urlshortner.services;

import dev.mgbarbosa.urlshortner.services.interfaces.PagingUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PagingUtilsImpl implements PagingUtils {
    @Override
    public Sort parseSorting(String sortBy) {
        if (!sortBy.contains("+") && !sortBy.contains("-")) {
            return Sort.by(sortBy).ascending();
        }

        var startChar = sortBy.charAt(0);
        var propertyName = sortBy.substring(1);

        return startChar == '+'
                ? Sort.by(propertyName).ascending()
                : Sort.by(propertyName).descending();
    }
}
