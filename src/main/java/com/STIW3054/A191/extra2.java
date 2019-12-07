/*class Foo {
  void baseCyclo() {                // Cyclo = 1
    highCyclo();
  }
  
  void highCyclo() {                // Cyclo = 10
    int x = 0, y = 2;
    boolean a = false, b = true;
    
    if (a && (y == 1 ? b : true)) { // +3
      if (y == x) {                 // +1
        while (true) {              // +1
          if (x++ < 20) {           // +1
            break;                  // +1
          }
        }
      } else if (y == t && !d) {    // +2
        x = a ? y : x;              // +1
      } else {
        x = 2;
      }
    }  
  }     
}*/