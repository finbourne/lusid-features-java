package com.finbourne.features;
import java.io.*;

public class FeatureFileWriter {

    private FileOutputStream fileOut;

    public void writeToFile(String data, String filePath) throws IOException {
        fileOut = new FileOutputStream(filePath);
        fileOut.write(data.getBytes());
        fileOut.close();
    }



}
