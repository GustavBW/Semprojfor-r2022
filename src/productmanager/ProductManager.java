package productmanager;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ProductManager implements IProductManager, Runnable{

    private JSONReader jsonReader;

    private ArrayList<Product> productArray;
    private ArrayList<Product> updatedProductArray;
    private boolean backgroundThreadIsRunning = false;
    private Thread backgroundThread;
    private long lastCall;

    private int updateInterval;    //Minutes
    private String config = "resources/config.txt";

    public ProductManager(){

        //When you create a new object of this class, the backgroundThread is started automatically.

        jsonReader = new JSONReader("resources/products.json");
        productArray = new ArrayList<>();
        updatedProductArray = null;

        backgroundThread = new Thread(this);
        backgroundThread.start();

        lastCall = System.currentTimeMillis();
        updateInterval = readConfig();
    }

    public static void main(String[] args) {

        //This is only used for testing right now.

        ProductManager manager = new ProductManager();
        manager.productArray = manager.jsonReader.read();

        File file = new File("resources/productsWriteTest.json");
        System.out.println("File is at " + file.getAbsolutePath());

        //manager.printAllProducts();
        // write your code here
    }

    @Override
    public boolean create(Product p) {

        //Adds new product to the productArray.
        //Returns whether this was possible or not.
        checkForUpdates();
        boolean success = productArray.add(p);
        updateSource();

        return success;
    }

    @Override
    public boolean createAll(ArrayList<Product> pList){
        checkForUpdates();

        boolean success = productArray.addAll(pList);

        updateSource();
        return success;
    }

    @Override
    public Product read(String productId) {

        //This function returns a single product based on the UUID

        checkForUpdates();

        Product toReturn = null;

        for(Product p : productArray){
            if(p.get(ProductAttribute.ID).equalsIgnoreCase(productId)){
                toReturn = p;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public Product[] readAll(String[] productIds) {

        //This function returns an array of products based on an array of UUID's
        //The size of the return array should equal the size of the input ID array

        checkForUpdates();

        Product[] returnArray = new Product[productIds.length];

        for(int i = 0; i < productIds.length; i++){
            for(Product p : productArray){

                if(p.get(ProductAttribute.ID).equalsIgnoreCase(productIds[i])){
                    returnArray[i] = p;
                    break;
                }
            }
        }

        updateSource();

        return returnArray;
    }

    @Override
    public boolean update(String productId, ProductAttribute a, String s) {
        checkForUpdates();

        boolean succes = false;

        for(Product pT : productArray){
            if(Objects.equals(pT.get(ProductAttribute.ID), productId)){
                succes = pT.set(a,s);
                break;
            }
        }

        updateSource();

        return succes;
    }

    @Override
    public boolean update(String productId, Product p) {
        checkForUpdates();

        boolean success = false;

        for(Product pT : productArray){
            if(pT.get(ProductAttribute.ID).equalsIgnoreCase(productId)){
                pT = p;
                success = Objects.equals(pT, p);
                break;
            }
        }

        updateSource();

        return success;
    }

    @Override
    public boolean remove(String productId) {
        checkForUpdates();
        boolean toReturn = false;


        for(Product p : productArray){
            if (p.get(ProductAttribute.ID).equalsIgnoreCase(productId)){
                toReturn = productArray.remove(p);
                break;
            }
        }


        return toReturn;
    }

    @Override
    public void setUpdateInterval(int time) {
        checkForUpdates();

        updateInterval = time;
    }

    private boolean backgroundUpdate(){

        //This is a background function to be called once every index update interval.
        //For direct control of index reparses, use reparse()

        if(!backgroundThreadIsRunning) {
            backgroundThreadIsRunning = true;
        }

        //Right here is where the XXXX.updateIndex() call to the module from Group 2.2 goes

        return backgroundThread.isAlive();
    }

    @Override
    public void run(){

        //This function is only used by the backgroundThread.
        //It busy-waits (Thread.sleep would work as well) for updateInterval (minutes)
        //Then reads the json file again and prepares a new ArrayList<Product> for use.

        while(System.currentTimeMillis() < lastCall + (updateInterval * 60000L)){
            System.out.println("");
        }

        lastCall = System.currentTimeMillis();
        reparse();
        backgroundUpdate();
    }

    @Override
    public void reparse(){
        updatedProductArray = jsonReader.read();
    }

    private boolean checkForUpdates(){
        boolean output = false;
        if(updatedProductArray != null){

            //This function is called from every CRUD operation and checks if a newer version of the productArray is available
            //This newer version may come from an external call to reparse() or from the backgroundThread from run()
            //Alternatively, use clone(). It might be more performant.

            productArray.clear();
            productArray.addAll(updatedProductArray);
            updatedProductArray = null;
            output = true;
        }
        return output;
    }

    private void updateSource(){

        //This call rewrites the source file with the current productArray
        //This !.isEmpty and != null redundancy might not be necessary, but it's here just in case.

        if(!productArray.isEmpty() || productArray != null) {
            jsonReader.write(productArray);
        }else{
            System.err.println("ERROR function call ignored: Tried to write an empty or null array to source file.");
            System.err.println("\t  Error occurred at ProductManager.updateSource() line " + (Thread.currentThread().getStackTrace()[1].getLineNumber() - 3));
        }
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
