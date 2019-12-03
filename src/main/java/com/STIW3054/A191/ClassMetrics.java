package com.STIW3054.A191;

import java.util.HashSet;

public class ClassMetrics
{
    /** Weighted methods per class */
    private int wmc;
    /** Number of children */
    private int noc;
    /** Response for a Class */
    private int rfc;
    /** Coupling between object classes */
    private int cbo;
    /** Depth of inheritence tree */
    private int dit;
    /** Lack of cohesion in methods */
    private int lcom;
    /** Number of public methods */
    private int npm;
    /** True if the class has been visited by the metrics gatherer */
    private boolean visited;
    /** True if the class is public */
    private boolean isPublicClass;
    /** Coupled classes: classes that use this class */
    private HashSet<String> afferentCoupledClasses;

    ClassMetrics()
    {
        wmc = 0;
        noc = 0;
        cbo = 0;
        npm = 0;
        visited = false;
        afferentCoupledClasses = new HashSet<String>();
    }

    /** Increment the weighted methods count */
    public void incWmc() { wmc++; }

    /** Return the weighted methods per class metric */
    public int getWmc() { return wmc; }




}
