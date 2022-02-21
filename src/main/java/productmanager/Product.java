package productmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Product { //initialize class

    private final HashMap<ProductAttribute, String> productAttributes; //initialize hashmap to contain product attributes
    private String[] availableAt; //initialize array of strings to contain local stores with stock

    public Product(){ //product constructor w.o. attribute input
        productAttributes = new HashMap<>(); //initialize hashmap
        availableAt = new ArrayList<String>(); //initialize string array
    }


    public String get(ProductAttribute pA){ //String method running through pA's to assign values to productAttributes
        productAttributes.putIfAbsent(pA, "unavailable"); //replace nulls with string "unavailable" >function may be removed?
        return productAttributes.get(pA); //returns hashmap pf pA's
    }

    public String[] getLocations(){ //String array method returning the class attribute availableAt
        return availableAt;
    }

    public HashMap<ProductAttribute,String> getAttributeMap(){
        return productAttributes;
    }

    public boolean set(ProductAttribute pA, String value){

        if(value.endsWith("\"")){
            value = value.substring(0,value.length() - 1);
        }

        productAttributes.put(pA,value);
        return productAttributes.get(pA).equalsIgnoreCase(value);
    }

    public boolean setLocations(ProductAttribute pA, String[] values){
        availableAt = values;
        return availableAt.length > 0;
    }

    @Override
    public String toString(){
        return "Product: " + productAttributes.get(ProductAttribute.NAME) + " price: " + productAttributes.get(ProductAttribute.PRICE);
    }

    public void print(){
        System.out.println("Product : " + productAttributes.get(ProductAttribute.NAME));

        for(ProductAttribute pA : ProductAttribute.values()){

            if(pA == ProductAttribute.IN_STOCK){
                StringBuilder toPrint = new StringBuilder();
                for(String s : availableAt){
                    toPrint.append(s).append("\t");
                }
                System.out.println("\t " + pA.alias + ": " + toPrint);

            }else {

                System.out.println("\t " + pA.alias + ": " + get(pA));
            }
        }
    }
}