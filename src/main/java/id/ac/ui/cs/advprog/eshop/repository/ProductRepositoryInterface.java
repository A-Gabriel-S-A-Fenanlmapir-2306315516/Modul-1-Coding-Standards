package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.Iterator;

public interface ProductRepositoryInterface {
    Product create(Product product);

    Iterator<Product> findAll();

    Product findId(String productId);

    Product editProduct(Product product);

    void deleteProduct(String productId);
}
