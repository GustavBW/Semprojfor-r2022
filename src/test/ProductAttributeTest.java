package test;

import org.junit.jupiter.api.Test;
import productmanager.ProductAttribute;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static productmanager.ProductAttribute.*;

class ProductAttributeTest {

    @Test
    void values() {
        String[] sArr = {"UUID", "ID", "AVERAGE_USER_REVIEW", "IN_STOCK", "EAN", "PRICE", "PUBLISHED_DATE", "EXPIRATION_DATE", "CATEGORY", "NAME", "DESCRIPTION", "WEIGHT", "SIZE", "CLOCKSPEED"};
        ProductAttribute[] pArr = ProductAttribute.values();
        String[] sArr2 = new String[sArr.length];

        for (int i = 0; i < pArr.length; i++) {
            sArr2[i] = pArr[i].toString();
        }

        assertArrayEquals(sArr, sArr2);
    }

    @Test
    void valueOf() {
        String[] sArr = {"UUID", "ID", "AVERAGE_USER_REVIEW", "IN_STOCK", "EAN", "PRICE", "PUBLISHED_DATE", "EXPIRATION_DATE", "CATEGORY", "NAME", "DESCRIPTION", "WEIGHT", "SIZE", "CLOCKSPEED"};
        ProductAttribute[] pArr = ProductAttribute.values();

        for (int i = 0; i < pArr.length; i++) {
            assertEquals(pArr[i], ProductAttribute.valueOf(sArr[i]));
        }
    }
}