package productmanager;

import java.io.*;
import java.util.ArrayList;

public class JSONReader {

    private final String filepath;
    private final int amountOfProperties = 11;

    public JSONReader(String filepath){
        this.filepath = filepath;
    }


    public ArrayList<Product> read(){
        return read(filepath);
    }
    public ArrayList<Product> read(String filepath2){

        ArrayList<Product> output = new ArrayList<>();
        boolean newProduct;
        boolean containsArray;
        boolean containsData;
        boolean containsProductStart;
        int currentLine = 1;

        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath2));
            Product currentProduct = new Product();
            ArrayList<String> array = new ArrayList<>();
            String line;
            String arrayLine;

            br.readLine(); //Skipping the first line as to not break the array detection.
            currentLine++;

            while((line = br.readLine()) != null){
                currentLine++;

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

    public boolean write(ArrayList<Product> list, String filepath){
        boolean success = true;
        StringBuilder builder = new StringBuilder();

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            String propertyValue = null;

            builder.append("[");

            for(Product p : list){
                builder.append("\n{");

                for(ProductAttribute pAttr : ProductAttribute.values()){

                    if(pAttr != ProductAttribute.IN_STOCK) {
                        if((propertyValue = p.get(pAttr)) != null || !propertyValue.isEmpty()) {
                            builder.append("\n").append("\t").append("\"").append(pAttr.alias).append("\":").append(" \"").append(propertyValue).append("\",");
                        }
                    }
                }

                builder = new StringBuilder(removeLastOccurence(",", builder.toString()));
                builder.append("\n},");
            }

            builder.append("\n]");

            if(builder.isEmpty()){
                success = false;

            }else{
                bw.write(builder.toString());
                success = true;
            }

            bw.flush();
            bw.close();
        }catch (IOException e){
            success = false;
            e.printStackTrace();
        }


        return success;
    }

    public boolean write(ArrayList<Product> list){
        return write(list, filepath);
    }

    private int findLastOccurence(String whatToFind, String line){
        int index = line.length();
        boolean foundIt = false;
        //System.out.println("Searching: " + line + "\t\t" +  " of length " + index +" for; \"" + whatToFind + "\"");

        for(int i = index; i > 0; i--){
            if(line.substring(i - 1,i).contains(whatToFind)){
                foundIt = true;
                break;
            }
            index--;
        }
        if(!foundIt) {
            //System.out.println("No occourence found returning line length");
            index = line.length() +1;
        }

        return index;
    }
    private String removeLastOccurence(String whatToRemove, String line){
        int index = findLastOccurence(whatToRemove, line);

        return line.substring(index - 1, index).replace(",","");
    }


}
