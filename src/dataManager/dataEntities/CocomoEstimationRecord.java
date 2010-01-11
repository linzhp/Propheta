package dataManager.dataEntities;

/**
 * cocomo估算记录，用于存储cocomo估算的输入输出
 * @author Administrator
 *
 */
public class CocomoEstimationRecord {

	private int estimationID=-1;
	private int nodeID=-1;
	private String EMType=null;
	private double sumSF=-1;
	private double productEM=-1;
	private double SCEDValue=-1;
	private double PM=-1;
	private double devTime=-1;
	private String PREC=null;
	private String FLEX=null;
	private String RESL=null;
	private String TEAM=null;
	private String PMAT=null;
	private String RELY=null;
	private String DATA=null;
	private String CPLX=null;
	private String RUSE=null;
	private String DOCU=null;
	private String TIME=null;
	private String STOR=null;
	private String PVOL=null;
	private String ACAP=null;
	private String PCAP=null;
	private String PCON=null;
	private String APEX=null;
	private String PLEX=null;
	private String LTEX=null;
	private String TOOL=null;
	private String SITE=null;
	private String SCED=null;
	private String RCPX=null;
	private String PDIF=null;
	private String PERS=null;
	private String PREX=null;
	private String FCIL=null;
	
	
	public int getEstimationID() {
		return estimationID;
	}
	public void setEstimationID(int estimationID) {
		this.estimationID = estimationID;
	}
	public int getNodeID() {
		return nodeID;
	}
	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}
	public String getEMType() {
		return EMType;
	}
	public void setEMType(String eMType) {
		EMType = eMType;
	}
	public double getSumSF() {
		return sumSF;
	}
	public void setSumSF(double sumSF) {
		this.sumSF = sumSF;
	}
	public double getProductEM() {
		return productEM;
	}
	public void setProductEM(double productEM) {
		this.productEM = productEM;
	}
	public double getSCEDValue() {
		return SCEDValue;
	}
	public void setSCEDValue(double sCEDValue) {
		SCEDValue = sCEDValue;
	}
	public double getPM() {
		return PM;
	}
	public void setPM(double pM) {
		PM = pM;
	}
	public double getDevTime() {
		return devTime;
	}
	public void setDevTime(double devTime) {
		this.devTime = devTime;
	}
	public String getPREC() {
		return PREC;
	}
	public void setPREC(String pREC) {
		PREC = pREC;
	}
	public String getFLEX() {
		return FLEX;
	}
	public void setFLEX(String fLEX) {
		FLEX = fLEX;
	}
	public String getRESL() {
		return RESL;
	}
	public void setRESL(String rESL) {
		RESL = rESL;
	}
	public String getTEAM() {
		return TEAM;
	}
	public void setTEAM(String tEAM) {
		TEAM = tEAM;
	}
	public String getPMAT() {
		return PMAT;
	}
	public void setPMAT(String pMAT) {
		PMAT = pMAT;
	}
	public String getRELY() {
		return RELY;
	}
	public void setRELY(String rELY) {
		RELY = rELY;
	}
	public String getDATA() {
		return DATA;
	}
	public void setDATA(String dATA) {
		DATA = dATA;
	}
	public String getCPLX() {
		return CPLX;
	}
	public void setCPLX(String cPLX) {
		CPLX = cPLX;
	}
	public String getRUSE() {
		return RUSE;
	}
	public void setRUSE(String rUSE) {
		RUSE = rUSE;
	}
	public String getDOCU() {
		return DOCU;
	}
	public void setDOCU(String dOCU) {
		DOCU = dOCU;
	}
	public String getTIME() {
		return TIME;
	}
	public void setTIME(String tIME) {
		TIME = tIME;
	}
	public String getSTOR() {
		return STOR;
	}
	public void setSTOR(String sTOR) {
		STOR = sTOR;
	}
	public String getPVOL() {
		return PVOL;
	}
	public void setPVOL(String pVOL) {
		PVOL = pVOL;
	}
	public String getACAP() {
		return ACAP;
	}
	public void setACAP(String aCAP) {
		ACAP = aCAP;
	}
	public String getPCAP() {
		return PCAP;
	}
	public void setPCAP(String pCAP) {
		PCAP = pCAP;
	}
	public String getPCON() {
		return PCON;
	}
	public void setPCON(String pCON) {
		PCON = pCON;
	}
	public String getAPEX() {
		return APEX;
	}
	public void setAPEX(String aPEX) {
		APEX = aPEX;
	}
	public String getPLEX() {
		return PLEX;
	}
	public void setPLEX(String pLEX) {
		PLEX = pLEX;
	}
	public String getLTEX() {
		return LTEX;
	}
	public void setLTEX(String lTEX) {
		LTEX = lTEX;
	}
	public String getTOOL() {
		return TOOL;
	}
	public void setTOOL(String tOOL) {
		TOOL = tOOL;
	}
	public String getSITE() {
		return SITE;
	}
	public void setSITE(String sITE) {
		SITE = sITE;
	}
	public String getSCED() {
		return SCED;
	}
	public void setSCED(String sCED) {
		SCED = sCED;
	}
	public String getRCPX() {
		return RCPX;
	}
	public void setRCPX(String rCPX) {
		RCPX = rCPX;
	}
	public String getPDIF() {
		return PDIF;
	}
	public void setPDIF(String pDIF) {
		PDIF = pDIF;
	}
	public String getPERS() {
		return PERS;
	}
	public void setPERS(String pERS) {
		PERS = pERS;
	}
	public String getPREX() {
		return PREX;
	}
	public void setPREX(String pREX) {
		PREX = pREX;
	}
	public String getFCIL() {
		return FCIL;
	}
	public void setFCIL(String fCIL) {
		FCIL = fCIL;
	}
	
	
	/**
	 * 构造器
	 */
	public CocomoEstimationRecord(){
		
	}
	
	
	/**
	 * 构造器
	 * @param estimationID
	 * @param nodeID
	 */
    public CocomoEstimationRecord(int estimationID, int nodeID){
		this.estimationID=estimationID;
		this.nodeID=nodeID;
	}
}
