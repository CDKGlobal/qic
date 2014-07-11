package com.testing123.vaadin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonToTxt {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws FileNotFoundException {
        // private static final ObjectMapper mapper = new ObjectMapper();
        //        WebData user = mapper.readValue(new File(''), WebData.class);
        String date = "/2014-07-11T06-09-12-0700";
        String home = System.getProperty("user.home");
        String absolutePath = home + "/Perforce/weiyoud_sea-weiyoud_4033/Playpen/QIC2/Archives/";
        System.out.println(absolutePath);
        List<WebData> list = getData(absolutePath, date);
        // FileWriter outFile;
        // try {
        // outFile = new FileWriter(date + ".txt");
        PrintWriter writer = new PrintWriter(absolutePath + date + ".txt");
        for (WebData file : list) {
            writer.print(file.getId() + "\t" + file.getKey() + "\t" + file.getName() + "\t" + file.getScope()
                            + "\t" + file.getQualifier() + "\t" + file.getDate());
            if (file.getMsr() == null) {
                writer.println("\t-1.0 \t -1.0");
            } else {
                writer.println("\t" + file.getMsr().get(0).getVal() + "\t" + file.getMsr().get(1).getVal());
            }

            // } catch (Exception e) {
            // e.printStackTrace();
            // }
        }
        writer.close();

    }

    private static List<WebData> getData(String absolutePath, String date) {
        try {
            return mapper.readValue(new File(absolutePath + date + "/17271/files.json"),
                            new TypeReference<List<WebData>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}