package com.STIW3054.A191;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

class RepoLink {

    static ArrayList<String> getLink() {
        // Get assignment 1 link
        ArrayList<String> arrLink = new ArrayList<>();
        try {
            // connect 
            final Document doc = Jsoup.connect("https://github.com/STIW3054-A191/Assignments/issues/1").get();
            Elements elements = doc.select("table").select("a");

            for (Element arry : elements) {
                String elementLink = arry.attr("href");

                // Test .git link
                if (elementLink.endsWith(".git")) {
                    arrLink.add(elementLink.substring(0, elementLink.length() - 4));
                } else {
                    arrLink.add(elementLink);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrLink;
    }
}
