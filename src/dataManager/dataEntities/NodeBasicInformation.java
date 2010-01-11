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
	private String languageType=null;
	private String developmentPlatform=null;
	private String developmentTechniques=null;
	private double teamSize=5;
	private int duration=180;
	private boolean isRoot=false;
	private String estType="none";
	
	
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
	public String getLanguageType() {
		return languageType;
	}
	public void setLanguageType(String languageType) {
		this.languageType = languageType;
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
	public boolean getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	public String getEstType() {
		return estType;
	}
	public void setEstType(String estType) {
		this.estType = estType;
	}
	
	
	public NodeBasicInformation(){
		
	}
	
}
