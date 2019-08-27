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
	public double convertFtoK(double t){
        return (t-273.15)*(9/5)+32;
    }
    public double convertMPStoMPH(double ws){
        return ws* 2.237;
    }
    public double calculateWindChill(double ws, double temp){
        return 35.74+(0.6215*temp)-(35.75*Math.pow(ws,0.16))+(0.4275*Math.pow(ws,0.16));
    }

}
