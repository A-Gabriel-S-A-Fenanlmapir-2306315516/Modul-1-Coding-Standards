package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();


    public Product create (Product product){
        if (product.getProductId() == null){
            product.setProductId(UUID.randomUUID().toString());
        }
        productData.add(product);

        return product;
    }

    public Iterator<Product> findAll(){
        return  productData.iterator();
    }

    public Product findId(String productId){
        for(Product product : productData){
            if(product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product editProduct(Product product){
        int index = 0;
        while (findAll().hasNext()){
            index++;
            Product productTemp = productData.get(index);
            if(productTemp.getProductId().equals(product.getProductId())){
                productData.set(index,productTemp);
                return product;
            }
        }
        return null;
    }

    public void deteleProduct(String productId){
        productData.removeIf(product -> product.getProductId().equals(productId));
    }
}
