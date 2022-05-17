package dk.sdu.se_f22.productmodule.management.domain_persistance;

import dk.sdu.se_f22.sharedlibrary.models.Product;

import java.util.ArrayList;

public interface IProductManager {

    boolean create(BaseProduct p);
    boolean create(Product p);
    boolean createAll(ArrayList<BaseProduct> p);
    BaseProduct readProduct(String productId);
    Product readProductNew(String productId);
    Product read(String productId);
    BaseProduct[] readProducts(String[] productIds);
    Product[] readProductsNew(String[] productIds);
    boolean update(String productId, ProductAttribute a, String s);
    boolean update(String productId, Product p);
    boolean remove(String productId);
    void setUpdateInterval(int time);
    void reparse();

    ArrayList<BaseProduct> readAllProducts();

}
