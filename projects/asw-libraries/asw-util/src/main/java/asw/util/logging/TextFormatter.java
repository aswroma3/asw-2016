package asw.util.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class TextFormatter extends Formatter {
	
	public String format(LogRecord record) {
		return record.getLevel() + ": " 
				// + record.getSourceClassName() +": " 
				+ record.getMessage() + "\n";  
	}

}
