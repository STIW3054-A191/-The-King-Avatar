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
            //the url link
            String URL = "https://github.com/STIW3054-A191/Assignments/issues/1";
            Document page = Jsoup.connect(URL).get();
            String title = page.title();
            System.out.printf("%66s", title + "\n");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-10s| %-90s| %-15s\n", "No", "Link", "ThreadNo");
            System.out.println("---------------------------------------------------------------------------------------------------------------");

            ArrayList<String> arrayLink = RepoLink.getLink();
            for (int i = 0; i <= 28; i++) {
                for (String link : arrayLink) {
                    //int i = 1;
                    i++;
                    System.out.printf("| %-10s", i);
                    Thread run = new Thread(new Thread() {
                        @Override
                        public synchronized void run() {
                            System.out.printf("| %-90s| %-15s|\n", link, " ");
                            System.out.printf("| %-10s| %-90s| %-15s|\n", " ", "Now Cloning" + link, Thread.currentThread().getName());
                            CloneRepo.clone(link);
                            System.out.printf("| %-10s| %-90s| %-15s|\n", " ", "Done Cloning", " ");
                        }
                    });
                    run.start();

                    try {

                        run.join();
                        run.sleep(200);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("----------------------------------------------------------------------------------------");

        } catch (Exception e) {
            //return null;
        }
    }

}
