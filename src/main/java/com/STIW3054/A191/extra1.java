
/*package com.STIW3054.A191;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class extra1 {
   
    // total 8
    public Set set = new HashSet(); // +2
    public Map map = new HashMap(); // +2
    public String string = ""; // from java.lang -> does not count by default
    public Double number = 0.0; // from java.lang -> does not count by default
    public int[] intArray = new int[3]; // primitive -> does not count

    @Deprecated // from java.lang -> does not count by default
    @Override // from java.lang -> does not count by default
    public void foo(List list) throws Exception { // +1 (Exception is from java.lang)
        throw new IOException(); // +1
    }

    public int getMapSize() {
        return map.size(); // +1 because it uses the Class from the 'map' field
    }   
}*/