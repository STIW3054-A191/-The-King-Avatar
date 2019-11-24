package com.STIW3054.A191;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.LinkedList;

public class ScrapeData
{
    public List<Data> findAll() {
        try {
            System.out.println("");
            String URL = "https://github.com/STIW3054-A191/Assignments/issues/1";
            Document doc = Jsoup.connect(URL).get();
            String tittle = doc.title();
            System.out.printf("%66s", tittle + "\n");
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.printf("| %-5s\n","Link");
            System.out.println("----------------------------------------------------------------------------------------");
            ArrayList<Data> result = new ArrayList<Data>();

            Elements linkdata = doc.select("table>tbody>tr>td");
            for (int i = 1; i < linkdata.size(); i++){
                Elements linkindata=linkdata.get(i).select("p");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
