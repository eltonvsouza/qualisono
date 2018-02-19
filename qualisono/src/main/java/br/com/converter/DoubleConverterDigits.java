package br.com.converter;

//import java.text.DecimalFormat; 
//import java.text.Format; 
//import java.text.NumberFormat; 
//import java.util.Locale; 
//import java.util.regex.Pattern; 
//
//import javax.faces.component.UIComponent; 
//import javax.faces.context.FacesContext; 
//import javax.faces.convert.Converter; 
//import javax.faces.convert.ConverterException; 
//
//public class DoubleConverter implements Converter { 
//
//    public Object getAsObject(FacesContext arg0, UIComponent arg1, 
//            String valorTela) throws ConverterException { 
//
//        if (valorTela == null || valorTela.toString().trim().equals("")) { 
//            return 0.0d; 
//
//        } else { 
//            valorTela = valorTela.replaceAll(Pattern.quote("."), ""); 
//
//            try { 
////                NumberFormat nf = NumberFormat.getInstance();
//                NumberFormat nf = NumberFormat.getInstance(new Locale("pt_BR"));
//                nf.setMaximumFractionDigits(2); 
//
//                return nf.parse(valorTela.replace(",", ".")).doubleValue(); 
//
//            } catch (Exception e) { 
//                return 0.0d; 
//            } 
//        } 
//    } 
//
//    public String getAsString(FacesContext arg0, UIComponent arg1, 
//            Object valorTela) throws ConverterException { 
//
//        if (valorTela == null || valorTela.toString().trim().equals("")) { 
//            return "0,00"; 
//
//        } else { 
//            DecimalFormat nf = new DecimalFormat("#,##0.00"); 
//
//            return nf.format(Double.valueOf(valorTela.toString())); 
//        } 
//    } 
//
//}

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.stereotype.Service;
@Service
public class DoubleConverterDigits implements Converter{  
  
    public Object getAsObject(FacesContext context, UIComponent component, String value) {  
          
        if(value == null || value.trim().equals("")){  
            return 0.0D;  
         }  
            Boolean matcher = Pattern.matches("[0-9]+([.,][0-9]{4}+)?",value);  
            if(matcher){  
                value = value.replace(",", ".");  
                return new Double(value);  
            }else{  
//                    FacesUtil.messageError("Formato Invalido");  
                throw new ConverterException();  
            }  
    }  
  
    public String getAsString(FacesContext context, UIComponent component, Object object) {  
          
        if(object == null || object.toString().trim().equals("")){  
             return "0,0000";  
         }else{
        	 DecimalFormat nf = new DecimalFormat("#,####0.0000");
//             NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));  
             nf.setMaximumFractionDigits(4);  
             return nf.format(Double.valueOf(object.toString()));  
         }  
    }    
} 