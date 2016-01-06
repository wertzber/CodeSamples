package config;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.net.URL;
import java.util.Iterator;

/**
 * Created by eladw on 2/9/2015.
 */
public class Config {

    public static final void main(String []args){
        Config c = new Config();
        try {
            c.compositeConfigurations();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void compositeConfigurations() throws ConfigurationException {
        CompositeConfiguration config = new CompositeConfiguration();

        try{
            String systemFile = System.getProperty("AMS_CONFIG");
            if(systemFile != null) config.addConfiguration(new PropertiesConfiguration(systemFile));
        } catch ( Exception e){
            System.out.println("error 3");
        }

        try{
            config.addConfiguration(new PropertiesConfiguration("/liveperson/code/server_ams/conf/ams.properties"));
        } catch ( Exception e){
            System.out.println("file: ams.properties does not exists");
        }
        URL inputUrl = this.getClass().getResource("/test.properties");
        try{
            if(inputUrl != null){
                config.addConfiguration(new PropertiesConfiguration(inputUrl));
            } else {
                System.out.println("input url is null");
            }

        } catch ( Exception e){
            System.out.println("error get internal ams.prop");
        }

        Configuration extConfig = config.interpolatedConfiguration();

        Iterator<String> keys = extConfig.getKeys();
        while(keys.hasNext()){
            String key=(String)keys.next();
            String value=extConfig.getString(key);
            System.out.println(key +  ":" + value);
        }

    }

    private void automaticReloadingOfConfigurations() throws Exception {
        PropertiesConfiguration configs = new  PropertiesConfiguration("test.properties");
        configs.setReloadingStrategy(new FileChangedReloadingStrategy());

        System.out.println(configs.getString("name"));
        Thread.sleep(10000);
        //change file on disk
        System.out.println(configs.getString("name"));
    }



}
