package com.example.f4backend.configuration;

import com.example.f4backend.entity.*;
import com.example.f4backend.repository.*;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    VehicleTypeRepository vehicleTypeRepository;
    DocumentStatusRepository documentStatusRepository;
    OrderStatusRepository orderStatusRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName("ADMIN");
                        newRole.setDescription("Only for Admin");
                        return roleRepository.save(newRole);
                    });

                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Set.of(adminRole))
                        .build();
                userRepository.save(admin);
            }

            // Preload VehicleType
            if (vehicleTypeRepository.count() == 0) {
                String[] vehicleTypes = {"Xe máy", "Xe 4 chỗ", "Xe 7 chỗ", "Xe tải"};
                for (String typeName : vehicleTypes) {
                    VehicleType vehicleType = new VehicleType();
                    vehicleType.setVehicleTypeName(typeName);
                    vehicleType.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
                    vehicleTypeRepository.save(vehicleType);
                }
            }

            // Preload DocumentStatus
            if (documentStatusRepository.count() == 0) {
                String[] statuses = {"PENDING", "APPROVED", "REJECTED"};
                for (String statusName : statuses) {
                    DocumentStatus status = new DocumentStatus();
                    status.setStatusName(statusName);
                    status.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
                    documentStatusRepository.save(status);
                }
                log.info("Preloaded DocumentStatus data");
            }

            // Preload OrderStatus
            if (orderStatusRepository.count() == 0) {
                String[] orderStatuses = {"PENDING", "PROCESSING", "COMPLETED", "CANCELLED"};
                for (String statusName : orderStatuses) {
                    OrderStatus status = new OrderStatus();
                    status.setStatusName(statusName);
                    orderStatusRepository.save(status);
                }
                log.info("Preloaded OrderStatus data");
            }
        };
    }


}
