/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testteampro;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Teety
 */
public class Proj2190101_3_5 {
     private final static String COMMA_DELIMITER = ",";
    /**
     * @param args the command line arguments
     */
    public static int isDup(List<CountryInfected> c, String name){
        for (int i = 0; i < c.size(); i++){
            if(c.get(i) == null){
                break;
            }
            if(c.get(i).country.equals(name)){
                return i;
            }
        }
        return -1;
    }
    
    public static List<CountryInfected> getInfected() throws IOException {
        List<CountryInfected> c = new ArrayList<CountryInfected>();
        String name = "unknown", read;
        boolean more;

        try (Scanner scanner = new Scanner(new File("C:\\Users\\Tin\\Downloads\\time_series_covid19_confirmed_global.csv"))) {
            String line = scanner.nextLine();
            while (scanner.hasNextLine()) {
                CountryInfected newC = new CountryInfected();
                more = false;
                line = scanner.nextLine();
                List<Integer> datas = new ArrayList<Integer>();
                List<String> fields = new ArrayList<>();
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(COMMA_DELIMITER);
                    while (rowScanner.hasNext()) {
                        read = rowScanner.next();
                        if(read.isEmpty() == false){
                            if((read.charAt(0) >= 'a' && read.charAt(0) <= 'z') || (read.charAt(0) >= 'A' && read.charAt(0) <= 'Z')){
                                name = read;
                            }
                            else{
                                fields.add(read);
                            }
                        }
                    }
                    for (int i = 2; i < fields.size(); i++){
                            datas.add((int)Double.parseDouble(fields.get(i)));
                        if((int)Double.parseDouble(fields.get(i)) >= 100){
                            more = true;
                        }
                    }
                }
                if(more){
                    if(isDup(c, name) != -1){
                        c.get(isDup(c, name)).addData(datas);
                    }
                    else{
                        newC.setName(name);
                        newC.setList(datas);
                        c.add(newC);
                    }
                }
            }
        }
        return c;
    }
    

    public static double[] getDoNothingCurve(List<Integer> pastData, int numFutureDays){
        //double[] cpyData = new double[pastData.size() + numFutureDays]; อันเก่า
    	double[] cpyData = new double[pastData.size()]; //คนใหม่
        double[] ansData = new double[numFutureDays];
        int recent;
        for(int i = 0 ; i < pastData.size() ; i++){
            cpyData[i] = (double)pastData.get(i);
        }
        recent = pastData.size() - 1;

       /* double w,x,y,z;
        for(int i = 1 ; i < numFutureDays; i++){
            recent += 1;
            w = cpyData[recent - 1];
            x = cpyData[recent - 2];
            y = cpyData[recent - 3];
            z = cpyData[recent - 4];
            cpyData[recent] = w * (((w / x) + (x / y) + (y / z)) / 3.0);
            ansData[i - 1] = cpyData[recent]; 
        }*/
        double[] nd = new double[numFutureDays+9];
        double[] total_infected = new double[numFutureDays+9];
        
        double nd1,nd2,nd3,nd4,rd;
        
       	total_infected[0] = cpyData[cpyData.length-1];
		nd[8] = cpyData[cpyData.length-1]-cpyData[cpyData.length-2];
        nd[7] = cpyData[cpyData.length-2]-cpyData[cpyData.length-3];
       	nd[6] = cpyData[cpyData.length-3]-cpyData[cpyData.length-4];
       	nd[5] = cpyData[cpyData.length-4]-cpyData[cpyData.length-5];
		nd[4] = cpyData[cpyData.length-5]-cpyData[cpyData.length-6];
        nd[3] = cpyData[cpyData.length-6]-cpyData[cpyData.length-7];
       	nd[2] = cpyData[cpyData.length-7]-cpyData[cpyData.length-8];
       	nd[1] = cpyData[cpyData.length-8]-cpyData[cpyData.length-9];
		nd[0] = cpyData[cpyData.length-9]-cpyData[cpyData.length-10];
       	
       	//nd[j] = nd[j-1] * (((nd[j-1] / nd[j-2]) + (nd[j-2] / nd[j-3]) + (nd[j-3] / nd[j-4])) / 3.0);
		//total_infected[i] = nd[j]+ total_infected[i-1]; //i=1 j=4 i++ j++
        //System.out.println("FIRST DO NOTHING MODEL : "+total_infected[i]);
       	//System.out.println(cpyData[cpyData.length-1]);
       
		   for(int i = 1 , j = 9 ; i < total_infected.length & j < total_infected.length ; i++ ,j++)
       	{
       		nd[j] = nd[j-1] * (((nd[j-1] / nd[j-2]) + (nd[j-2] / nd[j-3]) + (nd[j-3] / nd[j-4]) + (nd[j-4] / nd[j-5]) + (nd[j-5] / nd[j-6]) + (nd[j-6] / nd[j-7]) + (nd[j-7] / nd[j-8]) + (nd[j-8] / nd[j-9])) / 8.0);
			   total_infected[i] = nd[j]+ total_infected[i-1];
       		//System.out.println((((nd[j-1] / nd[j-2]) + (nd[j-2] / nd[j-3]) + (nd[j-3] / nd[j-4]) + (nd[j-4] / nd[j-5]) + (nd[j-5] / nd[j-6]) + (nd[j-6] / nd[j-7]) + (nd[j-7] / nd[j-8]) + (nd[j-8] / nd[j-9])) / 8.0));
       	}
       	
        /*for(int i = 1,j = 1,k = 1, z = 2, m = 3, g = 4; i < nd.length ; i++,j++,k++,z++,m++,g++)
        {
        	nd1 = nd[i-j]-cpyData[cpyData.length-k];
        	nd2 = cpyData[cpyData.length-k]-cpyData[cpyData.length-z];
        	nd3 = cpyData[cpyData.length-z]-cpyData[cpyData.length-m];
        	nd4 = cpyData[cpyData.length-m]-cpyData[cpyData.length-g];
        	
        	nd[i] = nd1 * (((nd1 / nd2) + (nd2 / nd3) + (nd3 / nd4)) / 3.0);
        	
        	rd = nd[i-j];
        	nd[i] = nd[i]+rd;
        }*/
        
        return total_infected;
    }
    
    public static double[] getSCurve (int[] pastData, int numFutureDays, double[] paramLowerBounds, double[] paramUpperBounds)
    {
    	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final String firstInput = "02 02 2021";
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");  
   	 	LocalDateTime now = LocalDateTime.now(); 
   	 	
        final String secondInput = dtf.format(now);
        final LocalDate firstDate = LocalDate.parse(firstInput, formatter);
        final LocalDate secondDate = LocalDate.parse(secondInput, formatter);
        final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        double d_lek = numFutureDays+days+1;
        
    	System.out.println("This fitted S-curve model has S=["+paramLowerBounds[0]+","+paramUpperBounds[0]+"], D=["
    +paramLowerBounds[1]+","+paramUpperBounds[1]+"], L=["+paramLowerBounds[2]+","+paramUpperBounds[2]+"], M=["+paramLowerBounds[3]
    		+","+paramUpperBounds[2]+"], with the first projected day being d=" + d_lek);//(2 feb - current day) + input day
    
    	double[] L = new double[110];
    	for(int i = 0,j = 377 ; i < 111 & j < 486 ; i++,j++) //find the range of L
    	{
    		int d = (i+2);
    		double a = Math.log((paramLowerBounds[3]/(pastData[j]-paramLowerBounds[0]))-1.0);
    		double b = d-paramLowerBounds[1];
    		double l = (a/b)*(-1.0);
    		L[i] = l;
    		//System.out.println("L"+i+" = "+L[i]);
    	}
    	
    	double[] sd = new double[110]; // use range of l value to calculate mse
    	double[] mse_find_l = new double[110];
    	double l_range = 0.052,l_upper = 0.0542,l_final = l_range;
    	if( pastData[pastData.length-1] == 5979597)
    	{
    		l_range = 0.04;// อย่าลืมเเก้ lower upper para 
    		l_upper = 0.07;
    	}
    	
    	int num = 0;
    	while(true)
    	{
    		if(num == mse_find_l.length) break;
	    	for(int i = 0 ,j = 377 ; i < mse_find_l.length & j < pastData.length ; i++,j++) // ลย condition l_range ไป
	    	{
	    		int d = (i+2);
	    		double sd_calculate = paramLowerBounds[0]+((paramLowerBounds[3])/(1.0+Math.pow(Math.E,(l_range*-1)*(d-paramLowerBounds[1]))));
	    		mse_find_l[num] = mse_find_l[num] + Math.pow(sd_calculate-pastData[j],2);
	    	}
	    	//System.out.println("MSE"+num+" = "+mse_find_l[num]+" ,L = "+l_range);
	    	if(l_range < l_upper)
	    	{
	    		l_range += 0.00002;
	    	}
			
	    	num++;
    	}
    	
    	double mse_min_value = Integer.MAX_VALUE;
    	int l_index = 0;
    	for(int i = 0 ; i < mse_find_l.length ; i++)
    	{
    		mse_find_l[i] = mse_find_l[i]/110.0;
    		if (i < mse_min_value)
    		{
    			mse_min_value = mse_find_l[i];
    			l_index = i;
    		}
    	}
    	//System.out.println(mse_min_value);
    	//System.out.println("l index = "+l_index);
    	l_final = l_final + (0.00019988*l_index);
    	//System.out.println(l_final); // L lowerBound of france
    	//france upper 0.0221-0.0224
    	double l_upperbound = 0.015,delta_min = Integer.MAX_VALUE,l_best = 0;
    	while(l_upperbound <= 0.025)
    	{
	    	for(int i = 0, j = 377 ; i < sd.length & j < 487 ; i++,j++)
	    	{
	    		int d = (i+2);
	    		double sd_find_upperbound = paramUpperBounds[0]+((paramUpperBounds[3])/(1.0+Math.pow(Math.E,(l_upperbound*-1)*(d-paramUpperBounds[1]))));
	    		double delta = sd_find_upperbound - pastData[j];
	    		if(Math.abs(delta) < delta_min) {
	    			delta_min = delta;
	    			l_best = l_upperbound;
	    		}
	    		//System.out.println("D = "+d+", L = "+l_upperbound+" , delta = "+delta);
	    	}
	    	//System.out.println();
	    	l_upperbound += 0.001;
    	}
    	//System.out.println("delta = "+delta_min+" ,l = "+l_best);
    	/*System.out.println("START LOOP");
    	double[] sd = new double[110];
    	for(int i = 0,j = 377 ; i < L.length & j < 486  ; i++,j++)
    	{
    		int d = (i+2);
    		int index = 0;
    		double l_range = 0.04;
    		while(true)
    		{
    			if(index == sd.length -1 )
    			{
    				break;
    			}
    			sd[index] = paramLowerBounds[0]+((paramLowerBounds[3])/(1.0+Math.pow(Math.E,(l_range*-1)*(d-paramLowerBounds[1]))));
    			System.out.println("sd"+index+" = "+sd[i]+"   l = "+l_range);
    			index++;
    			l_range += 0.01;
    		}    		
    	}
    	
    	double MSE = 0.0;
    	double total_pastData = 0.0,total_sd = 0.0;
    	for(int i : pastData)
    	{
    		total_pastData = total_pastData + i;
    	}
    	for(double i : sd)
    	{
    		total_sd = total_sd + i;
    	}
    	for(int i = 0,j = 377 ; i < 110 & j < 486 ; i++ ,j++)
    	{
    		MSE = MSE + Math.pow(total_pastData-total_sd,2);
    		
    	}
    	MSE = MSE/110.0;
    	//System.out.println(MSE);*/
    	//5979597
    	double min_mse = Integer.MAX_VALUE ,l_min = 0, sd_min = 0;
    	double[] SD = new double [113];
    	double mse = 0;
    	while(true)
    	{
    		if(l_range > l_upper)
    		{
    			break;
    		}
	    	for (int i = 0 ; i < SD.length ; i++ )
	    	{
	    		int d = (i+2);
	    		SD[i] = paramLowerBounds[0]+((paramLowerBounds[3])/(1.0+Math.pow(Math.E,(l_range*-1)*(d-paramLowerBounds[1]))));
	    		//System.out.println(d+"."+"l = "+l_range+" sd ="+SD[i]);
	    	}
	    	//System.out.println("l = "+l_range);
	    	for(int i = 0,j = 377 ; i < SD.length & j < 486 ; i++ ,j++)
	    	{
	    		mse = mse + Math.pow(SD[i]-pastData[j],2);
	    	}
	    	
	    	mse = mse / 110.0;
	    	if(mse < min_mse) 
	    	{
	    		min_mse = mse;
	    		l_min = l_range;
	    		
	   		}
	    	//System.out.println("MSE = "+mse);
	    	//System.out.println("\n");
	    	l_range += 0.0001;
	    	
    	}
    	System.out.println("Start date 2/2/2021 using S-Curve");
    	double mse_final = 0;
    	for (int i = 0,j = 377 ; i < pastData.length & j < pastData.length ; i++,j++ )
    	{
    		int d = (i+2);
    		double sdLower = paramLowerBounds[0]+((paramLowerBounds[3])/(1.0+Math.pow(Math.E,(paramLowerBounds[2]*-1)*(d-paramLowerBounds[1]))));
    		double sdUpper = paramUpperBounds[0]+((paramUpperBounds[3])/(1.0+Math.pow(Math.E,(paramUpperBounds[2]*-1)*(d-paramUpperBounds[1]))));
    		double delta1 = sdLower-pastData[j];
    		double delta2 = sdUpper-pastData[j];
    		//System.out.println("SD UPPER - PAST DATA = "+delta2);
    		if(Math.abs(delta1) < Math.abs(delta2))
    		{
    			System.out.println("SD"+d+":"+sdLower);
    			mse_final = mse_final + Math.pow(sdLower-pastData[j],2);
    		}
    		else if (Math.abs(delta2) < Math.abs(delta1))
    		{
    			System.out.println("SD"+d+":"+sdUpper);
    			mse_final = mse_final + Math.pow(sdUpper-pastData[j],2);
    		}
    	}
    	mse_final = mse_final/110.0;
    	System.out.println("MSE FINAL = "+mse_final);
    	
    	//System.out.println("min mse = "+min_mse+" ,l = "+l_min);
    	double average = 0.0,x1 = 0,x2 = 0;
    	for(int k = pastData.length-22 ; k < pastData.length ; k++)
		{
    		if(k==pastData.length-22) x1 = pastData[k];
			if(k==pastData.length-1) x2 = pastData[k];
		}
    	average =  x2-x1;
    	average = average / 20.0;
    	//System.out.println(average);
    	
    	double new_ll = 0.0175,new_d = 2;//201
    	/*while(true)
    	{
    		double new_sd = 2232327 + (2451727.5/(1+Math.pow(Math.E,(new_ll*-1)*(new_d - 92.8897))));
    		System.out.println("d = "+new_d+"  sd = "+new_sd);
    		if(new_d == 201) break;
    		new_d++;
    	}*/

    	double[] return_sCurve = new double[numFutureDays];
    	for(int i = 112 , k = 0; i < (112+numFutureDays) & new_d <= d_lek & k < return_sCurve.length; i++ ,new_d ++,k++)
    	{
    		double new_sd = paramUpperBounds[0] + (paramUpperBounds[3]/(1.0+Math.pow(Math.E,(paramUpperBounds[2]*-1.0)*(i - paramUpperBounds[1]))));
    		return_sCurve[k] = new_sd;
    	}
		return return_sCurve;
    }
    
    public static double round(double value, int places) 
    {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public static void main(String[] args) {
        List<CountryInfected> c = new ArrayList<CountryInfected>();
        try{
            c = getInfected();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        for(CountryInfected i : c){
            System.out.print(i.country);
            for(int j : i.infected){
            	System.out.print(" " + j + ",");
            }
            System.out.println("");
        }
        System.out.println();
        //double[] GermanParamLowerBounds = {2232327,66.0537,0.05007100000000107,1420692}; // S,D,L,M
		double[] GermanParamLowerBounds = {2232327,66.0537,0.04800100000000107,1420692}; // S,D,L,M
        double[] GermanParamUpperBounds = {2232327,92.8897,0.017,2451727.5}; //good for d=83 and above
        int predict_day = 90;
        System.out.println("Country:"+c.get(66).getName());
        double [] sCurveGerman = getSCurve(c.get(66).getAllCases(),predict_day,GermanParamLowerBounds,GermanParamUpperBounds);
        double[] doNothingGerman = getDoNothingCurve(c.get(66).infected, predict_day);  
        System.out.println("Predict future since 23/5/2021 for "+predict_day+" days using S-Curve and do nothing curve");
        System.out.println("S-Curve\t\t\t\t\t\tDo nothing Curve");   
        for(int i = 0 , j = 1 ; i < sCurveGerman.length & j < doNothingGerman.length ; i++ , j++)
        {
        	if(j==91) break;
        	System.out.print("SD future day"+(j)+"."+round(sCurveGerman[i], 7));
        	System.out.print("\t\t\tDoNothingCurve day"+(j)+"."+doNothingGerman[j]);
        	System.out.println();
        }
        
        double[] FrenchParamLowerBounds = {3260308,57.0575,0.056960000000000295,2719289};
		double[] FrenchParamUpperBounds = {3260308,72.9953,0.0224,3900255.5}; //good for d=83 and above
		System.out.println("Country:"+c.get(62).getName());
        double[] sCurveFrench = getSCurve(c.get(62).getAllCases(),predict_day,FrenchParamLowerBounds,FrenchParamUpperBounds);
		double[] doNothingFrance = getDoNothingCurve(c.get(62).infected, predict_day);  
		System.out.println("Predict future since 23/5/2021 for "+predict_day+" days using S-Curve and do nothing curve");
		System.out.println("S-Curve\t\t\t\t\t\tDo nothing Curve");   
        for(int i = 0 , j = 1 ; i < sCurveGerman.length & j < doNothingGerman.length ; i++ , j++)
        {
        	if(j==91) break;
        	System.out.print("SD future day"+(j)+"."+round(sCurveFrench[i], 7));
        	System.out.print("\t\t\tDoNothingCurve day"+(j)+"."+doNothingFrance[j]);
        	System.out.println();
        }
        
        double[] NethParamLowerBounds = {995300,62.9338,0.049209999999999954,654660}; 
		double[] NethParamUpperBounds = {995300,94.8074,0.0195,1133757}; //good for d=83 and above //L good for 0.018-0.022
		System.out.println("Country:"+c.get(119).getName());
		double[] sCurveNeth = getSCurve(c.get(119).getAllCases(),predict_day,NethParamLowerBounds,NethParamUpperBounds);
        double[] doNothingNeth = getDoNothingCurve(c.get(119).infected, predict_day); 
        System.out.println("Predict future since 23/5/2021 for "+predict_day+" days using S-Curve and do nothing curve");
        System.out.println("S-Curve\t\t\t\t\t\tDo nothing Curve");   
        for(int i = 0 , j = 1 ; i < sCurveGerman.length & j < doNothingGerman.length ; i++ , j++)
        {
        	if(j==91) break;
        	System.out.print("SD future day"+(j)+"."+round(sCurveNeth[i], 7));
        	System.out.print("\t\t\tDoNothingCurve day"+(j)+"."+doNothingNeth[j]);
        	System.out.println();
        }
    }
} 