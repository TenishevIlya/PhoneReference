package com.company;

import java.io.FileReader;
import java.io.FileWriter;

public class FileConnect{
  public void connection() throws Exception{
      FileReader fr =  new FileReader("in.txt");
      FileWriter fw = new FileWriter("in.txt");
  }
}
