package test;

import org.junit.jupiter.api.Test;
import productmanager.BaseProduct;
import productmanager.ProductJSONReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BaseProductJSONReaderTest {
    private static ProductJSONReader reader = new ProductJSONReader("resources/products.json");

    @Test
    void read() {

        ArrayList<BaseProduct> baseProducts = null;
        try {
            assertFalse(reader.read().isEmpty());          //Is there any Products in the array?
            assertNotNull(baseProducts = reader.read());       //Does the array exist?
            assertTrue(reader.read().size() > 1);  //Is there more than 1 product object?

            assertThrows(FileNotFoundException.class, () -> reader.read("")); //Does it fail when there's an invalid filepath given?

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Test
    void testRead() {
    }

    @Test
    void write() {
        try {
            assertTrue(reader.write(reader.read()));                                //Does it succeed?
            assertThrows(NullPointerException.class, () -> reader.write(null));  //
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void testWrite() {
    }
    
    @Test
    void validate() {
        assertTrue(reader.validate("resources/products.json"));
        assertFalse(reader.validate("resources/prodadsucts.json"));
        assertFalse(reader.validate("resources/empty.json"));
        assertTrue(reader.validate("resources/cheese.json"));
        assertFalse(reader.validate("resources/config.txt"));
    }
}