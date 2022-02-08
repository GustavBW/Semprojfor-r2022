package productmanager;

import java.util.ArrayList;

public interface IProductManager {

    boolean create(Product p);
    boolean createAll(ArrayList<Product> p);
    Product read(int productId);
    Product[] readAll(int[] productIds);
    boolean update(int productId, ProductAttribute a, String s);
    boolean update(int productId, Product p);
    boolean remove(int productId);
    void setUpdateInterval(int time);
    void reparse();

    ArrayList<Product> getAllProducts();

}
