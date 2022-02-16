package test;

import productmanager.JSONReader;
import productmanager.Product;
import productmanager.ProductAttribute;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JSONReaderTest {

    private static JSONReader reader = new JSONReader("resources/products.json");

    public void runAllTests(){
        read();
        write();
    }

    @org.junit.jupiter.api.Test
    void read() {

        ArrayList<Product> products = null;
        try {
            assertFalse(reader.read().isEmpty());          //Is there any Products in the array?
            assertNotNull(products = reader.read());                  //Does the array exist?
            assertTrue(reader.read().size() > 1);  //Is there more than 1 product object?

            assertThrows(FileNotFoundException.class, () -> reader.read("")); //Does it fail when there's an invalid filepath given?

        }catch (IOException e){
            e.printStackTrace();
        }

        assert products != null;
        for(Product p : products) {
            for (ProductAttribute pattr : ProductAttribute.values()) {   //Has all attributes been assigned correctly?
                assertNotNull(p.get(pattr));
            }
        }

        String currentUUIDInspected;
        for(Product p : products){
            assertNotNull(currentUUIDInspected = p.get(ProductAttribute.UUID)); //Does each product have an UUID?
            assertEquals(36, currentUUIDInspected.length());            //Does each product's UUID have 36 characters? (It must)
        }


    }

    @org.junit.jupiter.api.Test
    void testRead() {
    }

    @org.junit.jupiter.api.Test
    void write() {
        try {
            assertTrue(reader.write(reader.read()));
            assertThrows(NullPointerException.class, () -> reader.write(null));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void testWrite() {
    }
}