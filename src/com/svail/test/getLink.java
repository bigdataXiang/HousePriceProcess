package com.svail.test;

import java.io.IOException;
import java.util.Vector;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.svail.chongqing.GetJsonText;
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;
import com.svail.util.Tool;

public class getLink {

	public static void main(String[] args) throws IOException {
		
		// String url="http://www.jdey.com.cn/doctor.aspx?page=";
		// for(int n=1;n<=8;n++){
		//	 getUniversityLink(url+Integer.toString(n));
		// }
		 

	   // getUniversityLink("http://isbf.sysu.edu.cn/cn/szll/szll01/index.htm");
		GetJsonText.jsonAddressMatch("D:/baidu/lingdaoxiaozu/lingdaoxiaozu-Result-delectRedundancy_NullException.txt");
		System.out.println("OK!");
	}
	public static String folder = "D:/zhouxiang/人口数据/简历数据/BaiDu/国际金融";
	/**
	 * 获取大学官网老师的链接
	 * 
	 * @param url
	 * @throws IOException
	 */
	public static void getUniversityLink(String url) throws IOException {
		int count = 0;
		try {
			//String content = HTMLTool.fetchURL(url, "utf-8", "get");//utf-8   GBK
			String content = Tool.fetchURL(url);
			System.out.println(content);
			Parser parser = new Parser();
			if (content == null) {
				FileTool.Dump(url, folder + "NullLink.txt", "utf-8");
			} else {
				parser.setInputHTML(content);
				parser.setEncoding("utf-8");//utf-8
				HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("ol"), new HasAttributeFilter("class", "techer-list")));
				HasParentFilter parentFilter2 = new HasParentFilter(new TagNameFilter("td"));
				HasParentFilter parentFilter3 = new HasParentFilter( new AndFilter(new TagNameFilter("li"),parentFilter2));
				//NodeFilter filter =new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("class", "apic"));//new HasAttributeFilter("target", "_blank")
				NodeFilter filter =new TagNameFilter("a");//,parentFilter2);
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				System.out.println(nodes);
				if (nodes.size()!=0) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String name = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").replace("&nbsp;", "").trim();
						String purl = tn.getAttribute("href");
						//if (purl.indexOf("TeacherDetail") != -1) {//purl.indexOf("zh") != -1&&
							FileTool.Dump(name + "," + purl, folder + "-中山-Link.txt", "utf-8");
							System.out.println(name + "," + purl);
							count++;
						//}

					}
					System.out.println(count);
				}

			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			e.printStackTrace();
			
		}

	}
	
}
