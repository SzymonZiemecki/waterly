package pl.lodz.p.it.ssbd2023.ssbd06.mol.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.it.ssbd2023.ssbd06.persistence.entities.Apartment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDto {

    Long id;
    String number;
    BigDecimal area;
    Long ownerId;

    //TODO Water meters list

    public ApartmentDto(final Apartment apartment) {
        this.id = apartment.getId();
        this.number = apartment.getNumber();
        this.area = apartment.getArea();
        this.ownerId = apartment.getOwner().getId();
    }

}
