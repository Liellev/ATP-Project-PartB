package Server;

import java.io.InputStream;
import java.util.Properties;

public class Configurations extends Properties{
    private static Configurations instance=null;
    private final Properties properties=new Properties();

    private Configurations(){
        try(InputStream input=getClass().getClassLoader().getResourceAsStream("config.properties")){
           if(input==null){
               throw new RuntimeException("config not found");
           }
           properties.load(input);
        }catch(Exception e){
            throw new RuntimeException("failed to config");
        }
    }

    public static Configurations getInstance(){
        if (instance==null){
            synchronized (Configurations.class){
                if(instance==null){
                    instance=new Configurations();
                }
            }

        }
        return instance;
    }


    @Override
    public String getProperty(String key) {
        return super.getProperty(key);
    }
}
