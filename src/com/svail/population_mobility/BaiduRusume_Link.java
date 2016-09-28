package com.svail.population_mobility;
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
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;
import com.svail.util.Tool;

public class BaiduRusume_Link {
	public static void main(String[] args) throws IOException {
		
		// String url="http://www.physics.sjtu.edu.cn/faculty/teachers/95?page=";
		// for(int n=0;n<=14;n++){
		//	 getUniversityLink(url+Integer.toString(n));
		 //}
	   // getUniversityLink("http://med.nju.edu.cn/shizi.asp?id=89");
	    getResume(folder); 
		System.out.println("OK!");
	}
	
	public static String folder = "D:/zhouxiang/人口数据/简历数据/BaiDu/国际金融";
	public static String schoolLink="http://isbf.sysu.edu.cn/cn/szll/szll01/";
	public static String tagName="div";//
	public static String attribute="id";
	public static String value="cont";
	public static void getResume(String folder){
		try{
			Vector<String> pois =FileTool.Load(folder+"-中山-Link.txt", "UTF-8");
			for(int i=0;i<pois.size();i++){
				if(pois.elementAt(i).indexOf("@")==-1){
					String[] poi=pois.elementAt(i).split(",");
					String link=poi[1];
					String url="";
					if(link.indexOf("http:")==-1)
					    url=schoolLink+link;//"http://baike.baidu.com"+
					else
						url=link;	
					if(poi[0].length()>=0){//&&poi[0].length()<30
						String tempresume=parseUniverResume(url.replace("amp;", "")).replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").replace("&nbsp;", "").replace("&rdquo;", "").trim();
						if(tempresume.length()<0)
							FileTool.Dump(poi[0].replace("&nbsp;", ""), folder+"personlist.txt", "utf-8");
						String resume="<POI>"+"<name>"+poi[0].replace("&nbsp;", "")+"</name>"+"<link>"+url.replace("amp;", "")+"</link>"+tempresume+"</POI>";
						//parseResumeLink(url)
						FileTool.Dump(resume, folder+"-中山-Resume.txt", "utf-8");
						System.out.println(resume);
						
					}
				}
			}
		}catch(NullPointerException e){
			e.getStackTrace();
	}
}
	/**
	 * 解析老师的简历页面
	 * @param url
	 * @return
	 */
	public static String parseUniverResume(String url){
		String resume = "";
		try {
			//String content = HTMLTool.fetchURL(url, "utf-8", "get");//utf-8
			String content = Tool.fetchURL(url);
		    //System.out.println(content);
			Parser parser = new Parser();
			if (content == null) {
				FileTool.Dump(url, folder + "NullResume.txt", "utf-8");
				return null;
			} else {
				int count=1;
				parser.setInputHTML(content);
				parser.setEncoding("utf-8");//utf-8
				HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "right")));
				HasParentFilter parentFilter2 = new HasParentFilter(new AndFilter(new TagNameFilter("tbody"), parentFilter1));
				HasParentFilter parentFilter3 = new HasParentFilter( new AndFilter(new TagNameFilter("tr"),parentFilter2));
				//NodeFilter filter =new TagNameFilter("a");//,parentFilter1);
			    // NodeFilter filter = new AndFilter(new TagNameFilter("table"),new HasAttributeFilter("id", "MainContent_expert"));
			    NodeFilter filter = new AndFilter(new TagNameFilter(tagName),new HasAttributeFilter(attribute, value));
				//NodeFilter filter =new AndFilter(new AndFilter(new TagNameFilter("p"), new HasAttributeFilter("style", " margin-top:0px; margin-bottom:0px; margin-left:2px; margin-right:0px; -qt-block-indent:0; text-indent:0px;")), parentFilter1);
				NodeList nodes = parser.extractAllNodesThatMatch(filter);//在职教师首页>师资队伍>系所介绍
				//System.out.println(nodes);
				if (nodes.size()!=0) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace("教师名录您的位置是：首页师资队伍教师名录基本信息", "").replace("&gt;", "").replace("&ensp;", "").replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").replace("&#8211;", "-").replace("&nbsp;", "").replace("&ldquo", "").replace("&#160;", "").replace("", "").trim();
						
						resume +="<profile"+count+">"+str+"</profile"+count+">";	
						count++;
						//System.out.println(str);
					}
								
				}
				
			    parser.reset();
			   // parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "ejcontent")));
			  //  parentFilter2 = new HasParentFilter(new AndFilter( new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "info_list_ct")),parentFilter1));
			    filter = new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "Custom_UnionStyle"));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes.size() != 0) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").replace("&#8211;", "-").replace("&nbsp;", "").trim();
						resume +="<profile"+count+">"+str+"</profile"+count+">";	
						count++;
						//System.out.println(str);
					}
								
				}
				/*
				parser.reset();
			    parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "details col-md-12 col-sm-12 col-xs-12")));
				filter = new AndFilter(new TagNameFilter("p"),parentFilter1);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes.size() != 0) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").replace("&#8211;", "-").replace("&nbsp;", "").trim();
						resume +="<profile"+count+">"+str+"</profile"+count+">";	
						count++;
						//System.out.println(str);
					}
								
				}
		
				 */
			}		

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
			FileTool.Dump(url, folder+"NullLink.txt", "utf-8");
		}

		return resume;

	
	}
	 
/**
 * 解析百度百科里人物的内容
 * @param url
 * @return
 */
	public static String parseResumeLink(String url) {
		String resume = "";
		try {
			String content = HTMLTool.fetchURL(url, "utf-8", "get");
			Parser parser = new Parser();
			if (content == null) {
				FileTool.Dump(url, folder + "NullResume.txt", "utf-8");
				return null;
			} else {
				parser.setInputHTML(content);
				parser.setEncoding("utf-8");
				HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("dd"), new HasAttributeFilter("class", "lemmaWgt-lemmaTitle-title")));
				NodeFilter filter = new AndFilter(new TagNameFilter("h1"),parentFilter1);
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					TagNode tn = (TagNode) nodes.elementAt(0);
					String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").trim();
					resume += "<name>" + str + "</name>";				
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "lemma-summary")));
				filter = new AndFilter(new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "para")),parentFilter1);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").trim();
						resume += "<profile" + (n + 1) + ">" + str + "</profile" + (n + 1) + ">";
					}
					//System.out.println(resume);
				}
				parser.reset();
				filter = new AndFilter(new TagNameFilter("dt"), new HasAttributeFilter("class", "basicInfo-item name"));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					String temp="";
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace("&nbsp;", "").replace("\r\n", "").replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").trim();
						temp += str + "|" ;
					}
					resume+="<basicInfo-item-name>"+temp+"</basicInfo-item-name>";
					//System.out.println(resume);
				}
				parser.reset();
				filter = new AndFilter(new TagNameFilter("dd"), new HasAttributeFilter("class", "basicInfo-item value"));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					String temp="";
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace("&nbsp;", "").replace("\r\n", "").replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").trim();
						temp += str + "|" ;
					}
					resume+="<basicInfo-item-value>"+temp+"</basicInfo-item-value>";
					//System.out.println(resume);
				}
			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
			FileTool.Dump(url, folder+"NullLink.txt", "utf-8");
		}

		return resume;

	}

	/**
	 * 获取百度百科里人物的链接
	 * 
	 * @param url
	 * @throws IOException
	 */
	public static void getResumeLink(String url) throws IOException {
		int count = 0;
		try {
			String content = HTMLTool.fetchURL(url, "utf-8", "get");
			Parser parser = new Parser();
			if (content == null) {
				FileTool.Dump(url, folder + "NullLink.txt", "utf-8");
			} else {
				parser.setInputHTML(content);
				parser.setEncoding("utf-8");
				NodeFilter filter = new AndFilter(new TagNameFilter("a"), new HasAttributeFilter("style", "color:;"));
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String name = tn.toPlainTextString().replace("&nbsp;", "").replace("\r\n", "").replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").trim();
						String purl = tn.getAttribute("href");
						if (purl.indexOf("newsopen") != -1) {
							FileTool.Dump(name + "," + purl, folder + "Academy-of-Engineering-Link.txt", "utf-8");
							System.out.println(name + "," + purl);
							count++;
						}

					}
					System.out.println(count);
				}

			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 获取大学官网老师的链接
	 * 
	 * @param url
	 * @throws IOException
	 */
	public static void getUniversityLink(String url) throws IOException {
		int count = 0;
		try {
			//String content = HTMLTool.fetchURL(url, "GBK", "get");//utf-8   GBK
			String content = Tool.fetchURL(url);
			System.out.println(content);
			Parser parser = new Parser();
			if (content == null) {
				FileTool.Dump(url, folder + "NullLink.txt", "utf-8");
			} else {
				parser.setInputHTML(content);
				parser.setEncoding("GBK");//utf-8
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
							FileTool.Dump(name + "," + purl, folder + "-吉林大学-Link.txt", "utf-8");
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
