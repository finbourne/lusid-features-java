package com.finbourne.features.reporter;
import java.io.*;

public class FeatureFileWriter {

    private FileOutputStream fileOut;

    public void writeToFile(String data, String filePath) throws IOException {
        File fileToWrite = new File(filePath);
        fileToWrite.createNewFile(); // if file already exists, this will do nothing
        fileOut = new FileOutputStream(fileToWrite);
        fileOut.write(data.getBytes());
        fileOut.close();
    }



}
