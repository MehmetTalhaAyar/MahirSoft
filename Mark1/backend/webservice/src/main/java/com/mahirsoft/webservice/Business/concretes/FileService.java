package com.mahirsoft.webservice.Business.concretes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import com.mahirsoft.webservice.Configuration.MahirsoftProperties;

@Service
public class FileService {

    private MahirsoftProperties mahirsoftProperties;
    
    private Tika tika = new Tika();

    public FileService(MahirsoftProperties mahirsoftProperties) {
        this.mahirsoftProperties = mahirsoftProperties;
    }


    public String saveBase64StringAsFile(String image){

        String filename = UUID.randomUUID().toString();

        Path path = getProfileImagePath(filename);


        try {
            OutputStream outputStream = new FileOutputStream(path.toFile());
            outputStream.write(decodedImage(image));
            outputStream.close();

            return filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String detectType(String value){
        return tika.detect(decodedImage(value));
    }

    private Path getProfileImagePath(String filename){
        return Paths.get(mahirsoftProperties.getStorage().getRoot(), mahirsoftProperties.getStorage().getProfile(),filename);
    }

    private byte[] decodedImage(String encodedImage){
        return Base64.getDecoder().decode(encodedImage.split(",")[1]);
    }

}
