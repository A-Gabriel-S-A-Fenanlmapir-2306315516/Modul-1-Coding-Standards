package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testCreateWithNullId() {
        Product product = new Product();
        product.setProductName("Sampo Tanpa ID");
        product.setProductQuantity(50);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindIdFound() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Sampo Kuda");
        productRepository.create(product);

        Product foundProduct = productRepository.findId("123");
        assertNotNull(foundProduct);
        assertEquals("Sampo Kuda", foundProduct.getProductName());
    }

    @Test
    void testFindIdNotFound() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        Product foundProduct = productRepository.findId("999");
        assertNull(foundProduct);
    }

    @Test
    void testEditProductFound() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Sampo Lama");
        productRepository.create(product);

        Product updatedData = new Product();
        updatedData.setProductId("123");
        updatedData.setProductName("Sampo Baru");
        updatedData.setProductQuantity(10);

        Product result = productRepository.editProduct(updatedData);

        assertNotNull(result);
        assertEquals("Sampo Baru", result.getProductName());
        assertEquals(10, result.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        Product updatedData = new Product();
        updatedData.setProductId("999");
        updatedData.setProductName("Sampo Halusinasi");

        Product result = productRepository.editProduct(updatedData);

        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("123");
        productRepository.create(product);

        productRepository.deteleProduct("123");

        Product foundProduct = productRepository.findId("123");
        assertNull(foundProduct);
    }@Test
    void testEditProductIfRepoIsNotEmptyButIdNotFound() {

        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Sampo Asli");
        productRepository.create(product);


        Product updatedData = new Product();
        updatedData.setProductId("999");
        updatedData.setProductName("Sampo Palsu");

        Product result = productRepository.editProduct(updatedData);


        assertNull(result);
    }
}