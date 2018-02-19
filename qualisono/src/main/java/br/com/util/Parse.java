package br.com.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parse {
	static public double decimal(String valor){
		return Double.parseDouble(valor.replace(".", "").replace(",", "."));
	}
	
	static public Date data(String formato, String data){
		DateFormat formatter = new SimpleDateFormat(formato);
		Date date = null;
		if (!data.equals(""))
			try {
				date = (Date)formatter.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return date;
	}
}
