package fr.sup.galilee.pharmacy.mapper;

import fr.sup.galilee.pharmacy.dtos.FactureDTO;
import fr.sup.galilee.pharmacy.entities.Facture;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FactureMapper
{
    public FactureDTO toDTO(Facture facture)
    {
        FactureDTO.FactureDTOBuilder factureDTOBuilder = FactureDTO.builder()
                .date(facture.getDate())
                .id(facture.getId())
                .value(facture.getValue())
                .user(facture.getUser());
        return factureDTOBuilder.build();
    }

    public Facture toEntity(FactureDTO factureDTO)
    {
        Facture facture =new Facture();
        facture.setId(factureDTO.getId());
        facture.setUser(factureDTO.getUser());
        facture.setDate(factureDTO.getDate());
        facture.setValue(factureDTO.getValue());
        return facture;
    }

}
