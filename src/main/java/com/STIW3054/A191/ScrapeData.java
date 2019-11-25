package com.STIW3054.A191;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class ScrapeData {

    public void findAll() {
        try {
            System.out.println("");
            String URL = "https://github.com/STIW3054-A191/Assignments/issues/1";
            Document doc = Jsoup.connect(URL).get();
            String tittle = doc.title();
            System.out.printf("%66s", tittle + "\n");
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.printf("| %-10s| %-90s|\n", "No", "Link");
            System.out.println("----------------------------------------------------------------------------------------");

            int i = 1;
            ArrayList<String> arrLink = getLink();
            for(String link:arrLink){
                System.out.printf("| %-10s| %-90s|\n", i, link);
                System.out.printf("| %-10s| %-90s|\n", " ", "Cloning " + link);
                CloneRepo.clone(link);
                System.out.printf("| %-10s| %-90s|\n", " ", "Completed Cloning");
                i++;
            }


                    //result.add(new Data(matchLink.group()));

            System.out.println("----------------------------------------------------------------------------------------");
            //return result;

        } catch (Exception e) {
            //return null;
        }
    }

    static String getRepoName(String repoUrl) {
        String[] splitUrl = repoUrl.split("/");
        return splitUrl[splitUrl.length-1];
    }

    private static ArrayList<String> getLink() {
        // Get assignment 1 link
        ArrayList<String> arrLink = new ArrayList<>();
        try {
            final Document document = Jsoup.connect("https://github.com/STIW3054-A191/Assignments/issues/1").get();
            Elements elements = document.select("table").select("a");

            for (Element element : elements) {
                String elementLink = element.attr("href");

                // Test .git link
                if(elementLink.endsWith(".git")){
                    arrLink.add(elementLink.substring(0, elementLink.length() - 4));;
                }else {
                    arrLink.add(elementLink);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrLink;
    }

}
