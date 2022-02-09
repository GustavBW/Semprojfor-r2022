package productmanager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class JSONReader {

    private String filepath;

    public JSONReader(String filepath){
        this.filepath = filepath;
    }


    public ArrayList<Product> read(){

        ArrayList<Product> output = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));

            

            br.close();
        }catch (IOException e){
            e.printStackTrace();

        }





    }

}
