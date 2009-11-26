package newProject.sizeEstimate;

public class COCOMO {

	//重用代码量计算公式
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

	//维护代码量计算公式
	public static int getMaintainSize(Double addSLOC, Double modifySLOC,
			Double SU, Double UNFM) {
		Double size = (addSLOC + modifySLOC) * (1 + ((SU / 100) * UNFM));
		return size.intValue();
	}

}
