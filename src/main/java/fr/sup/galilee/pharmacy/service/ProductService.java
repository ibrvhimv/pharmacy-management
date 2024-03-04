package fr.sup.galilee.pharmacy.service;

import fr.sup.galilee.pharmacy.dtos.ProductDTO;
import fr.sup.galilee.pharmacy.dtos.UserDTO;
import fr.sup.galilee.pharmacy.mapper.ProductMapper;
import fr.sup.galilee.pharmacy.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return
                productRepository.findAll()
                        .stream()
                        .map(productMapper::toDto)
                        .toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void insert(ProductDTO productDTO) {
        productRepository.save(productMapper.toEntity(productDTO));
    }

    @Transactional
    public void update(ProductDTO productDTO) {
        productRepository.findById(productDTO.getId())
                .ifPresent(
                        product -> {
                            product.setId(productDTO.getId());
                            product.setName(productDTO.getName());
                            product.setPrix(productDTO.getPrix());
                            product.setQuantity(productDTO.getQuantity());
                            productRepository.save(product);
                        }
                );
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
