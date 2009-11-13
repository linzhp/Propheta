package dataManager;

import java.util.ArrayList;

public class Statistic {

	//给ArrayList<Double>快速排序
	//初始化时：left=0, right=count-1;
	private static ArrayList<Double> Sort(ArrayList<Double> array, int left, int right){ 
		Double middle,strTemp;
		int i = left; 
		int j = right; 
		middle = array.get((left+right)/2); 
		do{ 
			while ((array.get(i) < middle) && (i < right))
				i++; 
			while ((array.get(j) > middle) && (j > left))
				j--; 
			if(i<=j){ 
				strTemp = array.get(i); 
				array.set(i, (array.get(j)));
				array.set(j, strTemp);
				i++; 
				j--;
			} 
		}while(i<j);//如果两边扫描的下标交错，完成一次排序 
		if(left<j) 
			Sort(array,left,j); //递归调用
		if(right>i) 
			Sort(array,i,right); //递归调用
		return array; 
	} 

	//取得ArrayList<Double>(已排好序  升序)的中位数
	public static Double getMedian(ArrayList<Double> array)
	{
		return array.get((array.size()-1)/2);
	}
	
	//取得ArrayList<Double>的平均值
	public static Double getMean(ArrayList<Double> array)
	{
		Double sum = 0.0;
		
		for(int i=0; i< array.size(); i++)
			sum += array.get(i);
		
		return sum/array.size();
	}
	
	//取得ArrayList<Double>的标准偏差
	public static Double getStandardDeviation(Double mean, ArrayList<Double> array)
	{
		Double sum = 0.0;
		
		for(int i=0; i< array.size(); i++)
			sum += Math.pow((array.get(i) - mean), 2);
		
		return Math.sqrt(sum/array.size());
	}
}
