package dk.sdu.se_f22.productmodule.management.domain_persistance;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;

public interface IProductManager {
    
    boolean create(BaseProduct p);
    boolean create(Product p);
    boolean createAll(ArrayList<Product> p);
    BaseProduct readBaseProduct(String productId);
    Product readProduct(String productId);
    BaseProduct[] readBaseProducts(String[] productIds);
    Product[] readProducts(String[] productIds);
    boolean update(String productId, ProductAttribute a, String s);
    boolean update(String productId, Product p);
    boolean remove(String productId);
    boolean removeAll(String[] productIds);
    void setUpdateInterval(int time);
    void reparse();
    ArrayList<BaseProduct> readAllProducts();
}
