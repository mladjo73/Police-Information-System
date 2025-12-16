package view;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.MaskFormatter;

import model.TreeElement.Column;

/**
 *Klasa dinamički kreira različite tipove input polja(input Fields) na osnovu tipa podataka proslijedjene kolone
 *@author grupa4
 */
public class FieldGenerator
{
	public static InputField createInput(Column column) {
		
		InputField input;
	
		String label = column.getName() + ((column.isNullable())? "":"*");

	switch(column.getType()) {
	case "java.lang.String":{
		StringBuilder mask = new StringBuilder();
		
		for(int i=0; i<column.getSize(); i++) {
			mask.append("*");
		}
		JFormattedTextField formattedTextField = null;
		try {
			formattedTextField = new JFormattedTextField(new MaskFormatter(mask.toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		input = new TextField(label, formattedTextField, column);
		break;
	}
	case "java.math.BigDecimal": 
	case "java.lang.Double": {
		
		NumberFormat numFormat = NumberFormat.getInstance();
		numFormat.setMaximumIntegerDigits(column.getSize());
		numFormat.setMaximumFractionDigits(column.getScale());

		JFormattedTextField formattedField = new JFormattedTextField(createFormatter(numFormat));
		
		input = new TextField(label, formattedField, column);
		break;
	}
	case "java.lang.Short":
	case "java.lang.Integer":
	case "java.lang.Long":{
		
		NumberFormat numFormat = NumberFormat.getInstance();
		numFormat.setMaximumIntegerDigits(column.getSize());
		numFormat.setMaximumFractionDigits(0);
		numFormat.setParseIntegerOnly(true);
		
		input = new TextField(label, new JFormattedTextField(createFormatter(numFormat)), column);
		break;
	}
	case "java.lang.Boolean" : {
		input = new BooleanInput(column);
		break;
	}
	case "java.sql.Timestamp": {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm-d/MM/yyyy");
		
		input = new TextField(label, new JFormattedTextField(dateFormat), column);
		break;
	}
	default:
		throw new IllegalArgumentException("Nepoznati tip: " + column.getType() + " !");
	}
	if(column.getRefTable() != null) {			
		input = new LinkedField(input, column.getRefTable(), column.getRefColumn());
	}
	return input;
	}

private static DefaultFormatter createFormatter(NumberFormat format) {
	InternationalFormatter formatter = new InternationalFormatter(format) {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Object stringToValue(String text) throws java.text.ParseException {
            if (text.trim().isEmpty()) {
                return null;
            }
            return super.stringToValue(text);
        }
	};
	formatter.setOverwriteMode(false);
	return formatter;
};
}
