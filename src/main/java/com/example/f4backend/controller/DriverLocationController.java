package com.example.f4backend.controller;

import com.example.f4backend.dto.request.UpdateDriverLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class DriverLocationController {
    private final RedisTemplate<String, String> redisTemplate;
    private final static String GEO_KEY = "driver_locations";
    private final static long ONLINE_TTL_SECONDS = 60;

    @PostMapping("/update")
    public ResponseEntity<Void> updateLocation(@RequestBody UpdateDriverLocation request) {
        redisTemplate.opsForGeo().add(GEO_KEY,
                new Point(request.getLongitude(), request.getLatitude()),
                request.getDriverId());

        String statusKey = "driver_status:" + request.getDriverId();
        Boolean exists = redisTemplate.hasKey(statusKey);

        if (exists == null || !exists) {
            Map<String, String> driverInfoMap = new HashMap<>();
            driverInfoMap.put("status", "AVAILABLE");
            driverInfoMap.put("vehicleTypeId", String.valueOf(1));
            redisTemplate.opsForHash().putAll(statusKey, driverInfoMap);
        } else {
            Object statusObj = redisTemplate.opsForHash().get(statusKey, "status");
            String currentStatus = statusObj != null ? statusObj.toString() : "UNKNOWN";

            if (!"BUSY".equalsIgnoreCase(currentStatus)) {
                redisTemplate.opsForHash().put(statusKey, "status", "AVAILABLE");
            }
        }

        redisTemplate.expire(statusKey, Duration.ofSeconds(ONLINE_TTL_SECONDS));
        return ResponseEntity.ok().build();
    }
    @PostMapping("/busy")
    public ResponseEntity<Void> updateBusy(@RequestBody UpdateDriverLocation request) {

        redisTemplate.opsForGeo().add(GEO_KEY,
                new Point(request.getLongitude(), request.getLatitude()),
                request.getDriverId());

        String statusKey = "driver_status:" + request.getDriverId();

        // Luôn cập nhật status thành BUSY
        redisTemplate.opsForHash().put(statusKey, "status", "BUSY");
        redisTemplate.opsForHash().put(statusKey, "vehicleTypeId", String.valueOf(1));

        // Cập nhật TTL để giữ driver online
        redisTemplate.expire(statusKey, Duration.ofSeconds(ONLINE_TTL_SECONDS));

        return ResponseEntity.ok().build();
    }

}