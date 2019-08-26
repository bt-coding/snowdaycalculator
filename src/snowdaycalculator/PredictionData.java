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
	private int zipcode;
	
	public PredictionData(double tempLow, double tempHigh, double tempNorm, boolean precipPresent, double precipAmount,
			boolean alertPresent, int alertSeverity, boolean stormPresent, String stormStartTime, String stormEndTime,
			double percentUnusual, double snowDayChance, double delayChance, int zipcode) {
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
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}
}
