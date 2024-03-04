package fr.sup.galilee.pharmacy.service;

import java.time.LocalDateTime;
import fr.sup.galilee.pharmacy.dtos.FactureDTO;
import fr.sup.galilee.pharmacy.entities.Cart;
import fr.sup.galilee.pharmacy.entities.Facture;
import fr.sup.galilee.pharmacy.entities.User;
import fr.sup.galilee.pharmacy.mapper.FactureMapper;
import fr.sup.galilee.pharmacy.mapper.UserMapper;
import fr.sup.galilee.pharmacy.repositories.FactureRepository;
import fr.sup.galilee.pharmacy.repositories.ProductCartRepository;
import fr.sup.galilee.pharmacy.utils.Dates;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
public class FactureService
{
    private final FactureRepository factureRepository;
    private final FactureMapper factureMapper;
    private final ProductCartService productCartService;
    private final UserService userService;
    private final UserMapper userMapper;
    @Transactional(readOnly=true)
    public FactureDTO findById(Long id)
    {
        return factureRepository.findById(id)
                .map(factureMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public FactureDTO findOrCreateFact(Long userId) {
        return factureRepository.findByUserId(userId)
                .map(factureMapper::toDTO)
                .orElseGet(() -> {
                    User user = userMapper.toEntity(userService.findById(userId));
                    Facture facture =new Facture();
                    LocalDateTime localDateTime = LocalDateTime.now();
                    Date date = Dates.from(localDateTime);
                    facture.setDate(date.toInstant());
                    facture.setUser(user);
                    facture.setValue(productCartService.getTotal(user.getId()));
                    factureRepository.save(facture);
                    return factureMapper.toDTO(facture);
                });
    }
    @Transactional
    public void update(Long userId)
    {
        factureRepository.findByUserId(userId).map(
                facture ->
                {
                    Date date = Dates.from(LocalDateTime.now());
                    facture.setValue(productCartService.getTotal(userService.findById(userId).getId()));
                    facture.setDate(date.toInstant());
                    factureRepository.save(facture);
                    return facture;
                }
        );
    }
    @Transactional
    public void insert(FactureDTO factureDTO)
    {
        factureDTO.setValue(
                productCartService.getTotal(factureDTO.getUser().getId())
        );
        factureRepository.save(factureMapper.toEntity(factureDTO));
    }

    @Transactional
    public void deleteById(Long id )
    {
        factureRepository.deleteById(id);
    }

}
