package dk.sdu.se_f22.productmodule.management.GUI;

import dk.sdu.se_f22.productmodule.management.domain_persistance.BaseProduct;
import dk.sdu.se_f22.productmodule.management.domain_persistance.ProductJSONReader;
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
        root = root + "/src/main/resources/dk/sdu/se_f22/productmodule/management/GUI/";
    }

    private final ProductJSONReader reader;
    private List<BaseProduct> quickAccess;
    private boolean removeCacheOnExit = false;
    private Thread shutdownHook,dumpHook;

    public static JsonCache getInstance(){
        System.out.println("CACHE " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " : INSTANCE REQUESTED");
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
        System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : CREATING INSTANCE");

        cacheFile = new File(root + "guiCache.json");
        boolean cacheFileFound = cacheFile.createNewFile();
        reader = new ProductJSONReader(cacheFile.getCanonicalPath());
        quickAccess = new ArrayList<>();

        if(!cacheFileFound) {
            System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : FILE FOUND. READING CONTENTS");
            quickAccess = reader.read();
            System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : FILE CONTAINED " + quickAccess.size() + " ELEMENTS");
        }

        System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : CREATION SUCCESS");

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
        System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : UPDATING TO CONTAIN " + quickAccess.size() + " ELEMENTS");
        if(quickAccess.isEmpty()){return;}
        reader.write(quickAccess);
    }

    public void overwrite(List<BaseProduct> list){
        quickAccess = list;
    }

    public void add(List<BaseProduct> list){
        System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : ADDING " + list.size() + " ELEMENTS");
        quickAccess.addAll(list);
    }
    public void add(String path) throws IOException{
        if(reader.validate(path)) {
            add(reader.read(path));
        }else{
            System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : FILEPATH " + path + " RETURNES INVALID FILE");
        }
    }
    public void add(BaseProduct p) throws IOException{
        add(List.of(p));
    }

    public void remove(List<BaseProduct> list){
        quickAccess.removeAll(list);
    }
    public void remove(BaseProduct p) throws IOException{
        quickAccess.remove(p);
    }

    public List<BaseProduct> get(){
        return new ArrayList<>(quickAccess);
    }

    public void dump() throws IOException{
        updateCache();
    }

    public void dumpTo(String path){
        System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : DUMPING CACHE TO " + path);
        reader.write(quickAccess,path);
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
                    System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : DUMPING ON EXIT");
                    dump();
                }catch(Throwable e){
                    e.printStackTrace();
                }
            });
        }
        return dumpHook;
    }

    private void destroy(){
        System.out.println("CACHE " + Thread.currentThread().getStackTrace()[1].getLineNumber() + " : DESTROYED ON EXIT: " + cacheFile.delete());
    }

}
