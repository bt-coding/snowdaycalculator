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
}
