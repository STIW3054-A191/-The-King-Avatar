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

    public void setExtdirs(Path e)
    {
        if (extdirs == null)
        {
            extdirs = e;
        }

        else {
//            extdirs.append(e);
        }
    }

    public Path getExtdirs()
    {
        return extdirs;
    }

    public Path createExtdirs()
    {
        if (extdirs == null)
        {
//            extdirs = new Path(getProject());
        }

        return extdirs;
    }

    public void execute()
    {
        if (classDir == null)
        {
//            throw new BuildException("classdir attribute must be set!");
        }

        if (!classDir.exists())
        {
//            throw new BuildException("classdir does not exist!");
        }

        if (!classDir.isDirectory())
        {
//            throw new BuildException("classdir is not a directory!");
        }

//        if (extdirs != null && extdirs.size() > 0)
        {
            if (System.getProperty("java.ext.dirs").length() == 0)
                System.setProperty("java.ext.dirs", extdirs.toString());
            else
                System.setProperty("java.ext.dirs",
                        System.getProperty("java.ext.dirs") + File.pathSeparator +
                                extdirs);
        }








}
