import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DipteshK
 */
public class JapWNXMLParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws org.xml.sax.SAXException {
        // TODO code application logic here
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        HashMap<String,ArrayList<String>> dsf = new HashMap<String, ArrayList<String>>();
        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            //parse using builder to get DOM representation of the XML file
            Document dom = db.parse("jpn_wn_lmf.xml");
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("jpn.dsf")));
            Element docEle = dom.getDocumentElement();

            //get a nodelist of 
            NodeList nl = docEle.getElementsByTagName("LexicalEntry");
            System.out.println(nl.getLength());
            if (nl != null && nl.getLength() > 0) {
                for (int i = 0; i < nl.getLength(); i++) {
                    System.out.println(i);
                    //get the employee element
                    Element el = (Element) nl.item(i);
                    String word_id= el.getAttribute("id");
                    NodeList Lemmas = el.getElementsByTagName("Lemma");
                    Element lemma_el = (Element)Lemmas.item(0);
                    String lemma = lemma_el.getAttribute("writtenForm");
                    String pos = lemma_el.getAttribute("partOfSpeech");
                    NodeList Senses = el.getElementsByTagName("Sense");
                    if(Senses != null && Senses.getLength()>0){
                        for(int j = 0; j < Senses.getLength(); j++){
                            Element sense_el = (Element) Senses.item(j);
                            String e_sid = sense_el.getAttribute("id");
                            String j_sid = sense_el.getAttribute("synset");
                            if(dsf.containsKey(j_sid)){
                                ArrayList<String> dsf_data = dsf.get(j_sid);
                                dsf_data.add(lemma);
                                dsf_data.add(word_id);
                                dsf_data.add(e_sid);
                                dsf.put(j_sid, dsf_data);
                            } else {
                                ArrayList<String> dsf_data = new ArrayList<String>();
                                dsf_data.add(lemma);
                                dsf_data.add(word_id);
                                dsf_data.add(e_sid);
                                dsf.put(j_sid, dsf_data);
                            }
                        }
                    }
                    
                }
            }
            
            for(String key : dsf.keySet()){
                ArrayList dsf_data = dsf.get(key);
                int len = dsf_data.size()/3;
                String final_line = key+"\t";
                String words="";
                String wids="";
                String e_sids="";
                for(int ctr = 0 ;ctr < len ; ctr++){
                    words+=dsf_data.get(ctr*3+0)+"#";
                    wids+=dsf_data.get(ctr*3+1)+"#";
                    e_sids+=dsf_data.get(ctr*3+2)+"#";
                }
//                final_line+=words+"\t"+wids+"\t"+e_sids+"\n";
                final_line+=words+"\n";
                bw.write(final_line);
                bw.flush();
            }
            bw.close();

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}