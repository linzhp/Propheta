package dataManager.dataEntities;

/**
 * 估算节点基本信息类，与数据表NodeBasicInfor表结构一致
 * @author Administrator
 *
 */
public class NodeBasicInformation {

	private int nodeID=-1;
	private int parentID=-1;
	private String name=null;
	private String description=null;
	private String businessArea=null;
	private int SLOC=-1;
	private int functionPoints=-1;
	private String developmentType=null;
	private String language=null;
	private String developmentPlatform=null;
	private String developmentTechniques=null;
	private double teamSize=-1;
	private int duration=-1;
	private double estEffort=-1;
	private double estPDR=-1;
	private double estProductivity=-1;
	private double estPM=-1;
	private int estPersons=-1;
	//private String estOthersInfo=null;
	private double cocomoEM=-1;
	private double cocomoSCED=-1;
	private boolean isRoot=false;
	
	
	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	public int getSLOC() {
		return SLOC;
	}
	public void setSLOC(int sLOC) {
		SLOC = sLOC;
	}
	public int getFunctionPoints() {
		return functionPoints;
	}
	public void setFunctionPoints(int functionPoints) {
		this.functionPoints = functionPoints;
	}
	public String getDevelopmentType() {
		return developmentType;
	}
	public void setDevelopmentType(String developmentType) {
		this.developmentType = developmentType;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDevelopmentPlatform() {
		return developmentPlatform;
	}
	public void setDevelopmentPlatform(String developmentPlatform) {
		this.developmentPlatform = developmentPlatform;
	}
	public String getDevelopmentTechniques() {
		return developmentTechniques;
	}
	public void setDevelopmentTechniques(String developmentTechniques) {
		this.developmentTechniques = developmentTechniques;
	}
	public double getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(double teamSize) {
		this.teamSize = teamSize;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public double getEstEffort() {
		return estEffort;
	}
	public void setEstEffort(double estEffort) {
		this.estEffort = estEffort;
	}
	public double getEstPDR() {
		return estPDR;
	}
	public void setEstPDR(double estPDR) {
		this.estPDR = estPDR;
	}
	public double getEstProductivity() {
		return estProductivity;
	}
	public void setEstProductivity(double estProductivity) {
		this.estProductivity = estProductivity;
	}
	public double getEstPM() {
		return estPM;
	}
	public void setEstPM(double estPM) {
		this.estPM = estPM;
	}
	public int getEstPersons() {
		return estPersons;
	}
	public void setEstPersons(int estPersons) {
		this.estPersons = estPersons;
	}
	public double getCocomoEM() {
		return cocomoEM;
	}
	public void setCocomoEM(double cocomoEM) {
		this.cocomoEM = cocomoEM;
	}
	public double getCocomoSCED() {
		return cocomoSCED;
	}
	public void setCocomoSCED(double cocomoSCED) {
		this.cocomoSCED = cocomoSCED;
	}
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	
	public NodeBasicInformation(){
		
	}
	
	
	
}
