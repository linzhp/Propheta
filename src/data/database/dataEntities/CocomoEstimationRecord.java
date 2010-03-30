package data.database.dataEntities;

import java.util.HashMap;

import data.database.dataAccess.CocomoEstimationAccess;

/**
 * cocomo估算记录，用于存储cocomo估算的输入输出
 * 
 * @author Administrator
 * 
 */
public class CocomoEstimationRecord extends Entity{

	/**
	 * 构造器
	 */
	public CocomoEstimationRecord() {
		//这三个值在校准时作为默认值使用
		this.set("sumSF", 18.97);
		this.set("productEM", 1);
		this.set("SCED", "N");
	}

	// 更新某条cocomoEstimation数据
	public static void saveCocomoEstimation(int nodeID, String EMType,
			Double sumSF, Double productEM, Double SCEDValue, Double PM,
			Double devTime, HashMap<String, String> factorsSF,
			HashMap<String, String> factorsEM) {
		CocomoEstimationAccess cer_access = new CocomoEstimationAccess();
		CocomoEstimationRecord cer = cer_access
				.getCocomoEstimationByNodeID(nodeID);

		cer.set("EMType",EMType);
		cer.set("sumSF",sumSF);
		cer.set("productEM",productEM);
		cer.set("SCEDValue",SCEDValue);
		cer.set("PM",PM);
		cer.set("devTime",devTime);
		// 设置SF因子
		cer.attributes.putAll(factorsSF);
		// 设置EM因子
		cer.attributes.putAll(factorsEM);

		cer_access.update(cer);
	}

}
