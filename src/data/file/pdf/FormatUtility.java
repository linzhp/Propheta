package data.file.pdf;

public class FormatUtility {
	public String ConvertKeyToShow(int i, Object key) {
		String text = null;
		if(key!=null){
		
		String[] texts = null;
		String[] values = null;
		switch (i) {
		case 1:
			texts = new String[] { "电信", "金融", "流通", "保险", "交通", "媒体", "卫生",
					"制造", "政府", "能源" };
			values = new String[] { "Telecom", "Finance", "Retail", "General",
					"Transport", "Media", "HealthCare", "Manufacturing",
					"PublicAdmin", "Energy" };
			break;

		case 2:
			texts = new String[] { "新开发", "二次开发", "优化", "其它" };
			values = new String[] { "NewDevelopment", "ReDevelopment",
					"Enhancement", "Other" };
			break;
		case 3:
			texts = new String[] { "面向对象分析设计", "事件建模", "业务领域建模", "回归测试",
					"面向对象与事件建模", "回归测试与业务领域建模", "其它" };
			values = new String[] {
					"Object Oriented Analysis;Object Oriented Design",
					"Event Modelling",
					"Business Area Modelling",
					"Regression Testing",
					"Object Oriented Analysis;Object Oriented Design;Event Modelling",
					"Regression Testing;Business Area Modelling", "Other" };
			break;
		case 4:
			texts = new String[] { "大型机", "中型机", "个人计算机", "混合" };
			values = new String[] { "MF", "MR", "PC", "Multi" };
			break;
		case 5:
			texts = new String[] { "第二代语言", "第三代语言", "第四代语言", "应用代" };
			values = new String[] { "2GL", "3GL", "4GL", "ApG" };
			break;
		case 6:
			texts = new String[] { "ASP", "C#", "VB", "JAVA", "C++", "C",
					"COBOL" };
			values = new String[] { "ASP", "C#", "VB", "Java", "C++", "C",
					"Cobol" };
			break;
		}

		text = arrayfind(key.toString(), texts, values);
		}
		return text;
	}
	public String arrayfind(String key, String[] s1, String[] s2) {

		String value = null;
		int length = s1.length;
		for (int i = 0; i < length; i++) {
			if (s2[i].equals(key)) {
				value = s1[i];
				break;
			}
		}
		return value;
	}
}

