package GUI;

import productmanager.Product;
import productmanager.ProductJSONReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonCache {

    private static JsonCache instance;
    private static File cacheFile;
    private static String root;
    static{
        try {
            root = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        root = root + "/src/main/resources";
    }

    private final ProductJSONReader reader;
    private List<Product> quickAccess;
    private boolean cacheFileFound = false,removeCacheOnExit = false;
    private Thread shutdownHook,dumpHook;

    public static JsonCache getInstance(){
        System.out.println("CACHE: INSTANCE REQUESTED");
        if(instance == null){
            try {
                instance = new JsonCache();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private JsonCache(boolean removeCacheOnExit) throws IOException{
        System.out.println("CACHE: CREATING INSTANCE");

        cacheFile = new File(root + "/guiCache.json");
        cacheFileFound = cacheFile.createNewFile();
        reader = new ProductJSONReader(cacheFile.getCanonicalPath());
        quickAccess = new ArrayList<>();

        if(!cacheFileFound) {
            System.out.println("CACHE: FILE FOUND. READING CONTENTS");
            quickAccess = reader.read();
            System.out.println("CACHE: FILE CONTAINED " + quickAccess.size() + " ELEMENTS");
        }

        System.out.println("CACHE: CREATION SUCCESS");

        if(removeCacheOnExit){
            addShutdownHook(getDestroyHook());
        }else{
            addShutdownHook(getDumpHook());
        }
    }
    private JsonCache() throws IOException{
        this(false);
    }

    private void updateCache() throws IOException{
        System.out.println("CACHE: UPDATING TO CONTAIN " + quickAccess.size() + " ELEMENTS");
        reader.write(quickAccess);
    }

    public void overwrite(List<Product> list){
        quickAccess = list;
    }

    public void add(List<Product> list) throws IOException{
        quickAccess.addAll(list);
        updateCache();
    }
    public void add(String path) throws IOException{
        this.add(reader.read(path));
        updateCache();
    }

    public void remove(List<Product> list) throws IOException{
        quickAccess.removeAll(list);
        updateCache();
    }

    public List<Product> get(){
        return new ArrayList<>(quickAccess);
    }

    public void dump() throws IOException{
        updateCache();
    }
    public void setDestroyOnExit(boolean state){
        if(state && !removeCacheOnExit){
            addShutdownHook(getDestroyHook());
            removeShutdownHook(getDumpHook());
            removeCacheOnExit = true;
        }else if(!state && removeCacheOnExit){
            removeShutdownHook(getDestroyHook());
            addShutdownHook(getDumpHook());
            removeCacheOnExit = false;
        }
    }
    public boolean destroysOnExit(){
        return removeCacheOnExit;
    }
    private void addShutdownHook(Thread hook){
        Runtime.getRuntime().addShutdownHook(hook);
    }
    private void removeShutdownHook(Thread hook){
        Runtime.getRuntime().removeShutdownHook(hook);
    }
    private Thread getDestroyHook(){
        if(shutdownHook == null){
            shutdownHook = new Thread(() -> {
                try {
                    destroy();
                }catch(Throwable e){
                    e.printStackTrace();
                }
            });
        }
        return shutdownHook;
    }
    private Thread getDumpHook(){
        if(dumpHook == null){
            dumpHook = new Thread(() -> {
                try {
                    System.out.println("CACHE: DUMPING ON EXIT");
                    dump();
                }catch(Throwable e){
                    e.printStackTrace();
                }
            });
        }
        return dumpHook;
    }

    private void destroy(){
        System.out.println("CACHE: DESTROYED ON EXIT: " + cacheFile.delete());
    }

}
