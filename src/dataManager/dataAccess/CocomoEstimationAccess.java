package dataManager.dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

import dataManager.dataEntities.CocomoEstimationRecord;
import dataManager.dataEntities.NodeBasicInformation;

/**
 * 数据访问类，用于cocomoEstimation表的存取操作
 * @author Administrator
 *
 */
public class CocomoEstimationAccess extends DataBaseAccess{

	
	public CocomoEstimationAccess(){
		
	}
	
	public CocomoEstimationAccess(String dataBasePath){
		super(dataBasePath);
	}
	
	
	public CocomoEstimationRecord getCocomoEstimationByNodeID(int nodeID){
		try {
			String sqlString="select [estimationID],[nodeID],[EMType],[sumSF],[productEM],[SCEDValue]," +
					"[PM],[devTime],[PREC],[FLEX],[RESL],[TEAM],[PMAT],[RELY],[DATA],[CPLX],[RUSE],[DOCU]," +
					"[TIME],[STOR],[PVOL],[ACAP],[PCAP],[PCON],[APEX],[PLEX],[LTEX],[TOOL],[SITE],[SCED]," +
					"[RCPX],[PDIF],[PERS],[PREX],[FCIL] from cocomoEstimation where [nodeID]="+nodeID;
			ResultSet rs=statement.executeQuery(sqlString);
			if(rs.next()){
				CocomoEstimationRecord record=new CocomoEstimationRecord();
				record.setEstimationID(rs.getInt("estimationID"));
				record.setNodeID(rs.getInt("nodeID"));
				record.setEMType(rs.getString("EMType"));
				record.setSumSF(rs.getDouble("sumSF"));
				record.setProductEM(rs.getDouble("productEM"));
				record.setSCEDValue(rs.getDouble("SCEDValue"));
				record.setPM(rs.getDouble("PM"));
				record.setDevTime(rs.getDouble("devTime"));
				record.setPREC(rs.getString("PREC"));
				record.setFLEX(rs.getString("FLEX"));
				record.setRESL(rs.getString("RESL"));
				record.setTEAM(rs.getString("TEAM"));
				record.setPMAT(rs.getString("PMAT"));
				record.setRELY(rs.getString("RELY"));
				record.setDATA(rs.getString("DATA"));
				record.setCPLX(rs.getString("CPLX"));
				record.setRUSE(rs.getString("RUSE"));
				record.setDOCU(rs.getString("DOCU"));
				record.setTIME(rs.getString("TIME"));
				record.setSTOR(rs.getString("STOR"));
				record.setPVOL(rs.getString("PVOL"));
				record.setACAP(rs.getString("ACAP"));
				record.setPCAP(rs.getString("PCAP"));
				record.setPCON(rs.getString("PCON"));
				record.setAPEX(rs.getString("APEX"));
				record.setPLEX(rs.getString("PLEX"));
				record.setLTEX(rs.getString("LTEX"));
				record.setTOOL(rs.getString("TOOL"));
				record.setSITE(rs.getString("SITE"));
				record.setSCED(rs.getString("SCED"));
				record.setRCPX(rs.getString("RCPX"));
				record.setPDIF(rs.getString("PDIF"));
				record.setPERS(rs.getString("PERS"));
				record.setPREX(rs.getString("PREX"));
				record.setFCIL(rs.getString("FCIL"));
				
				return record;
			}else{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}	
		return null;
	}
}
