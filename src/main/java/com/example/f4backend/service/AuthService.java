package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.AuthResponse;
import com.example.f4backend.dto.reponse.IntrospectResponse;
import com.example.f4backend.dto.request.AuthRequest;
import com.example.f4backend.dto.request.IntrospectRequest;
import com.example.f4backend.entity.Driver;
import com.example.f4backend.entity.User;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.exception.CustomException;
import com.example.f4backend.repository.DriverRepository;
import com.example.f4backend.repository.InvalidtokenRepository;
import com.example.f4backend.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository userRepository;
    DriverRepository driverRepository;
    InvalidtokenRepository invalidtokenRepository;
    @NonFinal
    @Autowired
    private Dotenv dotenv;
    @NonFinal
    private String SIGNER_KEY;

    @PostConstruct
    public void init() {
        SIGNER_KEY = dotenv.get("SIGNER_KEY");
    }
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token);
        } catch (RuntimeException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }
    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
            throw new CustomException(ErrorCode.UNAUTHENTICATED);

        if (invalidtokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new CustomException(ErrorCode.UNAUTHENTICATED);

        if(invalidtokenRepository.existsById(
                signedJWT.getJWTClaimsSet().getJWTID()))
            throw new CustomException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }

    public AuthResponse authenticate(AuthRequest request){

            var user = userRepository.findByPhone(request.getPhone())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            boolean isAuthenticate = passwordEncoder.matches(request.getPassword(), user.getPassword());

            if(!isAuthenticate)
                throw new CustomException(ErrorCode.LOGIN_FAULT);
            var token  = generateToken(user);
            return AuthResponse.builder()
                    .isAuthenticated(isAuthenticate)
                    .jwt(token)
                    .build();
    }

    public AuthResponse authenticateDriver(AuthRequest request){
        var driver = driverRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isAuthenticate = passwordEncoder.matches(request.getPassword(), driver.getPassword());

        if(!isAuthenticate)
            throw new CustomException(ErrorCode.LOGIN_FAULT);
        var token  = generateTokenDriver(driver);
        return AuthResponse.builder()
                .isAuthenticated(isAuthenticate)
                .jwt(token)
                .build();
    }
    public String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issuer("f4delivery")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }public String generateTokenDriver(Driver driver){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(driver.getDriverId())
                .issuer("f4delivery")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScopeDriver(driver))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
            });
        }
        stringJoiner.add(user.getId());
        return stringJoiner.toString();
    }

    private String buildScopeDriver(Driver driver){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(driver.getRoles())){
            driver.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
            });
        }
        stringJoiner.add(driver.getDriverId());
        return stringJoiner.toString();
    }
}