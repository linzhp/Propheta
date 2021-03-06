package estimation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import data.database.dataAccess.ConstantsAccess;
import data.file.COCOMOProperties;

public class COCOMO {

	private static ConstantsAccess constants = new ConstantsAccess();
	
	public static Double getA() throws SQLException{
		return (Double)constants.get("A");
	}

	public static Double getB() throws SQLException{
		return (Double)constants.get("B");
	}

	public static Double getC() throws SQLException{
		return (Double)constants.get("C");
	}

	public static Double getD() throws SQLException{
		return (Double)constants.get("D");
	}

	private final static HashMap<String, Double> relativeSCED = new HashMap<String, Double>();
	static {
		relativeSCED.put("XL", 0.75);
		relativeSCED.put("VL", 0.75);
		relativeSCED.put("L", 0.85);
		relativeSCED.put("N", 1.0);
		relativeSCED.put("H", 1.3);
		relativeSCED.put("VH", 1.6);
		relativeSCED.put("XH", 1.6);
	}

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
			HashMap<String, String> factorsSF, HashMap<String, String> factorsEM) throws SQLException {
		// 求各SF因子的和
		Double sumSF = getSumSF(factorsSF);
		// 求各EM因子的乘积
		Double productEM = getProductEM(factorsEM);
		// 求effort
		Double SCED = getSCEDValue(factorsEM.get("SCED"));
		return getModuleEffortTime(size, sumSF, productEM, SCED);
	}

	// 模块工作量计算公式,输入参数为cocomo运算的中间值
	public static Double[] getModuleEffortTime(Double size, Double sumSF,
			Double productEM, Double SCED) throws SQLException {
		// 求effort
		Double E = getE(sumSF);
		Double PM = getA() * Math.pow((size / 1000), E) * productEM * SCED;
		// 求TDEV: Time to development
		Double TDEV = getC() * Math.pow((PM / SCED), (getD() + 0.2 * (E - getB()))) * SCED;
		Double[] effort = { PM, TDEV };

		System.out.println("sumSF = " + sumSF);
		System.out.println("productEM = " + productEM);
		System.out.println("SCED = " + SCED);
		System.out.println("E= " + E);
		System.out.println("PM = " + PM);
		System.out.println("TDEV = " + TDEV);

		return effort;
	}

	// 一级集成工作量计算公式，输入参数为cocomo原始输入值
	public static Double[] getIntegratedEffortTime(Double[] sizes,
			Double[] productEMs, HashMap<String, String> factorsSF,
			String SCEDLevel) throws SQLException {
		// 求各SF因子的和
		Double sumSF = getSumSF(factorsSF);
		// 求effort
		Double SCED = getSCEDValue(SCEDLevel);
		return getIntegratedEffortTime(sizes, productEMs, sumSF, SCED);
	}

	// 一级集成工作量计算公式，输入参数为cocomo中间值
	public static Double[] getIntegratedEffortTime(Double[] sizes,
			Double[] productEMs, Double sumSF, Double SCED) throws SQLException {
		// 求effort
		Double E = getE(sumSF);
		Double sumSize = 0.0;
		for (Double size : sizes)
			sumSize += size;
		Double PMBasic = getA() * Math.pow((sumSize / 1000), E) * SCED;
		Double PM = 0.0;
		for (int i = 0; i < sizes.length; i++)
			PM += PMBasic * (sizes[i] / sumSize) * productEMs[i];
		// 求TDEV: Time to development
		Double TDEV = getC() * Math.pow((PM / SCED), (getD() + 0.2 * (E - getB()))) * SCED;

		Double[] effort = { PM, TDEV };

		System.out.println("sumSF = " + sumSF);
		System.out.println("SCED = " + SCED);
		System.out.println("PM = " + PM);
		System.out.println("TDEV = " + TDEV);

		return effort;
	}

	// 求各SF因子的和
	public static Double getSumSF(HashMap<String, String> factorsSF) {
		Double sumSF = 0.0;
		String propertyKey;
		Set<String> factors = factorsSF.keySet();
		for (String factor : factors) {

			propertyKey = "SF." + factor + "." + factorsSF.get(factor);
			System.out.println(propertyKey);
			sumSF += Double.valueOf(COCOMOProperties.readValue(propertyKey));
		}
		return sumSF;
	}

	// 求各EM因子的乘积 不包括SCED
	public static Double getProductEM(HashMap<String, String> factorsEM) {
		Double productEM = 1.0;
		String propertyKey;
		Set<String> factors = factorsEM.keySet();
		for (Object factor : factors) {
			propertyKey = "EM." + factor.toString() + "."
					+ factorsEM.get(factor);
			productEM *= Double.valueOf(COCOMOProperties.readValue(propertyKey));
		}
		Double SCED = getSCEDValue(factorsEM.get("SCED"));
		return productEM / SCED;
	}

	public static Double getSCEDValue(String SCEDLevel) {
		String propertyKey = "EM.SCED." + SCEDLevel;
		Double SCED = Double.valueOf(COCOMOProperties.readValue(propertyKey));
		return SCED;
	}

	public static Double getE(Double sumSF) throws SQLException {
		return getB() + 0.01 * sumSF;
	}

	// 求阶段工作量分布
	public static Double[] getPhaseEfforts(String[] phases, String sizeLevel,
			String ELevel, Double PM) {
		Double[] efforts = new Double[phases.length];
		String propertyKey;
		for (int i = 0; i < phases.length; i++) {
			propertyKey = "activity." + phases[i] + ".E." + ELevel + "."
					+ sizeLevel;
			System.out.println(propertyKey);
			efforts[i] = PM
					* Double.valueOf(COCOMOProperties.readValue(propertyKey)) / 100;
		}
		return efforts;
	}

	// 求各阶段的活动工作量分布
	public static Double[] getActivityEfforts(String phase,
			String[] activities, String sizeLevel, String ELevel, Double PM,
			Double phaseEffort) {
		Double[] efforts = new Double[activities.length];
		String propertyKey;
		for (int i = 0; i < activities.length; i++) {
			propertyKey = "activity." + phase + ".E." + ELevel + "."
					+ activities[i] + "." + sizeLevel;
			System.out.println(propertyKey);
			efforts[i] = phaseEffort
					* Double.valueOf(COCOMOProperties.readValue(propertyKey)) / 100;
		}
		return efforts;
	}

	// 获取size的级别：S、I、M、L、VL
	public static String getSizeLevel(int size) {
		String[] levels = { "S", "I", "M", "L", "VL" };
		int sizeLevel;
		int abs;
		int absMin = Math
				.abs(size
						- Integer.valueOf(COCOMOProperties.readValue("size." + levels[0])));
		String resultLevel = levels[0];
		for (String level : levels) {
			sizeLevel = Integer.valueOf(COCOMOProperties.readValue("size." + level));
			abs = Math.abs(size - sizeLevel);
			resultLevel = (absMin < abs) ? resultLevel : level;
			absMin = Math.min(abs, absMin);
		}
		return resultLevel;
	}

	// 获取E的级别：S、M、L
	public static String getELevel(Double E) {
		String[] levels = { "S", "M", "L" };
		Double ELevel;
		Double abs;
		Double absMin = Math.abs(E
				- Double.valueOf(COCOMOProperties.readValue("E." + levels[0])));
		String resultLevel = levels[0];
		for (String level : levels) {
			ELevel = Double.valueOf(COCOMOProperties.readValue("E." + level));
			abs = Math.abs(E - ELevel);
			resultLevel = (absMin < abs) ? resultLevel : level;
			absMin = Math.min(abs, absMin);
		}
		return resultLevel;
	}

	// 获取阶段开发进度
	public static Double[] getScheduleTime(String[] phases, Double devTime) {
		Double[] scheduleTimes = new Double[phases.length];
		String propertyKey;
		for (int i = 0; i < phases.length; i++) {
			propertyKey = "phase." + phases[i] + ".schedule.min";
			scheduleTimes[i] = devTime
					* Double.valueOf(COCOMOProperties.readValue(propertyKey)) / 100;
		}
		return scheduleTimes;
	}

	// 获取阶段人员分布
	public static Double[] getPersonDistribution(Double[] efforts,
			Double[] schedules) {
		Double[] persons = new Double[efforts.length];
		for (int i = 0; i < efforts.length; i++)
			persons[i] = efforts[i] / schedules[i];
		return persons;
	}

	// 获取项目风险估算值
	public static Double[] getRiskAccessment(String[] risks,
			HashMap<String, Object> drivers) {
		Double[] riskValues = new Double[risks.length];
		String factorList;
		String[] factors;
		String[] rules;
		String[] attributes;
		String[] attributesLevels;
		int riskLevel;

		riskValues[0] = 0.0; // 总项目风险值初始化
		for (int i = 1; i < risks.length; i++) {
			riskValues[i] = 0.0;
			factorList = COCOMOProperties.readValue(risks[i]);
			factors = factorList.split(";");
			for (String factor : factors) {
				rules = COCOMOProperties.readValue(risks[i] + "." + factor).split(";");
				for (String rule : rules) {
					attributes = rule.split("_");
					attributesLevels = new String[attributes.length];
					for(int j=0; j<attributes.length; j++)
						attributesLevels[j]= getFactorLevel(attributes[j], drivers);
					riskLevel = getRiskLevel(attributesLevels);
					if (riskLevel != 0)
						riskValues[i] += riskLevel
								* getEffortMulProduct(attributes, attributesLevels);
				}
			}
			riskValues[0] += riskValues[i];
		}

		return riskValues;
	}

	// 获取风险等级
	private static int getRiskLevel(String[] levels) {
		int level = 0;
		// attribute的个数还有1和3的情况，暂不考虑
		if (levels.length == 2) {
			String level1 = levels[0];
			String level2 = levels[1];
			if ((level1.equals("XH") && level2.equals("VL"))
					|| (level1.equals("XH") && level2.equals("XL")))
				level = 4;
			else if ((level1.equals("VH") && level2.equals("VL"))
					|| (level1.equals("VH") && level2.equals("XL"))
					|| (level1.equals("XH") && level2.equals("L")))
				level = 2;
			else if ((level1.equals("H") && level2.equals("VL"))
					|| (level1.equals("H") && level2.equals("XL"))
					|| (level1.equals("VH") && level2.equals("L"))
					|| (level1.equals("XH") && level2.equals("N")))
				level = 1;
			else
				level = 0;
			System.out.println(level1 + "_"+level2 +"_"+"riskLevel:"+level);
		}
		
		return level;
	}

	// 获取风险评估中每个rule的effort multiplier product值
	private static Double getEffortMulProduct(String[] effortMultipliers,String[] levels) {
		final String factorsSF = "PREC;FLEX;RESL;TEAM;PMAT";
		Double product = 1.0;
		String type;
		String propertyKey;
		//对于没有等级值的因子，一律按N处理
		for (int i=0; i<effortMultipliers.length; i++){
			if (factorsSF.contains(effortMultipliers[i]))
				type = "SF";
			else 
				type = "EM";
			propertyKey = type + "." + effortMultipliers[i] + "." + levels[i];
			System.out.println(propertyKey);
			product *= Double.valueOf(COCOMOProperties.readValue(propertyKey));
			if (effortMultipliers[i].contains("SCED"))
				product /= relativeSCED.get(levels[i]);
		}
		return product;
	}
	
	//获取风险评估中各因子的对应的等级
	private static String getFactorLevel(String factor, HashMap<String, Object> factors)
	{
		String level;
		if((!factors.containsKey(factor))||factors.get(factor) == null)
			level = "N";
		else
			level = factors.get(factor).toString();
		return level;
	}
}
