package Server;

import java.io.InputStream;
import java.util.Properties;


/**
 * This is a singleton class.
 * It allows us to implement and pull all necessary
 * properties in the program.
 */
public class Configurations extends Properties{

    private static Configurations instance=null;
    private final Properties properties=new Properties();

    /**
     * Private constructor.
     */
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

    /**
     * method to get the 1 created instance of the class.
     * @return Configurations instance.
     */
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


    /**
     * This method is a getter for the property from config file.
     * @param key   the property key.
     * @return String represent the value of the property
     */
    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
