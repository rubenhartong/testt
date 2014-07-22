package model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandler {
	
	public Timestamp stringToTimestamp(String string) {
		 java.util.Date date= new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(string != "") {
				Date parsedDate = dateFormat.parse(string);
				System.out.println(parsedDate);
				timestamp = new java.sql.Timestamp(parsedDate.getTime());
			} 
		} catch(Exception e){
				System.out.println("error in stringToTimestamp: " + e);
		}
		
		return timestamp;
	}
	
	public String getDate(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String s = dateFormat.format(date);
		
		return s;
	}
	
public String getUniDate() throws ParseException{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateS = dateFormat.format(date);		
		return dateS;
	}
	
	public String dateToString(Date date){
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			String s = dateFormat.format(date);
			
			return s;
		}
	
	public Date dateTimeToDate(String date) throws ParseException{
			
		return  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
	}
	
	
	public String uniDate(Date date){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String s = dateFormat.format(date);
		
		return s;
	}
	
	public String dateTimeToString(Date date){
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String s = dateFormat.format(date);
		
		return s;
	}
	
	public Date dateCompare(Timestamp dateT) throws ParseException{
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String s = dateFormat.format(dateT);
		Date date = dateFormat.parse(s);
		
		return date;
	}
}
