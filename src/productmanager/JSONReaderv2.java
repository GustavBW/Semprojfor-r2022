package productmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class JSONReaderv2 {

    private final String filepath;

    private final File writeProfFile;
    private final File readProfFile;

    public JSONReaderv2(String filepath){
        this.filepath = filepath;
        writeProfFile = new File("resources/JSONReaderProfiling/writeSpeed.txt");
        readProfFile = new File("resources/JSONReaderProfiling/readSpeed.txt");

    }

    public ArrayList<Product> read() throws IOException {
        ArrayList<Product> output = new ArrayList<>();
        int currentByte;
        boolean attrVal = true;
        boolean attrArr = true;
        boolean startEnd = false;


        try{
            InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filepath)));

            while((currentByte = isr.read()) != 0){

                if(currentByte == '"'){
                    startEnd = !startEnd;
                }

            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }



        return output;
    }
}
