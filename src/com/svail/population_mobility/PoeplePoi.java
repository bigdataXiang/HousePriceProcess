package com.svail.population_mobility;
import java.io.IOException;
import com.svail.util.Tool;

public class PoeplePoi {
	public String Name;
	public String Code;
	public String CodeAddr;
	public String CodeCoor;
	public String CodeReg;
	public String CtfTp;
	public String CtfId;
	public String Home;
	public String Gender;
	public String Birth;
	public String PostAddr;
	public String Mobile;
	public String Nation;
	public String Version;
	public String PostReg;
	public String PostCoor;
	PoeplePoi(String line) throws IOException
	{
		try{
			Name=Tool.getStrByKey(line, "<Name>", "</Name>", "</Name>");
			Code=Tool.getStrByKey(line, "<Code>", "</Code>", "</Code>");
			CodeAddr=Tool.getStrByKey(line, "<CodeAddr>", "</CodeAddr>", "</CodeAddr>");
			CodeCoor=Tool.getStrByKey(line, "<CodeCoor>", "</CodeCoor>", "</CodeCoor>");
			CodeReg=Tool.getStrByKey(line, "<CodeReg>", "</CodeReg>", "</CodeReg>");
			CtfTp=Tool.getStrByKey(line, "<CtfTp>", "</CtfTp>", "</CtfTp>");
			CtfId=Tool.getStrByKey(line, "<CtfId>", "</CtfId>", "</CtfId>");
			Home=Tool.getStrByKey(line, "<Home>", "</Home>", "</Home>");
			Gender=Tool.getStrByKey(line, "<Gender>", "</Gender>", "</Gender>");
			Birth=Tool.getStrByKey(line, "<Birth>", "</Birth>", "</Birth>");
			PostAddr=Tool.getStrByKey(line, "<PostAddr>", "</PostAddr>", "</PostAddr>");
			Mobile=Tool.getStrByKey(line, "<Mobile>", "</Mobile>", "</Mobile>");
			Nation=Tool.getStrByKey(line, "<Nation>", "</Nation>", "</Nation>");
			Version=Tool.getStrByKey(line, "<Version>", "</Version>", "</Version>");
			PostReg=Tool.getStrByKey(line, "<PostReg>", "</PostReg>", "</PostReg>");
			PostCoor=Tool.getStrByKey(line, "<PostCoor>", "</PostCoor>", "</PostCoor>");
		     
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 	 
	}

}
