package com.inventory.api.service;

import com.inventory.api.exception.OutOfStockException;
import com.inventory.api.exception.ProductNotFoundException;
import com.inventory.api.model.Product;
import com.inventory.api.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        product.setId(null);
        return repository.save(product);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public List<Product> search(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Product> lowStock(int threshold) {
        return repository.findByQuantityLessThan(threshold);
    }

    @Transactional
    public Product sell(Long id, int quantity) {
        requirePositive(quantity);
        Product product = getById(id);
        if (product.getQuantity() < quantity) {
            throw new OutOfStockException(product.getName(), product.getQuantity());
        }
        product.setQuantity(product.getQuantity() - quantity);
        return repository.save(product);
    }

    @Transactional
    public Product restock(Long id, int quantity) {
        requirePositive(quantity);
        Product product = getById(id);
        product.setQuantity(product.getQuantity() + quantity);
        return repository.save(product);
    }

    private void requirePositive(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
}
