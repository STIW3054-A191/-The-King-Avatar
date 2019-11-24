package com.STIW3054.A191;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
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
            System.out.printf("| %-10s| %-80s|\n", "No", "Link");
            System.out.println("----------------------------------------------------------------------------------------");

            Elements linkdata = doc.select("table>tbody>tr>td");
            for (int i = 1; i < linkdata.size(); i++) {
                
                Elements linkindata = linkdata.get(i).select("p");
                
                for (int j = 0; j < linkindata.size(); j++) {
                    String repoLink = null;
                    
                    Pattern link = Pattern.compile("https://.*");
                    Matcher matchLink = link.matcher(linkindata.get(j).text());
                    
                    if (matchLink.find()) {
                        System.out.printf("| %-10s| %-80s\n",i, matchLink.group());
                        repoLink = matchLink.group();
                    } else {
                        System.out.print(" ");
                    }
                    
                    String repoUrl = repoLink;
                    String cloneDirectoryPath = ("D://git/");
                    
                    try{
                        File file = new File(cloneDirectoryPath+i);
                        file.mkdir();
                        System.out.printf("| %-10s| %-70s|\n"," ","Cloning " + repoUrl + " into folder " + i);
                        Git.cloneRepository()
                                .setURI(repoUrl)
                                .setDirectory(file)
                                .call();
                    } catch (GitAPIException e){
                        
                    }
                    

                    //result.add(new Data(matchLink.group()));
                }
            }
            System.out.println("----------------------------------------------------------------------------------------");
            //return result;

        } catch (Exception e) {
            //return null;
        }
    }

}
