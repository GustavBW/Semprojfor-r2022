package test;

import org.junit.jupiter.api.Test;
import productmanager.ProductAttribute;

import static org.junit.jupiter.api.Assertions.*;

class ProductAttributeTest {

    @Test
    void values() {
        //First I make a list of all the values we want ProductAttribute to have
        String[] sArr = {"UUID", "ID", "AVERAGE_USER_REVIEW", "IN_STOCK", "EAN", "PRICE", "PUBLISHED_DATE", "EXPIRATION_DATE", "CATEGORY", "NAME", "DESCRIPTION", "WEIGHT", "SIZE", "CLOCKSPEED"};

        //Then I make a list of all the values ProductAttribute actually has and turn them into strings
        ProductAttribute[] pArr = ProductAttribute.values();
        String[] sArr2 = new String[sArr.length];

        for (int i = 0; i < pArr.length; i++) {
            sArr2[i] = pArr[i].toString();
        }

        //Last I check if the two string arrays are equal
        assertArrayEquals(sArr, sArr2);
    }

    @Test
    void valueOf() {
        //First I make a list of all the values we want ProductAttribute to turn into enum-values of the same name
        String[] sArr = {"UUID", "ID", "AVERAGE_USER_REVIEW", "IN_STOCK", "EAN", "PRICE", "PUBLISHED_DATE", "EXPIRATION_DATE", "CATEGORY", "NAME", "DESCRIPTION", "WEIGHT", "SIZE", "CLOCKSPEED"};

        //Then I make a list of all the enum-values ProductAttribute has
        ProductAttribute[] pArr = ProductAttribute.values();

        //Last I check each value to see if .valueOf() turns it into the correct enum-value
        for (int i = 0; i < pArr.length; i++) {
            assertEquals(pArr[i], ProductAttribute.valueOf(sArr[i]));
        }
    }
}