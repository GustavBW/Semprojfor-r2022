package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import productmanager.Product;
import productmanager.ProductAttribute;
import productmanager.ProductManager;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class ProductManagerTest {

private Product product = new Product();
private ProductManager productManager = new ProductManager();

    @BeforeEach
    void setup(){
        System.out.println("============ ProductManager TEST SETUP ============");
    }

    @Test
    void create() {

    //creating product
    assertTrue(productManager.create(product));
    System.out.println("========== create() TEST DONE ============");
    }

    @Test
    void createAll() {

    //trying to createAll() using non-initialized, empty List
    assertFalse(productManager.createAll(new ArrayList<Product>()));

    ArrayList<Product> List = new ArrayList<>();

    assertFalse(productManager.createAll(List));

    //trying to createAll() after adding products to the List
    List.add(product);
    assertTrue(productManager.createAll(List));

    System.out.println("========== createAll() TEST DONE ============");
    }

    @Test
    void read() {

    //trying to read a productId (UUID) using an empty String
    assertNull(productManager.read(""));

    //setting product UUID
    product.set(ProductAttribute.UUID, "25");

    //using read on a set product UUID
    assertEquals(product.get(ProductAttribute.UUID), "25");
        System.out.println("========== read() TEST DONE ============");
    }

    @Test
    void readAll() {
        //Initializing String array of possible product UUIDs
        String[] ids = {"35", "42", "1", "364"};

        //assertArrayEquals(something, something);
    }

    @Test
    void update() {
        //testing with nothing in the productArray
        assertFalse(productManager.update("30", ProductAttribute.UUID, "50"));

        //adding product to productArray
        productManager.create(product);
        productManager.productArray.add(product);

        //setting product UUID
        product.set(ProductAttribute.UUID, "30");

        //testing if desired update occurred
        assertTrue(productManager.update("30", ProductAttribute.UUID, "50"));
        System.out.println("========== update() TEST DONE ============");
    }


    @Test
    void remove() {

        //trying to remove product not in productArray
        assertFalse(productManager.remove("30"));

        //adding product to productArray
        productManager.create(product);
        productManager.productArray.add(product);

        //setting product UUID
        product.set(ProductAttribute.UUID, "30");

        //trying to remove product in productArray, using faulty UUID
        assertFalse(productManager.remove("45"));

        //trying to remove product in productArray, using actual UUID
        assertTrue(productManager.remove("30"));
        System.out.println("========== remove() TEST DONE ============");
    }

    @Test
    void removeAll() {
        //Initializing String array of possible product UUIDs
        String[] ids = {"35", "42", "1", "364"};

        //Initializing second String array of UUIDs, for test with 1 not matching
        String[] ids1 = {"35", "42", "1", "363"};

        //creating additional product objects
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        //trying to removeAll products in empty productArray
        assertFalse(productManager.removeAll(ids));

        //adding products to productArray
        productManager.create(product);
        productManager.create(product1);
        productManager.create(product2);
        productManager.create(product3);

        productManager.productArray.add(product);
        productManager.productArray.add(product1);
        productManager.productArray.add(product2);
        productManager.productArray.add(product3);

        //setting product UUIDs
        product.set(ProductAttribute.UUID, "35");
        product1.set(ProductAttribute.UUID, "42");
        product2.set(ProductAttribute.UUID, "1");
        product3.set(ProductAttribute.UUID, "364");

        //testing removeAll with an array containing 1 non-matching UUID
        assertFalse(productManager.removeAll(ids1));

        //testing removeAll with an array of matching UUIDs
        assertTrue(productManager.removeAll(ids));
        System.out.println("========== removeAll() TEST DONE ============");
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

    @AfterEach
    void teardown(){
        System.out.println("============ ProductManager TEST TEARDOWN ============");
    }
}