package helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenerateUniqueFilename {
	public static String generateUniqueFilename(String originalFilename) {
	    String timestamp = new SimpleDateFormat("yyyyMMddHHm-mssSSS").format(new Date());
	    return timestamp + "_" + originalFilename;
	}

}
