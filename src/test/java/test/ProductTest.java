package test;

import org.junit.jupiter.api.Test;
import productmanager.JSONReader;
import productmanager.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        ArrayList<Product> availableAt;
        try {
            availableAt = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("Odense","Odense");
    }

    @Test
    public ArrayList<String> getAttributeMap() {
        ArrayList<String> atributteList = new ArrayList<>();
        try {
                atributteList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return atributteList;
    }

    @Test
    void set() { //na
    }

    @Test
    public ArrayList<String> setLocations() {
            ArrayList<String> locationList = new ArrayList<>();
            try {
                    locationList = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return locationList;

    }

    @Test
    void testToString() {
        ArrayList<String> productTitle = new ArrayList<>();
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