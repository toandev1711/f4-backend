package com.example.f4backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "drivers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String driverId;

    @ManyToOne
    @JoinColumn(name = "driver_type_id", nullable = false)
    private DriverType driverType;

    @ManyToOne
    @JoinColumn(name = "driver_status_id", nullable = false)
    private DriverStatus driverStatus;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "portrait")
    private String portrait;

    @Column(name = "date_birth")
    private Date dateBirth;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "isLocked")
    private Boolean isLocked;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "drivers_roles",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
