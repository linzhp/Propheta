package estimation;

import java.util.HashMap;
import java.util.Set;

import data.database.dataAccess.CocomoEstimationAccess;
import data.database.dataEntities.CocomoEstimationRecord;
import data.file.PropertyFile;


public class COCOMO {

	static Double A = Double.valueOf(PropertyFile.readValue(
			"properties/COCOMO.properties", "A"));
	static Double B = Double.valueOf(PropertyFile.readValue(
			"properties/COCOMO.properties", "B"));
	static Double C = Double.valueOf(PropertyFile.readValue(
			"properties/COCOMO.properties", "C"));
	static Double D = Double.valueOf(PropertyFile.readValue(
			"properties/COCOMO.properties", "D"));
	
	// 重用代码量计算公式
	public static int getReuseSize(Double SLOC, Double AT, Double DM,
			Double CM, Double IM, Double AA, Double SU, Double UNFM) {
		Double size, AAF, AAM;
		AAF = (0.4 * DM) + (0.3 * CM) + (0.3 * IM);
		if (AAF <= 50)
			AAM = (AA + AAF * (1 + (0.02 * SU * UNFM))) / 100;
		else
			AAM = (AA + AAF + (SU * UNFM)) / 100;
		size = SLOC * (1 - (AT / 100)) * AAM;
		return size.intValue();
	}

	// 维护代码量计算公式
	public static int getMaintainSize(Double addSLOC, Double modifySLOC,
			Double SU, Double UNFM) {
		Double size = (addSLOC + modifySLOC) * (1 + ((SU / 100) * UNFM));
		return size.intValue();
	}

	// 模块工作量计算公式，输入参数为cocomo原始输入值
	public static Double[] getModuleEffortTime(Double size,
			HashMap<String, String> factorsSF, HashMap<String, String> factorsEM) {
		// 求各SF因子的和
		Double sumSF = getSumSF(factorsSF);
		// 求各EM因子的乘积
		Double productEM = getProductEM(factorsEM);
		// 求effort
		Double SCED = getSCEDValue(factorsEM.get("SCED"));
		return getModuleEffortTime(size, sumSF, productEM, SCED);
	}
	
	// 模块工作量计算公式,输入参数为cocomo运算的中间值
	public static Double[] getModuleEffortTime(Double size, Double sumSF, Double productEM, Double SCED)
	{
		// 求effort
		Double E = getE(sumSF);
		Double PM = A * Math.pow((size / 1000), E) * productEM * SCED;
		// 求TDEV: Time to development
		Double TDEV = C * Math.pow((PM / SCED), (D + 0.2 * (E - B))) * SCED;
		Double[] effort = { PM, TDEV };
		
		System.out.println("sumSF = " + sumSF);
		System.out.println("productEM = " + productEM);
		System.out.println("SCED = " + SCED);
		System.out.println("E= " + E);
		System.out.println("PM = " + PM);
		System.out.println("TDEV = " + TDEV);
		
		return effort;
	}

	//一级集成工作量计算公式，输入参数为cocomo原始输入值
	public static Double[] getIntegratedEffortTime(Double[] sizes, Double[] productEMs,
			HashMap<String, String> factorsSF, String SCEDLevel) {
		// 求各SF因子的和
		Double sumSF = getSumSF(factorsSF);
		// 求effort
		Double SCED = getSCEDValue(SCEDLevel);
		return getIntegratedEffortTime(sizes, productEMs, sumSF, SCED);
	}
	
	//一级集成工作量计算公式，输入参数为cocomo中间值
	public static Double[] getIntegratedEffortTime(Double[] sizes, Double[] productEMs,
			Double sumSF, Double SCED) {
		// 求effort
		Double E = getE(sumSF);
		Double sumSize = 0.0;
		for(Double size: sizes)
			sumSize += size;
		Double PMBasic = A * Math.pow((sumSize / 1000), E) * SCED;
		Double PM = 0.0;
		for(int i=0; i<sizes.length; i++)
			PM += PMBasic * (sizes[i]/sumSize)* productEMs[i];
		// 求TDEV: Time to development
		Double TDEV = C * Math.pow((PM/SCED), (D + 0.2 * (E - B))) * SCED;
		
		Double[] effort = { PM, TDEV };
		
		System.out.println("sumSF = " + sumSF);
		System.out.println("SCED = " + SCED);
		System.out.println("PM = " + PM);
		System.out.println("TDEV = " + TDEV);
		
		return effort;
	}
	//求各SF因子的和
	public static Double getSumSF(HashMap<String, String> factorsSF)
	{
		Double sumSF = 0.0;
		String propertyKey;
		Set<String> factors = factorsSF.keySet();
		for (String factor : factors) {
			
			propertyKey = "SF." + factor + "." + factorsSF.get(factor);
			sumSF += Double.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", propertyKey));
		}
		return sumSF;
	}
	// 求各EM因子的乘积 不包括SCED
	public static Double getProductEM(HashMap<String, String> factorsEM)
	{
		Double productEM = 1.0;
		String propertyKey;
		Set<String> factors = factorsEM.keySet();
		for (Object factor : factors) {
			propertyKey = "EM." + factor.toString() + "."
					+ factorsEM.get(factor);
			productEM *= Double.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", propertyKey));
		}
		Double SCED = getSCEDValue(factorsEM.get("SCED"));
		return productEM/SCED;
	}
	
	public static Double getSCEDValue(String SCEDLevel)
	{
		String propertyKey = "EM.SCED." + SCEDLevel;
		Double SCED = Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", propertyKey));
		return SCED;
	}
	
	public static Double getE(Double sumSF)
	{
		return B + 0.01 * sumSF;
	}
	
	//求阶段工作量分布
	public static Double[] getPhaseEfforts(String[] phases, String sizeLevel, String ELevel, Double PM)
	{
		Double[] efforts = new Double[phases.length];
		String propertyKey;
		for(int i=0; i<phases.length; i++)
		{
			propertyKey = "activity." + phases[i] + ".E." + ELevel + "." + sizeLevel;
			System.out.println(propertyKey);
			efforts[i] = PM * Double.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", propertyKey)) / 100;
		}
		return efforts;
	}
	//求各阶段的活动工作量分布
	public static Double[] getActivityEfforts(String phase, String[] activities, String sizeLevel, String ELevel, Double PM, Double phaseEffort)
	{
		Double[] efforts = new Double[activities.length];
		String propertyKey;
		for(int i=0; i<activities.length; i++)
		{
			propertyKey = "activity." + phase + ".E." + ELevel + "." + activities[i] + "." + sizeLevel;
			System.out.println(propertyKey);
			efforts[i] = phaseEffort * Double.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", propertyKey)) / 100;
		}
		return efforts;
	}
	//获取size的级别：S、I、M、L、VL
	public static String getSizeLevel(int size){
		String[] levels = {"S", "I", "M", "L", "VL"};
		int sizeLevel;
		int abs;
		int absMin = Math.abs(size - Integer.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", ("size." + levels[0]))));
		String resultLevel = levels[0];
		for(String level: levels)
		{
			sizeLevel = Integer.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", ("size." + level)));
			abs = Math.abs(size - sizeLevel);
			resultLevel = (absMin<abs)?resultLevel: level; 
			absMin = Math.min(abs, absMin);
		}
		return resultLevel;
	}
	//获取E的级别：S、M、L
	public static String getELevel(Double E)
	{
		String[] levels = {"S", "M", "L"};
		Double ELevel;
		Double abs;
		Double absMin =Math.abs(E - Double.valueOf(PropertyFile.readValue(
				"properties/COCOMO.properties", ("E." + levels[0]))));
		String resultLevel = levels[0];
		for(String level: levels)
		{
			ELevel = Double.valueOf(PropertyFile.readValue(
					"properties/COCOMO.properties", ("E." + level)));
			abs = Math.abs(E - ELevel);
			resultLevel = (absMin<abs)?resultLevel: level; 
			absMin = Math.min(abs, absMin);
		}
		return resultLevel;
	}
	
}
