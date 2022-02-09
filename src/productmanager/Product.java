package productmanager;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Product {

    private final HashMap<ProductAttribute, String> productAttributes;
    private String[] availableAt;

    public Product(){
        productAttributes = new HashMap<>();
        availableAt = new String[10];
    }


    public String get(ProductAttribute pA){
        productAttributes.putIfAbsent(pA, "unavailable");
        return productAttributes.get(pA);
    }
    public String[] getLocations(){
        return availableAt;
    }
    public boolean set(ProductAttribute pA, String value){

        if(value.endsWith("\"")){
            value = value.substring(0,value.length() - 1);
        }

        productAttributes.put(pA,value);
        return Objects.equals(productAttributes.get(pA), value);
    }
    public boolean setLocations(ProductAttribute pA, String[] values){
        availableAt = values;
        return availableAt.length > 1;
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
