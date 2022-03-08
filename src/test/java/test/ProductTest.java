package test;

import org.junit.jupiter.api.Test;
import productmanager.JSONReader;
import productmanager.Product;
import productmanager.ProductAttribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private static JSONReader reader = new JSONReader("resources/products.json");

    @Test
    void get() {
        ArrayList<Product> productTestList = new ArrayList<>(); //initializing test arraylist
        int i = 0; //int for counting unavailable attributes
        Product p1 = new Product(); //initializing new product
        productTestList.add(0, p1); //adding said product to test list
        try { //trying read function
            productTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Product p : productTestList) { //running through test list
            for (ProductAttribute pA : ProductAttribute.values()) { //asserting pAs
                if (p.get(pA) == "unavailable") { //if pA is "unavailable" add i+1
                    i++;
                }
                assertNotNull(pA);
                assertFalse(i>2);
            }
        }
        assertFalse(productTestList.isEmpty());
        assertNotNull(p1);

    }

    @Test
    void getAsNumeric(){
        Product p1 = new Product();
        for (ProductAttribute pA : ProductAttribute.values()); {
        assertEquals(0.00,null);
        assertTrue(pA>=0);
        }

        /*
        double pA1 = 69.99;
        String pA2 = "Ultimate Performance";
        assertEquals(69.99,pA1);
        assertEquals(0.00, pA2);*/
    }

    @Test
    void getLocations() {
        ArrayList<Product> availableAt;
        try {
            availableAt = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("Odense","Odense");
    }

    @Test
    void getAttributeMap() {
        ArrayList<Product> attributeTestList = new ArrayList<>();
        try {
            attributeTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //for (Product product:attributeTestList) {

        //}
    }

    @Test
    void set() { //na
    }

    @Test
    void setLocations() {
        ArrayList<Product> locationList = new ArrayList<>();
        try {
            locationList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testToString() {
        ArrayList<Product> productTitle = new ArrayList<>();
        try {
            productTitle = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("Product: Monitor","Monitor");
    }

    /*@Test
    void print() {
    }
     */
}
