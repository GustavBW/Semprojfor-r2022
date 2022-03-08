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
            assertNotNull(products = reader.read());       //Does the array exist?
            assertTrue(reader.read().size() > 1);  //Is there more than 1 product object?

            assertThrows(FileNotFoundException.class, () -> reader.read("")); //Does it fail when there's an invalid filepath given?

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    void testRead() {
    }

    @org.junit.jupiter.api.Test
    void write() {
        try {
            assertTrue(reader.write(reader.read()));                                //Does it succeed?
            assertThrows(NullPointerException.class, () -> reader.write(null));  //
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void testWrite() {
    }
}