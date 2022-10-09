package dev.mgbarbosa.urlshortner.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SecurityTokenTicket {
    public String userId;
    public UUID nonce;
    public UUID secret;

    public SecurityTokenTicket(String userId)
    {
        this.userId = userId;
        nonce = UUID.randomUUID();
        secret = UUID.randomUUID();
    }
}
