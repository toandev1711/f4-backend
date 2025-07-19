package com.example.f4backend.repository;

import com.example.f4backend.entity.Driver;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, String>, JpaSpecificationExecutor<Driver> {
    boolean existsByDriverId(@NotNull String driverId);
    // Optional<User> findByPhone(String phoneNumber);
    Optional<Driver> findByPhone(String phoneNumber);

    boolean existsByPhone(String phone);
    // Optional<Driver> findByUserId(String userId);

    @Query("SELECT d FROM Driver d WHERE " +
            "(:status = -1 OR d.driverStatus.driverStatusId = :status) AND " +
            "(LOWER(d.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "d.phone LIKE CONCAT('%', :searchTerm, '%') OR " +
            "LOWER(d.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Driver> findBySearchTermAndStatus(@Param("searchTerm") String searchTerm,
                                           @Param("status") int status,
                                           Pageable pageable);
    @Query(value = "SELECT * FROM drivers WHERE driver_id IN (:ids)", nativeQuery = true)
    List<Driver> findDriversByIds(@Param("ids") List<String> ids);

    Driver findByDriverId(String id);
}
