package com.mahirsoft.webservice.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mahirsoft")
public class MahirsoftProperties {
    

    private Storage storage = new Storage();


    public static class Storage {

        String root = "uploads";
        String profile = "profile";
        
        public String getRoot() {
            return root;
        }
        public void setRoot(String root) {
            this.root = root;
        }
        public String getProfile() {
            return profile;
        }
        public void setProfile(String profile) {
            this.profile = profile;
        }

    }








    public Storage getStorage() {
        return storage;
    }


    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
