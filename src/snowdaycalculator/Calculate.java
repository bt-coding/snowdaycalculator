package snowdaycalculator;

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
		else if(data.getCondition().contains("snow")){
			snowdayChance += data.getPrecipAmount()/1219.2;
		}
		if(snowdayChance > 100) {
			snowdayChance = 100;
		}
		setSnowdayChance(snowdayChance);
	}
	public void setSnowdayChance(double chance) {
		data.setSnowDayChance(chance);
	}
	public static boolean willSnowStick(double rh, double temp) {
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
        return 35.74+(0.6215*temp)-(35.75*Math.pow(ws,0.16))+(0.4275*temp*Math.pow(ws,0.16));
    }
    public static void main(String[] args) {
    	//System.out.println(calculateWindChill(40,-10));
    	System.out.println(willSnowStick(60,6));
    }
    public static double convertKtoC(double temp) {
    	return temp-273.15;
    }

}
