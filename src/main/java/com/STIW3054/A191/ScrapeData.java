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
    public List<Data> findAll()
    {
        LinkedList<info> info = new LinkedList<info>();
        final String url = "https://github.com/STIW3054-A191/Main-Issues/issues/1";
        final Document file = Jsoup.connect(url).get();
        Elements row = file.select("table");
    }

}
