package com.svail.test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.svail.util.Tool;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONObject;


public class WaterShutoffNotice {
	public static void main(String[] args) throws IOException {
		getWaterShutoffNotice("http://www.cq966886.com/wsfw.php?cid=49",
				              "D:/重庆基础数据抓取/基础数据/停水通知/");
	}


	public static void getWaterShutoffNotice(String url,String folder){
		
			try {
				String content = Tool.fetchURL(url);

				Parser parser= new Parser();
				if (content == null) {
					//FileTool.Dump(url, folder + "-Null.txt", "utf-8");
				} else {
					parser.setInputHTML(content);
					parser.setEncoding("utf-8");
				    HasParentFilter parentFilter_table = new HasParentFilter(new AndFilter(new TagNameFilter("table"),new HasAttributeFilter("width", "98%")));
					NodeList nodes = parser.extractAllNodesThatMatch(parentFilter_table);
					if (nodes.size() != 0) {
						for (int n = 0; n < nodes.size(); n++) {
							Node node =  nodes.elementAt(n);
							if(node instanceof TagNode){
								Logger.getGlobal().log(Level.INFO," 获取到包含tbody的TagNode");
								TagNode no = (TagNode) nodes.elementAt(n);
								String html=no.toHtml();
								Attribute tbody=no.getAttributeEx("tr");

								Parser parser_html= new Parser();
								parser_html.setInputHTML(html);
								parser_html.setEncoding("utf-8");

								HasParentFilter parentFilter_html = new HasParentFilter(new AndFilter(new TagNameFilter("td"),new HasAttributeFilter("class", "list2")));
								NodeFilter filter_html = new AndFilter(new TagNameFilter("a"),parentFilter_html);
								NodeList nodes_html = parser_html.extractAllNodesThatMatch(filter_html);
								if(nodes_html.size()!=0){
									JSONObject obj=new JSONObject();
									for(int i=0;i<nodes_html.size();i++){
										TagNode no_html = (TagNode) nodes_html.elementAt(i);
										String str_html=no_html.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
										String href_html=no_html.getAttribute("href");
										System.out.println(str_html);
										System.out.println(href_html);
									}
								}

								String str=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
							}

						}
					}
				}

			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		
	}

}