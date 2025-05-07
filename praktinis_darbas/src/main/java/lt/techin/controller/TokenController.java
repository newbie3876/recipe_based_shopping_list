package lt.techin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TokenController {

  private JwtEncoder encoder;
  

  @Autowired
  public TokenController(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  @PostMapping("/token")
  public String token(Authentication authentication) {
    Instant now = Instant.now();
    long expiry = 36000L;

    String scope = authentication.getAuthorities().stream()
            .map(s -> s.getAuthority())
            .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(authentication.getName())
            .claim("scope", scope)
            .build();

    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
