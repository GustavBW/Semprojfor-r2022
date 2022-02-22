package test;

import org.junit.jupiter.api.Test;
import productmanager.JSONReader;
import productmanager.Product;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private static JSONReader reader = new JSONReader("resources/products.json");

    @Test
    void get() {
        ArrayList<Product> productList;
        try {
            productList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("unavailable", null);
        try {
            productList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("2054647099864","2054647099864");
    }

    @Test
    void getLocations() {
    }

    @Test
    void getAttributeMap() {
    }

    @Test
    void set() {
    }

    @Test
    void setLocations() {
    }

    @Test
    void testToString() {
    }

    @Test
    void print() {
    }
}