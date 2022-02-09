package productmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JSONReader {

    private final String filepath;
    private final int amountOfProperties = 11;

    public JSONReader(String filepath){
        this.filepath = filepath;
    }


    public ArrayList<Product> read(){

        ArrayList<Product> output = new ArrayList<>();
        boolean newProduct = true;
        boolean containsArray = false;
        boolean containsData = false;

        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            ArrayList<String> array = new ArrayList<>();
            String line = "";
            String arrayLine = "";

            br.readLine(); //Skipping the first line as to not break the array detection.

            while((line = br.readLine()) != null){
                Product currentProduct = new Product();

                newProduct = line.contains("}");
                containsArray = line.contains("[");
                containsData = line.contains("\"");

                if(!newProduct || !containsArray && containsData) {
                    int valueStart = line.indexOf(":");
                    int valueEnd = line.lastIndexOf("\"");
                    int propertyStart = line.indexOf("\"");

                    String propertyValue = line.substring(valueStart + 3, valueEnd);
                    String propertyName = line.substring(propertyStart, valueStart - 1);

                    for(ProductAttribute pA : ProductAttribute.values()){
                        if(pA.alias.equalsIgnoreCase(propertyName)){
                            currentProduct.set(pA, propertyValue);
                            break;
                        }
                    }

                }else if(containsArray){

                    while((arrayLine = br.readLine()) != null && arrayLine.contains("\"")){
                        int entryStart = arrayLine.indexOf("\"");
                        int entryEnd = arrayLine.lastIndexOf("\"");

                        array.add(arrayLine.substring(entryStart,entryEnd));
                    }

                    String[] actualArray = new String[array.size()];

                    for(int i = 0; i < array.size(); i++){
                        actualArray[i] = array.get(i);
                    }

                    array.clear();

                    currentProduct.set(ProductAttribute.IN_STOCK, actualArray);


                }else if(newProduct){
                    output.add(currentProduct);
                    currentProduct = new Product();
                }

            }



            br.close();
        }catch (IOException e){
            e.printStackTrace();

        }

        return output;
    }

}
