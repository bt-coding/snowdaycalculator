package snowdaycalculator;

public class Calculate {
	PredictionData data;
	public Calculate(PredictionData d) {
		data = d;
	}
	public double snowDayChance() {
		if(data.getTempLow() > 276.483) {
			return 0; 
		}
		if(!data.isPrecipPresent()) {
			
		}
		return 0;
	}
	public static double convertFtoK(double t){
        return (t-273.15)*(9/5)+32;
    }
    public static double convertMPStoMPH(double ws){
        return ws* 2.237;
    }
    public static double calculateWindChill(double ws, double temp){
        return 35.74+(0.6215*temp)-(35.75*Math.pow(ws,0.16))+(0.4275*temp*Math.pow(ws,0.16));
    }
    public static void main(String[] args) {
    	System.out.println(calculateWindChill(40,-10));
    }

}
