package productmanager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JSONReader {

    private final String filepath;
    private final int amountOfProperties = 11;
    private int currentLineNumber = 1;

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
        currentLineNumber = 1;
        int amountOfProducts = 1;

        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath2));
            Product currentProduct = new Product();
            ArrayList<String> array = new ArrayList<>();
            String line;

            br.readLine(); //Skipping the first line as to not break the array detection.
            currentLineNumber++;

            while((line = br.readLine()) != null){
                currentLineNumber++;

                newProduct = line.contains("}");
                containsArray = line.contains("[");
                containsData = line.contains("\"");
                containsProductStart = line.contains("{");



                if(containsArray){

                    currentProduct.setLocations(ProductAttribute.IN_STOCK, calculateInStockArray(br, currentProduct, array));

                }else if(newProduct){

                    currentProduct.set(ProductAttribute.ID, String.valueOf(amountOfProducts));
                    output.add(currentProduct);
                    currentProduct = new Product();
                    amountOfProducts++;

                }else if(!containsProductStart && containsData) {

                    setProductAttribute(line, currentProduct);

                }

            }
            
            br.close();

        }catch (StringIndexOutOfBoundsException e){
            System.out.println("String index out of bounds at line " + currentLineNumber);
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();

        }

        return output;
    }

    private String[] calculateInStockArray(BufferedReader br, Product currentProduct, ArrayList<String> array) throws IOException {
        String arrayLine;

        while((arrayLine = br.readLine()) != null && arrayLine.contains("\"")){
            currentLineNumber++;
            int entryStart = arrayLine.indexOf("\"");
            int entryEnd = arrayLine.lastIndexOf("\"");

            array.add(arrayLine.substring(entryStart + 1,entryEnd));
        }

        String[] actualArray = new String[array.size()];

        for(int i = 0; i < array.size(); i++){
            actualArray[i] = array.get(i);
        }

        array.clear();
        return actualArray;
    }

    private String getPropertyName(String line){
        int propertyStart = line.indexOf("\"");
        int propertyEnd = line.indexOf(":");

        String toReturn =  line.substring(propertyStart + 1, propertyEnd - 1).trim();

        return toReturn;
    }

    private String getPropertyValue(String line){
        int valueEnd = findLastOccurence(",",line);
        int valueStart = countOccurences(line, '"') < 4 ? line.indexOf(":") - 1 : line.indexOf(":");

        return line.substring(valueStart + 3, valueEnd - 1);
    }

    private void setProductAttribute(String line, Product p){

        String propertyName = getPropertyName(line);

        for(ProductAttribute pA : ProductAttribute.values()){
            if(pA.alias.equalsIgnoreCase(propertyName)){
                p.set(pA, getPropertyValue(line));
                break;
            }
        }
    }

    public boolean write(ArrayList<Product> list, String filepath){
        boolean success;
        StringBuilder builder = new StringBuilder();

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            String propertyValue;

            builder.append("[");

            for(Product p : list){
                builder.append("\n{");

                for(ProductAttribute pAttr : ProductAttribute.values()){

                    if(pAttr != ProductAttribute.IN_STOCK) {

                        if((propertyValue = p.get(pAttr)) != null || !propertyValue.isEmpty()) {
                            builder.append("\n").append("\t").append("\"").append(pAttr.alias).append("\":").append(" \"").append(propertyValue).append("\",");
                        }

                    }else{

                        builder.append("\n").append("\t").append("\"").append(pAttr.alias).append("\": [");

                        for(String s : p.getLocations()){
                            builder.append("\n\t\"").append(s).append("\",");
                        }

                        builder = new StringBuilder(removeLastOccurence(",", builder.toString()));
                        builder.append("\n],");
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
            index = line.length() + 1;
        }

        return index;
    }

    private String removeLastOccurence(String whatToRemove, String line){
        int index = findLastOccurence(whatToRemove, line);

        return line.substring(index - 1, index).replace(",","");
    }

    private int countOccurences(String line, Character whatToCount){

        byte[] asByteArray = line.getBytes(StandardCharsets.UTF_8);
        int occurences = 0;

        for(byte b : asByteArray){
            if(b == whatToCount){
                occurences++;
            }
        }

        System.out.println("Found " + occurences + " occurences of: " + whatToCount + " at line: " + currentLineNumber);
        return occurences;
    }


}
