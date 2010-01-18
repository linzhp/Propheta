package dataManager.dataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataManager.dataEntities.CocomoEstimationRecord;

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
	
	
	/**
	 * 通过节点ID获取cocomo估算记录
	 * @param nodeID 节点ID
	 * @return
	 */
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
	
	
	/**
	 * 通过记录ID获取cocomo估算记录
	 * @param recordID 记录ID
	 * @return
	 */
	public CocomoEstimationRecord getCocomoEstimationByEstimationID(int estimationID){
		try {
			String sqlString="select [estimationID],[nodeID],[EMType],[sumSF],[productEM],[SCEDValue]," +
					"[PM],[devTime],[PREC],[FLEX],[RESL],[TEAM],[PMAT],[RELY],[DATA],[CPLX],[RUSE],[DOCU]," +
					"[TIME],[STOR],[PVOL],[ACAP],[PCAP],[PCON],[APEX],[PLEX],[LTEX],[TOOL],[SITE],[SCED]," +
					"[RCPX],[PDIF],[PERS],[PREX],[FCIL] from cocomoEstimation where [estimationID]="+estimationID;
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
	
	
	/**
	 * 插入一条新的cocomo估算记录，并返回分配的记录ID号
	 * @param record
	 * @return
	 */
	public int insertCocomoEstimation(CocomoEstimationRecord record){
		try{
			String sqlString="insert into cocomoEstimation ([nodeID],[EMType],[sumSF],[productEM],[SCEDValue]," +
					"[PM],[devTime],[PREC],[FLEX],[RESL],[TEAM],[PMAT],[RELY],[DATA],[CPLX],[RUSE],[DOCU]," +
					"[TIME],[STOR],[PVOL],[ACAP],[PCAP],[PCON],[APEX],[PLEX],[LTEX],[TOOL],[SITE],[SCED]," +
					"[RCPX],[PDIF],[PERS],[PREX],[FCIL]) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
					"?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preStatement=connection.prepareStatement(sqlString);
			preStatement.setInt(1, record.getNodeID());
			preStatement.setString(2, record.getEMType());
			preStatement.setDouble(3, record.getSumSF());
			preStatement.setDouble(4, record.getProductEM());
			preStatement.setDouble(5, record.getSCEDValue());
			preStatement.setDouble(6, record.getPM());
			preStatement.setDouble(7, record.getDevTime());
			preStatement.setString(8, record.getPREC());
			preStatement.setString(9, record.getFLEX());
			preStatement.setString(10, record.getRESL());
			preStatement.setString(11, record.getTEAM());
			preStatement.setString(12, record.getPMAT());
			preStatement.setString(13, record.getRELY());
			preStatement.setString(14, record.getDATA());
			preStatement.setString(15, record.getCPLX());
			preStatement.setString(16, record.getRUSE());
			preStatement.setString(17, record.getDOCU());
			preStatement.setString(18, record.getTIME());
			preStatement.setString(19, record.getSTOR());
			preStatement.setString(20, record.getPVOL());
			preStatement.setString(21, record.getACAP());
			preStatement.setString(22, record.getPCAP());
			preStatement.setString(23, record.getPCON());
			preStatement.setString(24, record.getAPEX());
			preStatement.setString(25, record.getPLEX());
			preStatement.setString(26, record.getLTEX());
			preStatement.setString(27, record.getTOOL());
			preStatement.setString(28, record.getSITE());
			preStatement.setString(29, record.getSCED());
			preStatement.setString(30, record.getRCPX());
			preStatement.setString(31, record.getPDIF());
			preStatement.setString(32, record.getPERS());
			preStatement.setString(33, record.getPREX());
			preStatement.setString(34, record.getFCIL());
			
			preStatement.execute();
			ResultSet rs=preStatement.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/**
	 * 更新cocomo估算记录
	 * @param record
	 */
	public void updateCocomoEstimation(CocomoEstimationRecord record){
		try{
			String sqlString="update cocomoEstimation set [nodeID]=?,[EMType]=?,[sumSF]=?,[productEM]=?,[SCEDValue]=?," +
					"[PM]=?,[devTime]=?,[PREC]=?,[FLEX]=?,[RESL]=?,[TEAM]=?,[PMAT]=?,[RELY]=?,[DATA]=?,[CPLX]=?," +
					"[RUSE]=?,[DOCU]=?,[TIME]=?,[STOR]=?,[PVOL]=?,[ACAP]=?,[PCAP]=?,[PCON]=?,[APEX]=?,[PLEX]=?,[LTEX]=?," +
					"[TOOL]=?,[SITE]=?,[SCED]=?,[RCPX]=?,[PDIF]=?,[PERS]=?,[PREX]=?,[FCIL]=? where [estimationID]="+record.getEstimationID();
			PreparedStatement preStatement=connection.prepareStatement(sqlString);
			preStatement.setInt(1, record.getNodeID());
			preStatement.setString(2, record.getEMType());
			preStatement.setDouble(3, record.getSumSF());
			preStatement.setDouble(4, record.getProductEM());
			preStatement.setDouble(5, record.getSCEDValue());
			preStatement.setDouble(6, record.getPM());
			preStatement.setDouble(7, record.getDevTime());
			preStatement.setString(8, record.getPREC());
			preStatement.setString(9, record.getFLEX());
			preStatement.setString(10, record.getRESL());
			preStatement.setString(11, record.getTEAM());
			preStatement.setString(12, record.getPMAT());
			preStatement.setString(13, record.getRELY());
			preStatement.setString(14, record.getDATA());
			preStatement.setString(15, record.getCPLX());
			preStatement.setString(16, record.getRUSE());
			preStatement.setString(17, record.getDOCU());
			preStatement.setString(18, record.getTIME());
			preStatement.setString(19, record.getSTOR());
			preStatement.setString(20, record.getPVOL());
			preStatement.setString(21, record.getACAP());
			preStatement.setString(22, record.getPCAP());
			preStatement.setString(23, record.getPCON());
			preStatement.setString(24, record.getAPEX());
			preStatement.setString(25, record.getPLEX());
			preStatement.setString(26, record.getLTEX());
			preStatement.setString(27, record.getTOOL());
			preStatement.setString(28, record.getSITE());
			preStatement.setString(29, record.getSCED());
			preStatement.setString(30, record.getRCPX());
			preStatement.setString(31, record.getPDIF());
			preStatement.setString(32, record.getPERS());
			preStatement.setString(33, record.getPREX());
			preStatement.setString(34, record.getFCIL());
			
			preStatement.execute();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void deleteCocomoEstimationByEstimationID(int estimationID){
		try{
			String sqlString="delete from cocomoEstimation where [estimationID]="+estimationID;
			statement.execute(sqlString);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public void deleteCocomoEstimationByNodeID(int nodeID){
		try{
			String sqlString="delete from cocomoEstimation where [nodeID]="+nodeID;
			statement.execute(sqlString);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
