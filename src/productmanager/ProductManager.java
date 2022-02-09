package productmanager;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ProductManager implements IProductManager, Runnable{

    private JSONParser jsonparser;

    private ArrayList<Product> productArray;
    private ArrayList<Product> updatedProductArray;

    private int updateInterval;    //Minutes
    private String config = "resources/config.txt";

    public ProductManager(){
        productArray = new ArrayList<>();
        updatedProductArray = null;


        updateInterval = readConfig();
    }

    public static void main(String[] args) {
        JSONReader reader = new JSONReader("resources/products.json");
        ProductManager manager = new ProductManager();
        manager.productArray = reader.read();

        File file = new File("productsWriteTest.json");
        System.out.println("File is at " + file.getAbsolutePath());
        reader.write(manager.productArray, file.getAbsolutePath());

        //manager.printAllProducts();
        // write your code here
    }

    @Override
    public boolean create(Product p) {
        checkForUpdates();

        return productArray.add(p);
    }

    @Override
    public boolean createAll(ArrayList<Product> pList){
        checkForUpdates();

        boolean success = productArray.addAll(pList);

        updateSource();
        return success;
    }

    @Override
    public Product read(int productId) {
        checkForUpdates();

        Product toReturn = null;

        /*

        for(Product p : productArray){
            if(p.get(ProductAttribute.ID) == productId){
                toReturn = p;
                break;
            }
        }

         */
        updateSource();


        return toReturn;
    }

    @Override
    public Product[] readAll(int[] productIds) {
        checkForUpdates();

        Product[] returnArray = new Product[productIds.length];

        /*
        for(int i = 0; i < productIds.length; i++){
            for(Product p : productArray){

                if(p.get(ProductAttribute.ID) == productIds[i]){
                    returnArray[i] = p;
                    break;
                }
            }
        }
        */
        updateSource();

        return returnArray;
    }

    @Override
    public boolean update(int productId, ProductAttribute a, String s) {
        checkForUpdates();

        boolean succes = false;
        /*
        for(Product pT : productArray){
            if(pT.get(ProductAttribute.ID) == productId){
                succes = pT.set(a,s);
                break;
            }
        }
         */
        updateSource();

        return succes;
    }

    @Override
    public boolean update(int productId, Product p) {
        checkForUpdates();

        boolean success = false;
        /*
        for(Product pT : productArray){
            if(pT.get(ProductAttribute.ID) == productId){
                pT = p;
                success = Objects.equals(pT, p);
                break;
            }
        }
         */
        updateSource();

        return success;
    }

    @Override
    public boolean remove(int productId) {
        checkForUpdates();
        boolean toReturn = false;

        /*
        for(Product p : productArray){
            if (p.get(ProductAttribute.ID) == productId){
                toReturn = productArray.remove(p);
                break;
            }
        }


         */
        return toReturn;
    }

    @Override
    public void setUpdateInterval(int time) {
        checkForUpdates();

        updateInterval = time;
    }

    private boolean updateIndex(){

        //This is a background function to be called once every index update interval.
        //For direct control of index reparses, use reparse()

        Thread newThread = new Thread(this);

        newThread.start();

        return newThread.isAlive();
    }

    @Override
    public void run(){
        reparse();
    }

    @Override
    public void reparse(){
        updatedProductArray = jsonparser.read();
    }

    private void checkForUpdates(){
        if(updatedProductArray != null){

            //Alternatively, use clone(). It might be more performant.

            productArray.clear();
            productArray.addAll(updatedProductArray);
            updatedProductArray = null;
        }
    }

    private void updateSource(){
        jsonparser.write(productArray);
    }

    private int readConfig(){
        int toReturn = 0;

        try{
            BufferedReader br = new BufferedReader(new FileReader(config));
            toReturn = Integer.parseInt(br.readLine());
            br.close();

        }catch (IOException e){
            e.printStackTrace();
            toReturn = 5;
        }
        return toReturn;
    }

    public void print(){
        for(Product p : productArray){
            System.out.println(p);
        }
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        checkForUpdates();
        return productArray;
    }

    public void printAllProducts(){
        for(Product p : productArray){
            p.print();
        }
    }
}
