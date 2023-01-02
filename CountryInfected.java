/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testteampro;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Teety
 */
public class CountryInfected {
    public String country;
    public List<Integer> infected = new ArrayList<Integer>();
    public void setName(String inName){
        switch (inName) {
            case "Holy See" :
            	inName = "Vatican City";
            	break;
            case "Korea South" :
            	inName = "South Korea";
            	break;
            case "Taiwan*" :
            inName = "Taiwan";
            break;
            default :{
            }
        }
        this.country = inName;
    }
    public void setList(List<Integer> inList){
        this.infected = inList;
    }
    public void addData(List<Integer> inList){
        for (int i = 0; i < this.infected.size(); i++){
            this.infected.set(i,this.infected.get(i) + inList.get(i)); 
        }
    }
    public int[] getAllCases(){
    	int[] arr = new int[infected.size()];
    	for (int i = 0; i < infected.size(); i++)
            arr[i] = infected.get(i);
    	return arr;
    }
    public String getName() {
    	return country;
    }
    
}

