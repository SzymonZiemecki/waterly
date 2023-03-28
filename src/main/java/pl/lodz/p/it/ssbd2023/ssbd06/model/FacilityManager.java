package pl.lodz.p.it.ssbd2023.ssbd06.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facility_manager")
@DiscriminatorValue("FACILITY_MANAGER")
@NoArgsConstructor
public class FacilityManager extends Role {
}
