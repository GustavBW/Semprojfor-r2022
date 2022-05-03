package dk.sdu.se_f22.productmodule.management.domain_persistance;

import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {
    
    private BaseProduct baseProduct;
    private ProductManager productManager;
    
    @BeforeAll
    static void initialize(){
        try {
            FileWriter fw = new FileWriter("src/test/resources/dk/sdu/se_f22/productmodule/management/domain_persistance/cheese.json");
            fw.write("");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("============ ProductManager TEST SETUP ============");
    }
    
    @BeforeEach
    void setup(){
        baseProduct = new BaseProduct();
        for(ProductAttribute pa : ProductAttribute.values()){
            baseProduct.set(pa, "test");
        }
        productManager = new ProductManager("src/test/resources/dk/sdu/se_f22/productmodule/management/domain_persistance/cheese.json");
        System.out.println("============ INITIALIZING ============");
    }
    
    @Order(1)
    @Test
    void create() {
        
        //creating baseProduct
        assertTrue(productManager.create(baseProduct));
        System.out.println("========== create() TEST DONE ============");
    }
    
    @Order(2)
    @Test
    void createAll() {
        
        //trying to createAll() using non-initialized, empty List
        assertFalse(productManager.createAll(new ArrayList<>()));
        
        ArrayList<BaseProduct> List = new ArrayList<>();
        
        assertFalse(productManager.createAll(List));
        
        //trying to createAll() after adding products to the List
        List.add(baseProduct);
        assertTrue(productManager.createAll(List));
        
        System.out.println("========== createAll() TEST DONE ============");
    }
    
    @Order(3)
    @Test
    void read() {
        
        //trying to read a productId (UUID) using an empty String
        assertNull(productManager.readProduct(""));
        
        //setting baseProduct UUID
        baseProduct.set(ProductAttribute.UUID, "25");
        
        //using read on a set baseProduct UUID
        assertEquals(baseProduct.get(ProductAttribute.UUID), "25");
        System.out.println("========== read() TEST DONE ============");
    }
    
    @Order(4)
    @Test
    void update() {
        //testing with nothing in the baseProductArray
        assertFalse(productManager.update("46", ProductAttribute.UUID, "50"));
        
        //adding baseProduct to baseProductArray
        productManager.create(baseProduct);
        productManager.baseProductArray.add(baseProduct);
        
        //setting baseProduct UUID
        baseProduct.set(ProductAttribute.UUID, "30");
        
        //testing if desired update occurred
        assertTrue(productManager.update("30", ProductAttribute.UUID, "50"));
        System.out.println("========== update() TEST DONE ============");
    }
    
    @Order(5)
    @Test
    void remove() {
        
        //trying to remove baseProduct not in baseProductArray
        assertFalse(productManager.remove("30"));
        
        //adding baseProduct to baseProductArray
        productManager.create(baseProduct);
        productManager.baseProductArray.add(baseProduct);
        
        //setting baseProduct UUID
        baseProduct.set(ProductAttribute.UUID, "30");
        
        //trying to remove baseProduct in baseProductArray, using faulty UUID
        assertFalse(productManager.remove("45"));
        
        //trying to remove baseProduct in baseProductArray, using actual UUID
        assertTrue(productManager.remove("30"));
        System.out.println("========== remove() TEST DONE ============");
    }
    
    @Order(6)
    @Test
    void removeAll() {
        //Initializing String array of possible baseProduct UUIDs
        String[] ids = {"35", "42", "1", "364"};
        
        //Initializing second String array of UUIDs, for test with 1 not matching
        String[] ids1 = {"35", "42", "1", "363"};
        
        //creating additional baseProduct objects
        BaseProduct baseProduct1 = new BaseProduct();
        BaseProduct baseProduct2 = new BaseProduct();
        BaseProduct baseProduct3 = new BaseProduct();
        BaseProduct baseProduct4 = new BaseProduct();
        
        for (ProductAttribute pa : ProductAttribute.values()) {
            baseProduct1.set(pa, "test");
            baseProduct2.set(pa, "test");
            baseProduct3.set(pa, "test");
            baseProduct4.set(pa, "test");
        }
        
        //trying to removeAll products in empty baseProductArray
        assertFalse(productManager.removeAll(ids));
        
        //adding products to baseProductArray
        productManager.create(baseProduct1);
        productManager.create(baseProduct2);
        productManager.create(baseProduct3);
        productManager.create(baseProduct4);
        
        productManager.baseProductArray.add(baseProduct1);
        productManager.baseProductArray.add(baseProduct2);
        productManager.baseProductArray.add(baseProduct3);
        productManager.baseProductArray.add(baseProduct4);
        
        //setting baseProduct UUIDs
        baseProduct1.set(ProductAttribute.UUID, "35");
        baseProduct2.set(ProductAttribute.UUID, "42");
        baseProduct3.set(ProductAttribute.UUID, "1");
        baseProduct4.set(ProductAttribute.UUID, "364");
        
        //testing removeAll with an array containing 1 non-matching UUID
        assertFalse(productManager.removeAll(ids1));
        
        //testing removeAll with an array of matching UUIDs
        assertTrue(productManager.removeAll(ids));
        System.out.println("========== removeAll() TEST DONE ============");
    }

    @Order(7)
    @Test
    void readProducts() {
        String[] ids = {"35", "42", "1", "364"};
        BaseProduct[] baseProducts = productManager.readProducts(ids);
        BaseProduct[] baseProductTest = new BaseProduct[4];

        assertNotNull(baseProducts);
        assertEquals(4, baseProducts.length);
        //assertArrayEquals(baseProductTest, baseProducts);
        assertNotNull(Arrays.asList(baseProducts));
    }

    @Order(8)
    @Test
    void setUpdateInterval() {
        //Setting new UpdateInterval
        productManager.setUpdateInterval(9);

        //Asserting the correct interval change
        assertEquals(productManager.getUpdateInterval(), 9);

        //Asserting a false interval change
        assertNotEquals(10, productManager.getUpdateInterval());
        System.out.println("========== setUpdateInterval() TEST DONE ============");

    }

    @Order(9)
    @Test
    void readAllProducts() {
        //Ensuring that the returned baseProduct arraylist isn't null
        assertNotNull(productManager.readAllProducts());

        //Ensuring that the returned arraylist is indeed an instance of the appropriate class
        assertInstanceOf(ArrayList.class, productManager.readAllProducts());
        System.out.println("========== readAllProducts() TEST DONE ============");
    }

    @Test
    @Order(10)
    void readProduct(){
        //reading a baseProduct not found in the baseProductArray
        assertNull(productManager.readProduct("1"));

        //creating baseProduct, setting the UUID and add it to the baseProductArray
        BaseProduct baseProduct1 = new BaseProduct();
        baseProduct1.set(ProductAttribute.UUID, "1");
        productManager.baseProductArray.add(baseProduct1);

        //reading baseProduct now added to the baseProductArray
        assertNotNull(productManager.readProduct("1"));
        System.out.println("========== readProduct() TEST DONE ============");
    }
    
    @AfterEach
    void teardown(){
        System.out.println("============ TEARDOWN ============");
    }
    
    @AfterAll
    static void ending(){
        System.out.println("============ TEST COMPLETED SUCCESSFULLY ============");
    }
}