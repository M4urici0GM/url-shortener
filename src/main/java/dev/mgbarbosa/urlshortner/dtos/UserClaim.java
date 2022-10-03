package dev.mgbarbosa.urlshortner.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserClaim {
    private String name;
    private String value;
}
