package demo;

import java.io.IOException;
import java.util.Calendar;



public class Test {
	public static void main(String[] args) throws IOException {
		Calendar cal = Calendar.getInstance();
		int year=cal.get(Calendar.YEAR);//得到年
		int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		String fileName = "D:\\imageUpload\\" + year +"\\" + month+"\\"+day;
		System.out.println(fileName);//打印
	}
}
