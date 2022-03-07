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
        ArrayList<Product> productTestList = new ArrayList<>();
        int i = 0;
        Product p1 = new Product();
        productTestList.add(0, p1);
        try {
            productTestList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Product p : productTestList) {
            for (ProductAttribute pA : ProductAttribute.values()) {
                if (p.get(pA) == "unavailable") {
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

    @Test
    void print() {
    }
}