package productmanager;

import java.util.ArrayList;

public class ProductManager implements IProductManager{

    public static void main(String[] args) {
	// write your code here
    }

    @Override
    public Product create(Product p) {
        return null;
    }

    @Override
    public Product read(int productId) {
        return null;
    }

    @Override
    public Product[] readAll(int[] productIds) {
        return new Product[0];
    }

    @Override
    public boolean update(int productId, ProductAttribute a, String s) {
        return false;
    }

    @Override
    public boolean update(int productId, Product p) {
        return false;
    }

    @Override
    public boolean remove(int productId) {
        return false;
    }

    @Override
    public void updateInterval(int time) {

    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return null;
    }
}
