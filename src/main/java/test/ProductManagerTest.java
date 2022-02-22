package test;

import org.junit.jupiter.api.Test;
import productmanager.Product;
import productmanager.ProductAttribute;
import productmanager.ProductManager;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

class ProductManagerTest {

private Product product = new Product();

public void testAll(){
    create();
    createAll();
    read();
    readAll();
    update();
    testUpdate();
    remove();
    removeAll();
    setUpdateInterval();
    run();
    reparse();
    print();
    getAllProducts();
    printAllProducts();
}

    @Test
    void create() {
    ProductManager pM1 = new ProductManager();
    assertTrue(pM1.create(product));
    }

    @Test
    void createAll() {
    ProductManager pM2 = new ProductManager();
    assertFalse(pM2.createAll(new ArrayList<Product>()));

    ArrayList<Product> List = new ArrayList<>();
    List.add(product);
    assertTrue(pM2.createAll(List));
    }

    @Test
    void read() {
    ProductManager pM3 = new ProductManager();
    assertNull(pM3.read(""));

    product.set(ProductAttribute.UUID, "25");
    assertEquals(product.get(ProductAttribute.UUID), "25");
    }

    @Test
    void readAll() {
    }

    @Test
    void update() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void remove() {
    }

    @Test
    void removeAll() {
    }

    @Test
    void setUpdateInterval() {
    }

    @Test
    void run() {
    }

    @Test
    void reparse() {
    }

    @Test
    void print() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void printAllProducts() {
    }
}