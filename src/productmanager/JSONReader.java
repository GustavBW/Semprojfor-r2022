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
        boolean containsProductStart = true;
        int currentLine = 1;

        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            ArrayList<String> array = new ArrayList<>();
            String line = "";
            String arrayLine = "";

            br.readLine(); //Skipping the first line as to not break the array detection.
            currentLine++;

            while((line = br.readLine()) != null){
                currentLine++;
                Product currentProduct = new Product();

                newProduct = line.contains("}");
                containsArray = line.contains("[");
                containsData = line.contains("\"");
                containsProductStart = line.contains("{");

                if(!(newProduct || containsArray || containsProductStart) && containsData) {
                    int valueStart = line.indexOf(":");
                    int valueEnd = findLastOccurence(",",line);
                    int propertyStart = line.indexOf("\"");

                    String propertyValue = line.substring(valueStart + 3, valueEnd - 1);
                    String propertyName = line.substring(propertyStart, valueStart - 1);

                    for(ProductAttribute pA : ProductAttribute.values()){
                        if(pA.alias.equalsIgnoreCase(propertyName)){
                            currentProduct.set(pA, propertyValue);
                            break;
                        }
                    }

                }else if(containsArray){

                    while((arrayLine = br.readLine()) != null && arrayLine.contains("\"")){
                        currentLine++;
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

        }catch (StringIndexOutOfBoundsException e){
            System.out.println("String index out of bounds at line " + currentLine);
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();

        }

        return output;
    }

    private int findLastOccurence(String whatToFind, String line){
        int index = line.length();
        boolean foundIt = false;
        System.out.println("Searching: " + line + " of length " + index +" for; \"" + whatToFind + "\"");

        for(int i = index; i > 0; i--){
            if(line.substring(i - 1,i).contains(whatToFind)){
                foundIt = true;
                break;
            }
            index--;
        }
        if(foundIt) {
            System.out.println("Occurence found at index " + index);
        }else{
            System.out.println("No occourence found returning line length");
            index = line.length() +1 ;
        }

        return index;
    }

}
