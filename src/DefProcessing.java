import in.ac.iitb.cfilt.common.io.UTFReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;


public class DefProcessing {
	public static void main(String args[])throws Exception{
		System.out.println("Updating Definitions into file");
		WriteToFile();
}


private static void WriteToFile() throws IOException, ClassNotFoundException, SQLException {
	File filedir = new File("JapWNDef.syns");

	Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filedir),"UTF-8"));

	UTFReader reader = new UTFReader("wnjpn-def.txt");

	String line = null;
	int lineNumber=1;
	reader.open();
	try{
		while ((line = reader.readLine()) != null) {

			
			String parts[] = line.split("\t");
				
			out.append(parts[0]+"\t"+parts[3]+"\n");

			lineNumber++;
			
			}
		reader.close();
		out.flush();
		out.close();
		System.out.println("FinisheD!");
		}
		catch(UnsupportedEncodingException e){
			System.out.println(e.getMessage());
		}
	
	}

}
