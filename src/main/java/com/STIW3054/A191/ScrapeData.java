package com.STIW3054.A191;

import javax.lang.model.util.Elements;
import javax.swing.text.Document;
import java.io.IOException;
import java.util.LinkedList;

public class ScrapeData
{
    public static LinkedList<info> findAll() throws IOException
    {
        LinkedList<info> info = new LinkedList<info>();
        final String url = "https://github.com/STIW3054-A191/Main-Issues/issues/1";
        final Document file = Jsoup.connect(url).get();
        Elements row = file.select("table");

        return null;
    }

}
