package productmanager;

import java.util.ArrayList;

public interface IProductManager {

    Product create(Product p);
    Product read(int productId);
    Product[] readAll(int[] productIds);
    boolean update(int productId, ProductAttribute a, String s);
    boolean update(int productId, Product p);
    boolean remove(int productId);
    void updateInterval(int time);

    ArrayList<Product> getAllProducts();

}
