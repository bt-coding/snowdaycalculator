package snowdaycalculator;

import java.util.*;
import java.time.*;

public class Calculate {
	PredictionData data;
	public Calculate(PredictionData d) {
		data = d;
	}
	public void snowDayChance() {
		double snowdayChance = 0;
		if(convertKtoF(data.getTempLow()) > 38) {
			snowdayChance = 0;
		}
		else if(calculateWindChill(convertMPStoMPH(data.getWindSpeed()),convertKtoF(data.getTempHigh())) <= -30) {
			snowdayChance += 100;
		}
		else if(data.getCondition().toLowerCase().contains("snow")){
			snowdayChance += 100*(data.getPrecipAmount()/914.4)*(2-data.getPlowEfficiency());
		}
		if(snowdayChance > 100) {
			snowdayChance = 100;
		}
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		String[] ret = (dataIrregularityChance(data.getPrecipAmount(),Weather.getDataPoints(data.getLongitude(), data.getLatitude(), data.getState(), month),data));
		snowdayChance = Double.parseDouble(ret[0]);		
		data.setSnowDayChance(snowdayChance);
		data.setReason(ret[1]);
		data.setStatUnusual(Double.parseDouble(ret[2]));
	}
	public void setSnowdayChance(double chance) {
		data.setSnowDayChance(chance);
	}
	public static boolean willSnowStick(double rh, double temp) {
		//requires celcius
		if(9.5*Math.pow(Math.E,((-17.27*temp)/(temp+238.3)))*(10.5-temp) >= rh){
			return true;
		}
		return false;
	}
	public static double convertKtoF(double t){
        return (t-273.15)*(9/5)+32;
    }
    public static double convertMPStoMPH(double ws){
        return ws* 2.237;
    }
    public static double calculateWindChill(double ws, double temp){
    	//requires fahrenheit
        return 35.74+(0.6215*temp)-(35.75*Math.pow(ws,0.16))+(0.4275*temp*Math.pow(ws,0.16));
    }
    public static void main(String[] args) {
    	//System.out.println(calculateWindChill(40,-10));
    	System.out.println(willSnowStick(60,6));
    }
    public static double convertKtoC(double temp) {
    	return temp-273.15;
    }
    public static double getChancefromWindChill(double wc) {
    	double calc = (-.005*wc*wc*wc)/100;
    	if (calc > 0) {
    		return calc;
    	}
    	return 0;
    }
    public static double convertMMtoInches(double mm) {
    	return (mm/25.4);
    }
    public static String[] dataIrregularityChance(double point, double[] datapoints, PredictionData dat) {
    	String reason = "Generic Reason";
    	
    	//point = convertMMtoInches(point);
    	
    	double total=0;
    	for(double d : datapoints) {
    		total+=d;
    	}
    	double ave = total/(double)datapoints.length;
        double totsquare = 0;
        for(double d : datapoints) {
        	totsquare+=((d-ave)*(d-ave));
        }
        totsquare/=(double)datapoints.length;
        double stdev = Math.sqrt(totsquare);
    	double chance = 0;
        if (stdev!=0) {
    		chance = (point/stdev);
    		reason = "Statistical commonality of this much snow is " + (point/stdev) +".";
    	} else {
    		//chance = 8*(point/.000000001);
    		chance = (.25*point);
    		reason = "Very little snow usually occurs in this region at this time. Therefore a general approximation is applied due to lack of data";
    	}
        if (!willSnowStick(dat.getHumidity(),convertKtoC(dat.getTempLow()))) {
        	chance/=2;
        	reason = reason + " Chance Decreased because snow is unlikely to stick to the ground";
        }
        double wcchance = getChancefromWindChill(calculateWindChill(dat.getWindSpeed(),convertKtoF(dat.getTempLow())));
        chance+=wcchance;
        System.out.println(wcchance);
        if (wcchance > .3) {
        	reason = reason + " The Wind Chill is increasing this prediction.";
        }
        if (convertKtoF(dat.getTempHigh())>38) {
        	chance=0;
        	reason = "The temperature is too high for a chance of a snow day";
        }
        
        //END CALCULATIONS HERE
        if (chance > 1) {
        	chance = 1;
        } else if (chance < 0) {
        	chance=0;
        }
        
        
    	return new String[] {chance+"",reason,(point/stdev)+""};
    }
}
