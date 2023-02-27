package org.viktor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "driverLicenceNo")
@ToString(exclude = "user")
@Builder
@Entity
@Table(name = "client_data")
public class ClientDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String driverLicenceNo;
    private LocalDate dateExpiry;
    private Integer driverExperience;

    public void setUser(UserEntity user) {
        this.user = user;
        user.setClientData(this);
    }
}