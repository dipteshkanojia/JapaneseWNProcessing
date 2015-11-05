import in.ac.iitb.cfilt.common.io.UTFReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class ReProcessJapData {

	public static void main(String args[])throws Exception{
		System.out.println("Updating Synsets into file");
		WriteToFile();
	}
	
	

private static void WriteToFile() throws IOException, ClassNotFoundException, SQLException {
	File filedir = new File("JapWN.syns");

	Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filedir),"UTF-8"));

	UTFReader reader = new UTFReader("jpn.dsf");
	Map<String,String> Def = new HashMap<>();
	Map<String,String> Exe = new HashMap<>();


	String line = null;
	String line2 = null;
	String line3 = null;

	int lineNumber=1;
	reader.open();
	try{
		BufferedReader br = new BufferedReader(new FileReader("wnjpn-def.txt"));

		while ((line2 = br.readLine()) != null) {
			
			String parts2[] = line2.split("\t");
			Def.put(parts2[0], parts2[3]);
			
			
		}
		BufferedReader br2 = new BufferedReader(new FileReader("wnjpn-exe.txt"));
		
		while ((line3 = br2.readLine()) != null) {
			
			String parts3[] = line3.split("\t");
			Exe.put(parts3[0], parts3[3]);
			
			
		}
		while ((line = reader.readLine()) != null) {

			
			String parts[] = line.split("\t");
			String WordArray = parts[1].replace("#",", ");
			String Words = WordArray.replaceAll(", $", "");
		
			String IDCategory[] = parts[0].split("-1.1-");
			//System.out.println(IDCategory[1]);
			//out.write(IDCategory[1]);
			
			String IDSplit[] = IDCategory[1].split("-");
			
			
			
			String ID = IDSplit[0];
			String Category = IDSplit[1];
			if(Category.equals("n")){
				Category = "NOUN";
			}
			else if(Category.equals("v")){
				Category = "VERB";
			}
			else if(Category.equals("a")){
				Category = "ADJECTIVE";
			}
			else if(Category.equals("r")){
				Category = "ADVERB";
			}
			
			out.append("ID\t::\t"+ID+"\n");
			out.append("CAT\t::\t"+Category+"\n");
			out.append("CONCEPT\t::\t"+Def.get(IDCategory[1])+"\n");
			out.append("EXAMPLE\t::\t"+"\""+Exe.get(IDCategory[1])+"\""+"\n");
			out.append("SYNSET-JAPANESE\t::\t"+Words+"\n\n");
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
