package snowdaycalculator;
/**
 * This class contains all of the data corresponding with a prediction, such as weather alerts, temperature, and so on.
 * This class is to be used for packaging and transporting data between classes.
 * @author Tristan Lonsway(kryptikz)
 */
public class PredictionData {
	private double tempLow;
	private double tempHigh;
	private double tempNorm;
	private boolean precipPresent;
	private double precipAmount;
	private boolean alertPresent;
	private int alertSeverity;
	private boolean stormPresent;
	private String stormStartTime;
	private String stormEndTime;
	private double percentUnusual;
	private double snowDayChance;
	private double delayChance;
	private String zipcode;
	private double latitude;
	private double longitude;
	private String state;
	private String condition;
	private double windSpeed;
	private double humidity;
	private double plowEfficiency;
	private String county;
	private String reason;
	private String message;
	private double area;
	private double popDensity;
	private double statUnusual;
	public PredictionData(double tempLow, double tempHigh, double tempNorm, boolean precipPresent, double precipAmount,
			boolean alertPresent, int alertSeverity, boolean stormPresent, String stormStartTime, String stormEndTime,
			double percentUnusual, double snowDayChance, double delayChance, String zipcode, double latitude,
			double longitude, String state, String condition, double windSpeed, double humidity, double plowEfficiency,
			String county, String reason, String message, double area, double popDensity, double statUnusual) {
		super();
		this.tempLow = tempLow;
		this.tempHigh = tempHigh;
		this.tempNorm = tempNorm;
		this.precipPresent = precipPresent;
		this.precipAmount = precipAmount;
		this.alertPresent = alertPresent;
		this.alertSeverity = alertSeverity;
		this.stormPresent = stormPresent;
		this.stormStartTime = stormStartTime;
		this.stormEndTime = stormEndTime;
		this.percentUnusual = percentUnusual;
		this.snowDayChance = snowDayChance;
		this.delayChance = delayChance;
		this.zipcode = zipcode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.state = state;
		this.condition = condition;
		this.windSpeed = windSpeed;
		this.humidity = humidity;
		this.plowEfficiency = plowEfficiency;
		this.county = county;
		this.reason = reason;
		this.message = message;
		this.area = area;
		this.popDensity = popDensity;
		this.statUnusual = statUnusual;
	}
	public PredictionData() {
		super();
		this.tempLow = 0;
		this.tempHigh = 0;
		this.tempNorm = 0;
		this.precipPresent = false;
		this.precipAmount = 0;
		this.alertPresent = false;
		this.alertSeverity = 0;
		this.stormPresent = false;
		this.stormStartTime = "";
		this.stormEndTime = "";
		this.percentUnusual = 0;
		this.snowDayChance = 0;
		this.delayChance = 0;
		this.zipcode = "";
		this.latitude = 0;
		this.longitude = 0;
		this.state = "";
		this.condition = "";
		this.windSpeed = -1;
		this.humidity = -1;
		this.county = "";
		this.reason = "";
		this.message = "";
		this.area = 0;
		this.popDensity = 0;
		this.statUnusual = 0;
	}
	public double getTempLow() {
		return tempLow;
	}
	public void setTempLow(double tempLow) {
		this.tempLow = tempLow;
	}
	public double getTempHigh() {
		return tempHigh;
	}
	public void setTempHigh(double tempHigh) {
		this.tempHigh = tempHigh;
	}
	public double getTempNorm() {
		return tempNorm;
	}
	public void setTempNorm(double tempNorm) {
		this.tempNorm = tempNorm;
	}
	public boolean isPrecipPresent() {
		return precipPresent;
	}
	public void setPrecipPresent(boolean precipPresent) {
		this.precipPresent = precipPresent;
	}
	public double getPrecipAmount() {
		return precipAmount;
	}
	public void setPrecipAmount(double precipAmount) {
		this.precipAmount = precipAmount;
	}
	public boolean isAlertPresent() {
		return alertPresent;
	}
	public void setAlertPresent(boolean alertPresent) {
		this.alertPresent = alertPresent;
	}
	public int getAlertSeverity() {
		return alertSeverity;
	}
	public void setAlertSeverity(int alertSeverity) {
		this.alertSeverity = alertSeverity;
	}
	public boolean isStormPresent() {
		return stormPresent;
	}
	public void setStormPresent(boolean stormPresent) {
		this.stormPresent = stormPresent;
	}
	public String getStormStartTime() {
		return stormStartTime;
	}
	public void setStormStartTime(String stormStartTime) {
		this.stormStartTime = stormStartTime;
	}
	public String getStormEndTime() {
		return stormEndTime;
	}
	public void setStormEndTime(String stormEndTime) {
		this.stormEndTime = stormEndTime;
	}
	public double getPercentUnusual() {
		return percentUnusual;
	}
	public void setPercentUnusual(double percentUnusual) {
		this.percentUnusual = percentUnusual;
	}
	public double getSnowDayChance() {
		return snowDayChance;
	}
	public void setSnowDayChance(double snowDayChance) {
		this.snowDayChance = snowDayChance;
	}
	public double getDelayChance() {
		return delayChance;
	}
	public void setDelayChance(double delayChance) {
		this.delayChance = delayChance;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getPlowEfficiency() {
		return plowEfficiency;
	}
	public void setPlowEfficiency(double plowEfficiency) {
		this.plowEfficiency = plowEfficiency;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getPopDensity() {
		return popDensity;
	}
	public void setPopDensity(double popDensity) {
		this.popDensity = popDensity;
	}
	public double getStatUnusual() {
		return statUnusual;
	}
	public void setStatUnusual(double statUnusual) {
		this.statUnusual = statUnusual;
	}
	@Override
	public String toString() {
		return "PredictionData [tempLow=" + tempLow + ", tempHigh=" + tempHigh + ", tempNorm=" + tempNorm
				+ ", precipPresent=" + precipPresent + ", precipAmount=" + precipAmount + ", alertPresent="
				+ alertPresent + ", alertSeverity=" + alertSeverity + ", stormPresent=" + stormPresent
				+ ", stormStartTime=" + stormStartTime + ", stormEndTime=" + stormEndTime + ", percentUnusual="
				+ percentUnusual + ", snowDayChance=" + snowDayChance + ", delayChance=" + delayChance + ", zipcode="
				+ zipcode + ", latitude=" + latitude + ", longitude=" + longitude + ", state=" + state + ", condition="
				+ condition + ", windSpeed=" + windSpeed + ", humidity=" + humidity + ", plowEfficiency="
				+ plowEfficiency + ", county=" + county + ", reason=" + reason + ", message=" + message + ", area="
				+ area + ", popDensity=" + popDensity + ", statUnusual=" + statUnusual + "]";
	}
}
