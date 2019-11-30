package com.STIW3054.A191;

import org.apache.poi.sl.draw.geom.Path;

import java.io.File;

public class CalculateTask
{
    private File outputFile;

    private File classDir;

    private Path extdirs;

    private String format;

    public CalculateTask()
    {
        this.format = "plain";
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public void setOutputfile(File outputfile)
    {
        this.outputFile = outputfile;
    }

    public void setClassdir(File classDir)
    {
        this.classDir = classDir;
    }

}
