package com.svail.crawl.crawldata;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.svail.util.FileTool;
import com.svail.util.HTMLTool;

public class Fang_newhouse {


	private static Random random = new Random();
	/**
	 * java.util.Random类中实现的随机算法是伪随机，也就是有规则的随机，
	 * 所谓有规则的就是在给定种(seed)的区间内随机生成数字；相同种子数的Random对象，
	 * 相同次数生成的随机数字是完全相同的；Random类中各方法生成的随机数字都是均匀分布的，
	 * 也就是说区间内部的数字生成的几率均等；
	 * Random()：创建一个新的随机数生成器。
	 */
	private static String BJ_NEWHOUSE = "NEW";
	private static String BJ_RENTINFO = "RENTINFO";
	private static String BJ_RENTOUT = "RENTOUT";
	private static String BJ_RESOLDS = "RESOLD";
	
	public static String FOLDER="D:/Crawl_test";
	

	/* 解析出租页面 */
	private static String parseRentOut(String url)
	{
		String content = HTMLTool.fetchURL(url, "gb2312", "get");//GB2312是汉字书写国家标准。

		Parser parser = new Parser();//获取解析器
		if (content == null)
		{
			return null;
		}
		
		String poi = null;
		try {
			
			parser.setInputHTML(content);
			parser.setEncoding("gb2312");
			// 获取发布时间
			NodeFilter filter = new AndFilter(new TagNameFilter("dl"), new HasAttributeFilter("class", "title"));//通过创建如下NodeFilter对象，提取其间的文本
			//NodeFilter:过滤某个节点以及其子节点,NodeFilter是一个接口,接口是不能被实例化的,被实例化的是那个接口的实现类   那个实现类指针指向的是那个接口
			//TagNameFilter意思是根据节点名称来过滤。TagNameFilter("dl")是过滤"dl"
			//HasAttributeFilter("class", "title")可以匹配出包含制定名字的属性，或者制定属性为指定值的节点。
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			//遍历所有的节点
			if (nodes != null && nodes.size() == 1)
			{
				String str = nodes.elementAt(0).toPlainTextString().replace("免费发布出租", "").replace("\r\n", "").replace("\t", "").replace("\b","").replace("\n","").replace("\r","").replace("&nbsp;", "").trim();
				//elementAt(int index) 方法用于获取组件的向量的指定索引/位置。
				//String toPlainTextString()：取得纯文本信息。
				//replace(char oldChar, char newChar)返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 而生成的。
				//trim()去掉字符串中的空格
				//int n = str.indexOf("房源编号：");//indexOf()返回指定字符在此字符串中第一次出现处的索引
				int m = str.indexOf("发布时间：");
				int i= str.indexOf("房源编号");
				int s=str.indexOf("]");
				if(i!=-1)
				poi = "<TITLE>" + str.substring(0, i).replace(" ", "").replace("\r\n", "").replace("\t", "").trim() + "</TITLE>";
				if(s!=-1)
				poi = "<TITLE>" + str.substring(s+"]".length(), m).replace(" ", "").replace("\r\n", "").replace("\t", "").trim() + "</TITLE>";	
					if (m != -1)
					{
						int k = str.indexOf("(", m + "发布时间：".length());
						if (k != -1)
						{
							poi += "<TIME>" + str.substring(m + "发布时间：".length(), k).trim() + "</TIME>";
							parser.reset();//解析器重置清零的意思?
							
							// Huxing floatl
							filter = new AndFilter(new TagNameFilter("span"), new HasAttributeFilter("class", "num red"));
							
							nodes = parser.extractAllNodesThatMatch(filter);
							if (nodes != null)
							{
								for (int cnt = 0; cnt < nodes.size(); cnt ++)
								{
									Node cni = nodes.elementAt(cnt);
									
									str = cni.getParent().toPlainTextString().trim();
									//java.io.File.getParent() 方法返回的路径字符串，如果此抽象路径名的父或如果此路径名没有指定父目录则为null。
									//Node getParent ()：取得父节点
									int n=str.indexOf("元/月");
									if ( n!= -1)
									{
										poi += "<PRICE>" + str.substring(0,n).replace("\r\n", "") +"元/月"+ "</PRICE>";
										break;
									}
								}
							
							parser.reset();
							
							// <span class="num red">
							filter = new AndFilter(new TagNameFilter("li"), new HasParentFilter(new AndFilter(new TagNameFilter("ul"), new HasAttributeFilter("class", "Huxing floatl"))));
							nodes = parser.extractAllNodesThatMatch(filter);

							if (nodes != null)
							{
								for (int mm = 0; mm < nodes.size(); mm ++)
								{
									Node ni = nodes.elementAt(mm);
										
									if (ni instanceof TagNode && ((TagNode)ni).getTagName().equalsIgnoreCase("li"))
										//equals是比较2个字符串是否相等的，区分大小写 equalsignorecase功能一样，但是不区分大小写
										//instanceof是Java的一个二元操作符,它的作用是测试它左边的对象是否是它右边的类的实例，返回boolean类型的数据。
									{
										String tt = ni.toPlainTextString().trim();
										
										
										int ix = tt.indexOf("物业类型：");
										if (ix != -1)
										{
											String sub = tt.substring(ix + "物业类型：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<PROPERTY_TYPE>" + sub + "</PROPERTY_TYPE>";
											continue;
										}
										
										ix = tt.indexOf("小 区：");
										
										if (ix != -1)
										{
											String sub = tt.substring(ix + "小 区：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<COMMUNITY>" + sub + "</COMMUNITY>";
											continue;
										}

										ix = tt.indexOf("地 址：");
										
										if (ix != -1)
										{

											String sub = tt.substring(ix + "地 址：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<ADDRESS>" + sub + "</ADDRESS>";
											continue;
										}
										
										ix = tt.indexOf("户 型：");
										
										if (ix != -1)
										{

											String sub = tt.substring(ix + "户 型：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<HOUSE_TYPE>" + sub + "</HOUSE_TYPE>";
											
											continue;
										}

										ix = tt.indexOf("出租间：");
										
										if (ix != -1)
										{

											String sub = tt.substring(ix + "出租间：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<PARTMENT>" + sub + "</PARTMENT>";
											
											continue;
										}


										ix = tt.indexOf("面 积：");
										
										if (ix != -1)
										{

											String sub = tt.substring(ix + "面 积：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<AREA>" + sub + "</AREA>";

											continue;
										}

										ix = tt.indexOf("朝 向：");
										
										if (ix != -1)
										{

											String sub = tt.substring(ix + "朝 向：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<DIRECTION>" + sub + "</DIRECTION>";
											
											continue;
										}

										ix = tt.indexOf("楼 层：");
										
										if (ix != -1)
										{

											String sub = tt.substring(ix + "楼 层：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<FLOOR>" + sub + "</FLOOR>";

											continue;
										}
										

										ix = tt.indexOf("装 修：");
										
										if (ix != -1)
										{

											String sub = tt.substring(ix + "装 修：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<DECORATION>" + sub + "</DECORATION>";

											continue;
										}
										
										ix = tt.indexOf("公 交：");
										
										if (ix != -1)
										{

											String sub = tt.substring(ix + "公 交：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
											if (sub.indexOf("暂无") == -1)
												poi += "<TRAFFIC>" + sub + "</TRAFFIC>";

											continue;
										}
									}
								}
						return "<POI>" + poi + "<URL>" + url + "</URL></POI>";
							}
						}
					}		
				}
			}
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		if (poi != null)
		{
			poi = poi.replace("&nbsp;", "");
			int ss = poi.indexOf("[");
			while (ss != -1)
			{
				int ee = poi.indexOf("]", ss + 1);
				if (ee != -1)
				{
					String sub = poi.substring(ss, ee + 1);
					poi = poi.replace(sub, "");
				}
				else
					break;
				ss = poi.indexOf("[", ss);
			}
		}
		return poi;
	}
	
	/* 解析二手房页面 */
	private static String parseResold(String url)
	{
		String content = HTMLTool.fetchURL(url, "gb2312", "get");

		Parser parser = new Parser();
		if (content == null)
		{
			return null;
		}
		
		String poi = null;
		try {
			
			parser.setInputHTML(content);
			parser.setEncoding("gb2312");
			// 获取发布时间
			
			/* <dl class="title">
             * <dt class="name floatl">
             *   <p>
             * 长寿黄桷雅居 3室2厅126平米 押一付三(个人)
             *      
             * </p>
             * <p style="font-size: 12px; line-height: 16px" class="gray9">
             * 房源编号：51173910&nbsp;&nbsp;&nbsp;发布时间：2015/2/21 5:02:44(3天前更新)</p>
             *    <script type="text/javascript" src="http://img1.soufun.com/rent/image/rent/js/shareclick.js"></script>
             *   <a name="top" id="top"></a>
             *   <dd>
             *       <a class="btn-fabu" href="/Rent/Rent_Info/Rent_Input_Front.aspx" target="_blank"
             *           id="A1">免费发布出租</a></dd>
             * </dl>div class="mainBoxL
             * */
			NodeFilter filter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "mainBoxL"));//class mainBoxL
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			
			if (nodes != null && nodes.size() == 1)
			{
				String str = nodes.elementAt(0).toPlainTextString().replace("\r\n", "").replace("\t", "").trim();
				
				int n = str.indexOf("房源编号：");
				int m = str.indexOf("发布时间：");
				
				if (n != -1)
				{
					poi = "<TITLE>" + str.substring(0, n).trim() + "</TITLE>";
					if (m != -1)
					{
						int k = str.indexOf("(", m + "发布时间：".length());
						if (k != -1)
						{
							poi += "<TIME>" + str.substring(m + "发布时间：".length(), k) + "</TIME>";
						}
					}
				}
			}
			if (poi == null)
				return poi;
			
			parser.reset();
			filter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "inforTxt"));
			nodes = parser.extractAllNodesThatMatch(filter);
			
			if (nodes != null)
			{
				for (int rds = 0; rds < nodes.size(); rds ++)
				{
					NodeList hs = nodes.elementAt(rds).getChildren();//NodeList getChildren ()：取得子节点的列表
					if (hs == null)
						continue;
					
					for (int jk = 0; jk < hs.size(); jk ++)
					{
						if (hs.elementAt(jk) instanceof TagNode)//instanceof 测试它左边的对象是否是它右边的类的实例，返回boolean类型的数据。
						{
							TagNode tn = (TagNode) hs.elementAt(jk);
							if (tn.getTagName().equalsIgnoreCase("dl"))
							{
								NodeList chld = tn.getChildren();
								
								if (chld != null)
								{
									for (int cnt = 0; cnt < chld.size(); cnt ++)
									{
										if (chld.elementAt(cnt) instanceof TagNode)
										//elementAt() 函数把字符串划分为元素，并返回位于指定下标位置的元素。
									    //n = String.elementAt(string, index, separator):n从函数返回的字符串,string要解析的字符串。index一个整数，定义被返回的部分;separator分隔字符串的分隔符。
										//如果 index 是负数，则返回第一个元素。如果 index 的值太大，则返回最后一个元素。
										{
											String str = ((TagNode) chld.elementAt(cnt)).toPlainTextString().replace("\r\n", "").replace("\t", "").replace(" ","").trim();
											int kk = str.indexOf("总价：");
											if (kk != -1)
											{
												int kk1 = str.indexOf("(", kk + "总价：".length());
												/**
												 *java.lang.String.indexOf(String str, int fromIndex) 方法返回在此字符串中第一次出现的指定子指数，在指定的索引开始。返回的整数是最小的k值: 
                                                  k > = Math.min(fromIndex, this.length()) && this.startsWith(str, k)
                                                                                                                                                     如果不存在这样的k值，则返回-1
                                                  str -- 这是要搜索的子串.fromIndex -- 这是该指数开始搜索.
                                                                                                                                                       此方法返回指数在此字符串中第一次出现的指定子字符串，起始在指定的索引                                                                                                  
                                                                                                                 
												 */
												if (kk1 != -1)
												{
													String substr = str.substring(kk + "总价：".length(), kk1);
													poi += "<PRICE>" + substr + "</PRICE>";
												}
												continue;
											}
											kk = str.indexOf("户型：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "户型：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<HOUSE_TYPE>" + substr + "</HOUSE_TYPE>";
												continue;
											}
											kk = str.indexOf("面积：");
											// returns -1 as substring is not located
											// indexOf()是从字符串的0个位置开始查找的,索引位置也是从0开始计算,一个空格算入一个索引位置
											if (kk != -1)
											{
												String substr = str.substring(kk + "面积：".length());
												//subString() 函数返回字符串的指定部分。
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_AREA>" + substr + "</BUILDING_AREA>";
												continue;
											}
											
											kk = str.indexOf("年代：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "年代：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_TIME>" + substr + "</BUILDING_TIME>";
												continue;
											}
											
											kk = str.indexOf("朝向：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "朝向：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_DIR>" + substr + "</BUILDING_DIR>";
												continue;
											}
											kk = str.indexOf("楼层：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "楼层：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_FLOOR>" + substr + "</BUILDING_FLOOR>";
												continue;
											}
											kk = str.indexOf("结构：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "结构：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_STRUCT>" + substr + "</BUILDING_STRUCT>";
												continue;
											}
											kk = str.indexOf("装修：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "装修：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_CONDITION>" + substr + "</BUILDING_CONDITION>";
												continue;
											}
											kk = str.indexOf("名称：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "名称：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_NAME>" + substr + "</BUILDING_NAME>";
												continue;
											}
											kk = str.indexOf("住宅类别：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "住宅类别：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_TYPE>" + substr + "</BUILDING_TYPE>";
												continue;
											}
											
											kk = str.indexOf("产权性质：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "产权性质：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_OWNERTYPE>" + substr + "</BUILDING_OWNERTYPE>";
												continue;
											}
											
											kk = str.indexOf("配套设施：");
											if (kk != -1)
											{
												String substr = str.substring(kk + "配套设施：".length());
												if (substr.indexOf("暂无") == -1)
													poi += "<BUILDING_SERVICE>" + substr + "</BUILDING_SERVICE>";
												continue;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			parser.reset();
			filter = new AndFilter(new  TagNameFilter("p"), new HasParentFilter(new HasAttributeFilter("class", "traffic mt10")));
			nodes = parser.extractAllNodesThatMatch(filter);
			
			if (nodes != null)
			{
				for (int cnt = 0; cnt < nodes.size(); cnt ++)
				{
					String str = nodes.elementAt(cnt).toPlainTextString().trim();
					int si = str.indexOf("址：");
					if (str.indexOf("地") != -1 && si != -1)
					{
						if (str.indexOf("暂无") == -1)
							poi += "<ADDRESS>" + str.substring(si + "址：".length()) + "</ADDRESS>";
					}
				}
			}
			poi = poi.replace("&nbsp;", "");
			int ss = poi.indexOf("[");
			while (ss != -1)
			{
				int ee = poi.indexOf("]", ss + 1);
				if (ee != -1)
				{
					String sub = poi.substring(ss, ee + 1);
					poi = poi.replace(sub, "");
				}
				else
					break;
				ss = poi.indexOf("[", ss);
			}
			return "<POI>" + poi + "<URL>" + url + "</URL></POI>";

		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		return poi;
	}
	
	/* 解析本月开盘页面 */
	private static String parseNewBuilding(String url)
	{
		String content = HTMLTool.fetchURL(url, "utf-8", "get");

		Parser parser = new Parser();
		if (content == null)
		{
			return null;
		}
		
		String poi = "";
		try {
			
			parser.setInputHTML(content);
			parser.setEncoding("gb2312");
			// <a id="xfxq_C03_14" target="_blank" href="http://haitangwanld023.fang.com/house/3111064820/housedetail.htm">更多详细信息&gt;&gt;</a>
			NodeFilter filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new HasAttributeFilter("id", "xfxq_C03_14"), new HasAttributeFilter("href")));//xfxq_C03_14
			//AndFilter可以把两种Filter进行组合，只有同时满足条件的Node才会被过滤。
			//NodeFilter filter = new HasAttributeFilter( "id", "logoindex" );getText:div id="logoindex"
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			
			if (nodes != null)
			{
				for (int n = 0; n < nodes.size(); n ++)
				{
					if (nodes.elementAt(n) instanceof TagNode)
					//elementAt(int index) 方法用于获取组件的向量的指定索引/位置。index--这是一个索引该向量,它返回指定索引处的组件。
					{
						TagNode tn = (TagNode) nodes.elementAt(n);
						
						if (tn.getAttribute("href").endsWith("housedetail.htm"))
							//getAttribute这个方法是提取放置在某个共享区间的对象的，他对应了setAttribute方法，比如在session中，使用setAttribute将一个数据放入session区间，那么在一个会话区间内，便可以在其他页面中用getAttribute将数据提取并使用
							//Java String.endsWith()方法用法实例教程, 该方法返回一个true，如果参数表示的字符序列是由该对象表示的字符序列的后缀，否则返回false
							//java.lang.String.endsWith() 方法返回的测试该字符串是否以指定后缀结束
						{
							content = HTMLTool.fetchURL(tn.getAttribute("href"), "gb18030", "get");
							
							if (content == null)
							{
								return null;
							}
							try {
								
								parser.setInputHTML(content);
								parser.setEncoding("gb2312");
								
								// filter = new AndFilter(new TagNameFilter("table"), new HasParentFilter(new HasAttributeFilter("class", "besic_inform")));
								
								nodes = parser.extractAllNodesThatMatch(new HasAttributeFilter("class", "besic_inform"));
								
								//for (int jk = 0; jk < nodes.size(); jk ++)
								
									String tt = nodes.elementAt(0).toPlainTextString().replace("\r\n", "").replace("\n", "").replace("\t", "").replace(" ", "").replace("&nbsp;", "").trim();
			
									if (tt.indexOf("[更多]") != -1)
									{
										
											poi += "<COMMUNITY>" +  tt.substring(tt.indexOf("[更多]")+"[更多]".length(),tt.indexOf("房价")) + "</COMMUNITY>";
									}
									if (tt.indexOf("物业类别") != -1)
									{
										
											poi += "<BUILDING_USAGE>" + tt.substring(tt.indexOf("物业类别")+"物业类别".length(),tt.indexOf("项目特色")) + "</BUILDING_USAGE>";
									}
									 if (tt.indexOf("项目特色") != -1)
									{
										
											poi += "<CHARACTER>" + tt.substring(tt.indexOf("项目特色")+"项目特色".length(),tt.indexOf("建筑类别"))+ "</CHARACTER>";
									}
									 if (tt.indexOf("建筑类别") != -1)
									{
										
											poi += "<BUILDING_TYPE>" + tt.substring(tt.indexOf("建筑类别")+"建筑类别".length(),tt.indexOf("装修状况")) + "</BUILDING_TYPE>";
									}
									 if (tt.indexOf("装修状况") != -1)
									{
										
											poi += "<BUILDING_CONDITION>" + tt.substring(tt.indexOf("装修状况")+"装修状况".length(),tt.indexOf("环线位置")) + "</BUILDING_CONDITION>";
									}
									 if (tt.indexOf("环线位置") != -1)
									{
										 String huanxian=tt.substring(tt.indexOf("环线位置")+"环线位置".length(),tt.indexOf("容积率"));
										 if(huanxian.indexOf("装修案例")!=-1)
										    poi += "<LOOP_LINE>" + huanxian.subSequence(0, huanxian.indexOf("装修案例"))  + "</LOOP_LINE>";
										 else
											poi += "<LOOP_LINE>" + huanxian+ "</LOOP_LINE>";
									}
									 if (tt.indexOf("容积率") != -1)
									{
										
											poi += "<FAR>" + tt.substring(tt.indexOf("容积率")+"容积率".length(),tt.indexOf("绿化率")) + "</FAR>";
									}
									if (tt.indexOf("绿化率") != -1)
									{
										
											poi += "<GREEN>" +tt.substring(tt.indexOf("绿化率")+"绿化率".length(),tt.indexOf("开盘时间")) + "</GREEN>";
									}
									 if (tt.indexOf("开盘时间") != -1)
									{
										
											poi += "<SALE_TIME>" + tt.substring(tt.indexOf("开盘时间")+"开盘时间".length(),tt.indexOf("交房时间")) + "</SALE_TIME>";
									}
									 if (tt.indexOf("交房时间") != -1)
									{
										
											poi += "<SUBMIT_TIME>" + tt.substring(tt.indexOf("交房时间")+"交房时间".length(),tt.indexOf("物业费")) + "</SUBMIT_TIME>";
									}
									 if (tt.indexOf("物业费") != -1)
									{
										
											poi += "<PROPERTY_FEE>" + tt.substring(tt.indexOf("物业费")+"物业费".length(),tt.indexOf("物业公司")) + "</PROPERTY_FEE>";
									}
									 if (tt.indexOf("物业公司") != -1)
									{
										
											poi += "<SERVER>" + tt.substring(tt.indexOf("物业公司")+"物业公司".length(),tt.indexOf("开发商"))+ "</SERVER>";
									}
									 if (tt.indexOf("开发商") != -1)
									{
										
											poi += "<DEVELOPER>" + tt.substring(tt.indexOf("开发商")+"开发商".length(),tt.indexOf("预售许可证"))+ "</DEVELOPER>";
									}
									 if (tt.indexOf("预售许可证") != -1)
										{
											
												poi += "<LICENCE>" + tt.substring(tt.indexOf("预售许可证")+"预售许可证".length(),tt.indexOf("售楼地址")) + "</LICENCE>";
										}
									 if (tt.indexOf("售楼地址") != -1)
									{
										
											poi += "<SALE_ADDRESS>" + tt.substring(tt.indexOf("售楼地址")+"售楼地址".length(),tt.indexOf("物业地址")) + "</SALE_ADDRESS>";
									}
									 if (tt.indexOf("物业地址") != -1)
									{
										
											poi += "<SEVER_ADDRESS>" + tt.substring(tt.indexOf("物业地址")+"物业地址".length(),tt.indexOf("交通状况")) + "</SEVER_ADDRESS>";
									}
									 if (tt.indexOf("交通状况") != -1)
									{
										
											poi += "<TRAFFIC>" +tt.substring(tt.indexOf("交通状况")+"交通状况".length(),tt.indexOf("更多")) + "</TRAFFIC>";
									}
									 if (tt.indexOf("价") != -1)
									{
										
										
											poi += "<PRICE>" + tt.substring(tt.indexOf("价")+"价".length(),tt.indexOf("走势")).replace("[房价", "") + "</PRICE>";
									}
									
								if (!poi.isEmpty())
								{
									int ss = poi.indexOf("[");
									while (ss != -1)
									{
										int ee = poi.indexOf("]", ss + 1);
										if (ee != -1)
										{
											String sub = poi.substring(ss, ee + 1);
											poi = poi.replace(sub, "");
										}
										else
											break;
										ss = poi.indexOf("[", ss);
									}
									return "<POI>" + poi + "<URL>" + url + "</URL></POI>";
								}
							
							} catch (ParserException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
						}
					}
				}
			}

			return null;

		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		if (poi != null)
		{
			poi = poi.replace("&nbsp;", "");
			int ss = poi.indexOf("[");
			while (ss != -1)
			{
				int ee = poi.indexOf("]", ss + 1);
				if (ee != -1)
				{
					String sub = poi.substring(ss, ee + 1);
					poi = poi.replace(sub, "");
				}
				else
					break;
				ss = poi.indexOf("[", ss);
			}
		}
		return poi;
	}
	
	/* 解析求租页面 */
	private static String parseRental(String url)
	{
		String content = HTMLTool.fetchURL(url, "gb2312", "get");

		Parser parser = new Parser();
		if (content == null)
		{
			return null;
		}
		
		String poi = null;
		try {
			
			parser.setInputHTML(content);
			parser.setEncoding("gb2312");
			// 获取发布时间
			// div class="title"
			NodeFilter filter = new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "title"));
			NodeList nodes = parser.extractAllNodesThatMatch(filter);
			
			if (nodes != null && nodes.size() == 1)
			{
				nodes = nodes.elementAt(0).getChildren();
				for (int n = 0; n < nodes.size(); n ++)
				{
					String str = nodes.elementAt(n).toPlainTextString().trim();
					
					int mm = str.indexOf("]");
					if (mm != -1)
					{
						if (poi == null)
							poi = "<TITLE>" + str.substring(mm + 1).replace("\r\n", "").replace("\n", "").replace("\b", "").replace("\t", "") + "</TITLE>";
						else
							poi += "<TITLE>" + str.substring(mm + 1) + "</TITLE>";
						continue;
					}
					
					mm = str.indexOf("发布");
					if (mm != -1)
					{
						if (poi == null)
							poi = "<TIME>" + str.replace("发布", "").replace("-", "/").replace("\r\n", "").replace("\n", "").replace("\b", "").replace("\t", "") + "</TIME>";
						else
							poi += "<TIME>" + str.replace("发布", "").replace("-", "/").replace("\r\n", "").replace("\n", "").replace("\b", "").replace("\t", "") + "</TIME>";
					}
				}
			}
			parser.reset();
			// class="house"
			filter = new AndFilter(new TagNameFilter("dl"), new HasAttributeFilter("class", "house"));
			// 期望租金：1500元/月 期望面积： 不小于65平米 租赁方式：整租 求租地点：九龙坡，石桥铺   期望户型：二居房屋配套：暂无资料
			nodes = parser.extractAllNodesThatMatch(filter);
			
			if (nodes != null)
			{
				for (int n = 0; n < nodes.size(); n ++)
				{
					Node no = nodes.elementAt(n);
					if (no instanceof TagNode)
					{
						TagNode tno = (TagNode) no;
						String str = tno.toPlainTextString().replace(" ", "").replace("\t", "").replace("\r\n", "").replace("\n", "").replace("\b", "").replace("\t", "").trim();
						/**
						 * stringObj.split([separator，[limit]]) 
						 * stringObj:必选项。要被分解的 String 对象或文字。该对象不会被 split 方法修改。 
						 * separator :可选项。字符串或 正则表达式对象，它标识了分隔字符串时使用的是一个还是多个字符。如果忽略该选项，返回包含整个字符串的单一元素数组。
						 * limit:可选项。该值用来限制返回数组中的元素个数。
						 * split 方法的结果是一个字符串数组，在 stingObj 中每个出现 separator 的位置都要进行分解
						 */
						int kk=str.indexOf("期望租金：");
						if (kk != -1)
						{
							String substr = str.substring(kk + "期望租金：".length(),str.indexOf("面积：")-"期望".length());
								poi += "<PRICE>" + substr + "</PRICE>";
						}
					   kk=str.indexOf("期望面积：");
						if (kk != -1)
						{
							String substr = str.substring(kk + "期望面积：".length(),str.indexOf("租赁方式："));
								poi += "<AREA>" + substr + "</AREA>";
						}
						kk=str.indexOf("租赁方式：");
						if (kk != -1)
						{
							String substr = str.substring(kk + "租赁方式：".length(),str.indexOf("求租地点："));
								poi += "<SCHEMA>" + substr + "</SCHEMA>";
							
						}
						kk=str.indexOf("求租地点：");
						if (kk != -1)
						{
							String substr = str.substring(kk + "求租地点：".length(),str.indexOf("户型")-"期望".length());
								poi += "<ADDRESS>" + substr + "</ADDRESS>";
						
						}
						kk=str.indexOf("期望户型：");
						if (kk != -1)
						{
							String substr = str.substring(kk + "期望户型：".length(),str.indexOf("房屋配套："));
								poi += "<BUILDTYPE>" + substr + "</BUILDTYPE>";
						}
						kk=str.indexOf("房屋配套：");
						if (kk != -1)
						{
							String substr = str.substring(kk + "房屋配套：".length());
								poi += "<EQUITMENT>" + substr + "</EQUITMENT>";
						}
						/*

							else if (toks[jk].startsWith("期望面积："))
							{
								str = toks[jk].substring("期望面积：".length());
								if (str.indexOf("暂无") == -1)
								{
									poi += "<AREA>" + str.trim() + "</AREA>";
									continue;
								}
							}
							else if (toks[jk].startsWith("租赁方式："))
							{
								str = toks[jk].substring("租赁方式：".length());
								if (str.indexOf("暂无") == -1)
								{
									poi += "<SCHEMA>" + str.trim() + "</SCHEMA>";
									continue;
								}
							}else if (toks[jk].startsWith("求租地点："))
							{
								str = toks[jk].substring("求租地点：".length());
								if (str.indexOf("暂无") == -1)
								{
									poi += "<ADDRESS>" + str.trim() + "</ADDRESS>";
									continue;
								}
							}else if (toks[jk].startsWith("期望户型："))
							{
								str = toks[jk].substring("期望户型：".length());
								if (str.indexOf("暂无") == -1)
								{
									poi += "<BUILDTYPE>" + str.trim() + "</BUILDTYPE>";
									continue;
								}
							}else if (toks[jk].startsWith("房屋配套："))
							{
								str = toks[jk].substring("房屋配套：".length());
								if (str.indexOf("暂无") == -1)
								{
									poi += "<EQUITMENT>" + str.trim() + "</EQUITMENT>";
									continue;
								}
							}
							*/
						}						
					}
				}
			
			parser.reset();
			// <span class="tel"> <span>15922777917</span><span class="font14 gray6 master">小李</span> </span>
			filter = new AndFilter(new TagNameFilter("span"), new HasAttributeFilter("class", "qiuzutel"));
			nodes = parser.extractAllNodesThatMatch(filter);
			if (nodes != null)
			{
				String s = nodes.elementAt(0).toPlainTextString().replace(" ", "").replace("\t", "").replace("\r\n", "").trim();
				poi += "<CONTACT>" + s + "</CONTACT>";
			}
						
			parser.reset();
			// <p class="beizhu">家电齐全，价钱看房后面议</p>
			filter = new AndFilter(new TagNameFilter("p"), new HasAttributeFilter("class", "beizhu"));
			nodes = parser.extractAllNodesThatMatch(filter);
			if (nodes != null)
			{
				String s = nodes.elementAt(0).toPlainTextString().replace(" ", "").replace("\t", "").replace("\r\n", "").trim();
				poi += "<NOTATION>" + s + "</NOTATION>";
			}
			
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		if (poi != null)
		{
			poi = poi.replace("&nbsp;", "");
			int ss = poi.indexOf("[");
			while (ss != -1)
			{
				int ee = poi.indexOf("]", ss + 1);
				if (ee != -1)
				{
					String sub = poi.substring(ss, ee + 1);
					poi = poi.replace(sub, "");
				}
				else
					break;
				ss = poi.indexOf("[", ss);
			}
			
			if (poi != null  && !poi.isEmpty())
				poi = "<POI>" + poi + "<URL>" + url + "</URL></POI>";
		}
		return poi;
	}
	
	/*
	 * 二手房 http://esf.cq.fang.com/
	 * 出租房  http://zu.cq.fang.com/
     * */
	public static String regions[] = {
		"/house-a01/", "/house-a00/", "/house-a06/", "/house-a02/", "/house-a03/", "/house-a04/",
		"/house-a05/", "/house-a07/", "/house-a012/", "/house-a0585/", "/house-a010/", "/house-a011/",
		"/house-a08/", "/house-a013/", "/house-a09/", "/house-a014/", "/house-a015/", "/house-a016/",
		"/house-a0987/", "/house-a011817/",
	};
	
	/* 抓取二手房数据
	 * */
	public static String RESOLDAPARTMENT_URL = "http://esf.fang.com";
	
	public static void getResoldApartmentInfo(String region)
	{
		// 首先加载
		Vector<String> log = null;
		//Vector是一个集合，用数组实现的。所以vector的数据结构是数组。vector里面包含的可以是int，string等任何类型，包括一个自定义的对象。
		synchronized(BJ_RESOLDS)
		//无论synchronized关键字加在方法上还是对象上，它取得的锁都是对象，而不是把一段代码或函数当作锁
		{
			log = FileTool.Load(FOLDER + File.separator + region + "_resold.log", "UTF-8");
		}
		// 2014/12/8 17:16:42
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");//小写的mm表示的是分钟
		
		Date latestdate = null;
		Date newest = null;
		
		if (log != null)
		{
			try {
				latestdate = sdf.parse(log.elementAt(0));
				latestdate = new Date(latestdate.getTime() - 1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String url = RESOLDAPARTMENT_URL + region;
		Vector<String> urls = new Vector<String>();
		
		Set<String> visited = new TreeSet<String>();
		urls.add(url);
		
		Parser parser = new Parser();
		boolean quit = false;
		
		while (urls.size() > 0)
		{
			// 解析页面
			url = urls.get(0);
			
			urls.remove(0);
			visited.add(url);
			
			String content = HTMLTool.fetchURL(url, "gb2312", "get");

			if (content == null)
			{
				continue;
			}
			try {
				
				parser.setInputHTML(content);
				parser.setEncoding("gb18030");
				// <dd class="info rel floatr">
				// <p class="title"><a href="/chushou/3_153703104.htm" target="_blank" title="冉家坝龙山小学旁 光宇阳光地中海精装三房急售 无营业税拎包入住">冉家坝龙山小学旁 光宇阳光地中海精装三房急售 无营业税拎包入住</a></p>					
				// <p class="title"
				HasParentFilter parentFilter = new HasParentFilter(new AndFilter(new TagNameFilter("p"), new HasAttributeFilter("class", "title")));
				//HasChildFilter：是返回有符合条件的子节点的节点，需要另外一个Filter作为过滤子节点的参数。HasParentFilter和HasSiblingFilter的功能与HasChildFilter类似。
				//TagNameFilter：是最容易理解的一个Filter，根据Tag的名字进行过滤
				//HasAttributeFilter:可以匹配出包含制定名字的属性，或者制定属性为指定值的节点。
				NodeFilter filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new AndFilter(parentFilter, new HasAttributeFilter("title")), new HasAttributeFilter("href"))); 
				
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				
				if (nodes != null)
				{
					for (int n = 0; n < nodes.size(); n ++)
					{
						TagNode tn = (TagNode)nodes.elementAt(n);
						String purl = tn.getAttribute("href");
						if (purl.startsWith("/chushou"))
						{
							String poi = parseResold("http://esf.fang.com" + purl);
							if (poi != null)
							{
								// 获取时间
								int m = poi.indexOf("<TIME>");
								int k = poi.indexOf("</TIME>");
								
								if (m != -1 && k != -1)
								{
									assert(m < k);
									//assertion检查通常在开发和测试时启动，为了提高效率，在软件发布后，assertion检查通常是关闭的。
									String tm = poi.substring(m + "<TIME>".length(), k);
									try {
										Date date = sdf.parse(tm);
										if (latestdate != null)
										{
											if (date.before(latestdate))
											{
												quit = true;
											}
											else if (newest == null)
											{
												newest = date;
											}
											else{
												if (newest.before(date))
													newest = date;
											}
												
										}
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									if (quit)
									{
										break;
									}
									else
									{
										synchronized(BJ_RESOLDS)
										{
											FileTool.Dump(poi, FOLDER +"/ResoldApartmentInfo.txt", "UTF-8");
										}
									}
								}
							}
							
							try {
								Thread.sleep(500 * ((int) (Math
									.max(1, Math.random() * 3))));
							} catch (final InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
				
				parser.reset();
				
				// <div class="fanye gray6">  <a class="pageNow">PageControl1_hlk_next
				filter = new AndFilter(new TagNameFilter("a"),new HasAttributeFilter("id","PageControl1_hlk_next") );
				/* filter = new AndFilter(new TagNameFilter("div"), 
					new HasChildFilter(new AndFilter(new TagNameFilter("a"), new HasAttributeFilter("class", "pageNow")))); 
				*/
				nodes = parser.extractAllNodesThatMatch(filter);
				
				if (nodes != null)
				{
					String turl = ((TagNode)nodes.elementAt(0)).getAttribute("href");
					if (!visited.contains("http://esf.fang.com" + turl))
					{
						int kk = 0;
						for (; kk < urls.size(); kk ++)
						{
							if (urls.elementAt(kk).equalsIgnoreCase("http://esf.fang.com" + turl))
							{
								break;
							}
						}
						
						if (kk == urls.size())
							urls.add("http://esf.fang.com" + turl);
					}
					/* for (int nn = 0; nn < nodes.size(); nn ++)
					{
						NodeList chds = nodes.elementAt(nn).getChildren();

						if (chds == null)
							continue;
						
						for (int jk = 0; jk < chds.size(); jk ++)
						{
							if (chds.elementAt(jk) instanceof TagNode)
							{
								TagNode tni = (TagNode) chds.elementAt(jk);
								String href = tni.getAttribute("href");
								if (tni.getTagName().equalsIgnoreCase("a") && tni.getAttribute("id") == null && href != null)
								{
									if (!visited.contains("http://esf.fang.com" + href))
									{
										int kk = 0;
										for (; kk < urls.size(); kk ++)
										{
											if (urls.elementAt(kk).equalsIgnoreCase("http://esf.fang.com" + href))
											{
												break;
											}
										}
										
										if (kk == urls.size())
											urls.add("http://esf.fang.com" + href);
									}
								}
							}	
						}
					}*/
				}
				
				if (quit)
					break;
			}
			catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		
		synchronized(BJ_RESOLDS)
		{
			File f = new File(FOLDER + File.separator + region + "_resold.log");
			f.delete();
			if (newest != null)
			{			
				FileTool.Dump(sdf.format(newest), FOLDER + File.separator + region + "_resold.log", "UTF-8");
			}
		}
	}
	
	/* 抓取出租数据
	 * */
	public static String RENTOUT_URL = "http://zu.fang.com";
	
	public static void getRentOutInfo(String region)
	{
		// 首先加载
		Vector<String> log = null;
		synchronized(BJ_RENTOUT)
		{
			log = FileTool.Load(FOLDER + File.separator + region + "_rentout.log", "UTF-8");
		}
		// 2014/12/8 17:16:42
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	    //
		
		Date latestdate = null;
		Date newest = null;
		
		if (log != null)
		{
			try {
				latestdate = sdf.parse(log.elementAt(0));
				latestdate = new Date(latestdate.getTime() - 1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String url = RENTOUT_URL + region;
		Vector<String> urls = new Vector<String>();
		
		Set<String> visited = new TreeSet<String>();
		urls.add(url);
		
		Parser parser = new Parser();
		boolean quit = false;
		
		while (urls.size() > 0)
		{
			// 解析页面
			url = urls.get(0);
			
			urls.remove(0);
			visited.add(url);
			
			String content = HTMLTool.fetchURL(url, "gb2312", "get");
			
			if (content == null)
			{
				continue;
			}
			try {
				
				parser.setInputHTML(content);
				parser.setEncoding("gb2312");
				// <dd class="info rel floatr">
				// <p class="title"><a href="/chuzu/3_153703104.htm" target="_blank" title="冉家坝龙山小学旁 光宇阳光地中海精装三房急售 无营业税拎包入住">冉家坝龙山小学旁 光宇阳光地中海精装三房急售 无营业税拎包入住</a></p>					
				// <p class="title"
				HasParentFilter parentFilter = new HasParentFilter(new AndFilter(new TagNameFilter("p"), new HasAttributeFilter("class", "title")));
				NodeFilter filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new AndFilter(parentFilter, new HasAttributeFilter("target")), new HasAttributeFilter("href"))); 
				
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				
				if (nodes != null)
				{
					for (int n = 0; n < nodes.size(); n ++)
					{
						TagNode tn = (TagNode)nodes.elementAt(n);
						String purl = tn.getAttribute("href");
						if (purl.startsWith("/chuzu"))
						{
							String poi = parseRentOut("http://zu.fang.com" + purl);
							if (poi != null)
							{
								// 获取时间
								int m = poi.indexOf("<TIME>");
								int k = poi.indexOf("</TIME>");
								
								if (m != -1 && k != -1)
								{
									assert(m < k);
									String tm = poi.substring(m + "<TIME>".length(), k);
									try {
										if(tm.indexOf('-')!=-1)
											tm=tm.replace("-", "/");	
										Date date = sdf.parse(tm);
										if (latestdate != null)
										{
											if (date.before(latestdate))
											{
												quit = true;
											}
											else if (newest == null)
											{
												newest = date;
											}
											else{
												if (newest.before(date))
													newest = date;
											}
												
										}
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									if (quit)
									{
										break;
									}
									else
									{
										synchronized(BJ_RENTOUT)
										//synchronized是Java语言的关键字，当它用来修饰一个方法或者一个代码块的时候，能够保证在同一时刻最多只有一个线程执行该段代码。
										{
											FileTool.Dump(poi, FOLDER +"/bj_RentOutInfo.csv", "UTF-8");
											System.out.println(poi);
										}
									}
								}
							}
							
							try {
								Thread.sleep(500 * ((int) (Math
									.max(1, Math.random() * 3))));
							} catch (final InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
				
				parser.reset();
				
				// <div class="fanye gray6">  <a class="pageNow">
				filter = new AndFilter(new TagNameFilter("div"), 
					new HasChildFilter(new AndFilter(new TagNameFilter("a"), new HasAttributeFilter("class", "pageNow")))); 
				
				nodes = parser.extractAllNodesThatMatch(filter);
				
				if (nodes != null)
				{
					for (int nn = 0; nn < nodes.size(); nn ++)
					{
						NodeList cdl = nodes.elementAt(nn).getChildren();
						
						if (cdl == null)
							continue;
						
						for (int jj = 0; jj < cdl.size(); jj ++)
						{
							if (cdl.elementAt(jj) instanceof TagNode)
							{
								TagNode tni = (TagNode) cdl.elementAt(jj);
								String href = tni.getAttribute("href");
								if (tni.getTagName().equalsIgnoreCase("a") && tni.getAttribute("id") == null && href != null)
								{
									if (!visited.contains("http://zu.fang.com" + href))
									{
										int kk = 0;
										for (; kk < urls.size(); kk ++)
										{
											if (urls.elementAt(kk).equalsIgnoreCase("http://zu.fang.com" + href))
											{
												break;
											}
										}
										
										if (kk == urls.size())
											urls.add("http://zu.fang.com" + href);
									}
								}
							}
						}						
					}
				}
				
				if (quit)
					break;
			}
			catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		
		synchronized(BJ_RENTOUT)
		{
			File f = new File(FOLDER + File.separator + region + ".log");
			f.delete();
			if (newest != null)
			{			
				FileTool.Dump(sdf.format(newest), FOLDER + File.separator + region + ".log", "UTF-8");
			}
		}
	}
	
	/* 抓取求租数据
	 * */
	public static String RENTAL_URL = "http://zu.fang.com/qiuzu/h316/";
	
	public static void getRentalInfo()
	{
		// 首先加载
		Vector<String> log = null;
		synchronized(BJ_RENTINFO)
		{
			log = FileTool.Load(FOLDER + File.separator + "rental.log", "UTF-8");
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");//小写的mm表示的是分钟
		
		Date latestdate = null;
		Date newest = null;
		
		if (log != null)
		{
			try {
				latestdate = sdf.parse(log.elementAt(0));
				latestdate = new Date(latestdate.getTime() - 1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String url = RENTAL_URL;
		Vector<String> urls = new Vector<String>();
		
		Set<String> visited = new TreeSet<String>();
		urls.add(url);
		
		Parser parser = new Parser();
		boolean quit = false;
		
		while (urls.size() > 0)
		{
			// 解析页面
			url = urls.get(0);
			
			urls.remove(0);
			visited.add(url);
			
			String content = HTMLTool.fetchURL(url, "gb2312", "get");
			
			if (content == null)
			{
				continue;
			}
			try {
				
				parser.setInputHTML(content);
				parser.setEncoding("gb2312");
				// <dd class="info rel floatr">
				// <p class="title"><a href="/qiuzu/3_153703104.htm" target="_blank" title="冉家坝龙山小学旁 光宇阳光地中海精装三房急售 无营业税拎包入住">冉家坝龙山小学旁 光宇阳光地中海精装三房急售 无营业税拎包入住</a></p>					
				// <p class="title"
				HasParentFilter parentFilter = new HasParentFilter(new AndFilter(new TagNameFilter("p"), new HasAttributeFilter("class", "title")));
				NodeFilter filter = new AndFilter(new TagNameFilter("a"), new AndFilter(new AndFilter(parentFilter, new HasAttributeFilter("target")), new HasAttributeFilter("href"))); 
				
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				
				if (nodes != null)
				{
					for (int n = 0; n < nodes.size(); n ++)
					{
						TagNode tn = (TagNode)nodes.elementAt(n);
						String purl = tn.getAttribute("href");
						if (purl.startsWith("/qiuzu"))
						{
							String poi = parseRental("http://zu.fang.com" + purl);
							if (poi != null)
							{
								// 获取时间
								int m = poi.indexOf("<TIME>");
								int k = poi.indexOf("</TIME>");
								
								if (m != -1 && k != -1)
								{
									assert(m < k);
									String tm = poi.substring(m + "<TIME>".length(), k);
									try {
										Date date = sdf.parse(tm);
										if (latestdate != null)
										{
											if (date.before(latestdate))
											{
												quit = true;
											}
											else if (newest == null)
											{
												newest = date;
											}
											else{
												if (newest.before(date))
													newest = date;
											}
												
										}
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									if (quit)
									{
										break;
									}
									else
									{
										synchronized(BJ_RENTINFO)
										{
											FileTool.Dump(poi, FOLDER +"/bj_RentalInfo.csv", "UTF-8");
											System.out.println(poi);
										}
									}
								}
							}
							
							try {
								Thread.sleep(500 * ((int) (Math
									.max(1, Math.random() * 3))));
							} catch (final InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
				
				parser.reset();
				
				// <div class="fanye gray6">  <a class="pageNow">
				filter = new AndFilter(new TagNameFilter("div"), 
					new HasChildFilter(new AndFilter(new TagNameFilter("a"), new HasAttributeFilter("class", "pageNow")))); 
				
				nodes = parser.extractAllNodesThatMatch(filter);
				
				if (nodes != null)
				{
					for (int nn = 0; nn < nodes.size(); nn ++)
					{
						NodeList cld = nodes.elementAt(nn).getChildren();
						if (cld == null)
							continue;
						
						for (int jj = 0; jj < cld.size(); jj ++)
						{
							if (cld.elementAt(jj) instanceof TagNode)
							{
								TagNode tni = (TagNode) cld.elementAt(jj);
								String href = tni.getAttribute("href");
								if (tni.getTagName().equalsIgnoreCase("a") && tni.toPlainTextString().indexOf("下一页") == -1 &&  tni.toPlainTextString().indexOf("末页") == -1 && href != null)
								{
									if (!visited.contains("http://zu.fang.com" + href))
									{
										int kk = 0;
										for (; kk < urls.size(); kk ++)
										{
											if (urls.elementAt(kk).equalsIgnoreCase("http://zu.fang.com" + href))
											{
												break;
											}
										}
										
										if (kk == urls.size())
											urls.add("http://zu.fang.com" + href);
									}
								}
							}
						}
					}
				}
				
				if (quit)
					break;
			}
			catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		
		synchronized(BJ_RENTINFO)
		{
			File f = new File(FOLDER + File.separator + "rental.log");
			f.delete();
			if (newest != null)
			{			
				FileTool.Dump(sdf.format(newest), FOLDER + File.separator + "rental.log", "UTF-8");
			}
		}
		
	}
	
	/*
	 * 新盘
	 *  本月开盘  http://newhouse.cq.fang.com/house/saledate/201502.htm
	 *  top100楼盘 http://newhouse.cq.fang.com/house/asp/trans/buynewhouse/default.htm
	 * */
	public static String NEWBUILDING_URL = "http://newhouse.fang.com/house/saledate/";
	
	public static void getNewBuildingInfo(int year, int month)
	{
		String url = NEWBUILDING_URL;
		
		if (month < 10)
			url += year + "0" + month;
		else
			url += year + month;
		url += ".htm";
		
		Vector<String> urls = new Vector<String>();
		
		Set<String> visited = new TreeSet<String>();
		urls.add(url);
		
		Parser parser = new Parser();
		boolean quit = false;
		
		while (urls.size() > 0)
		{
			// 解析页面
			url = urls.get(0);
			
			urls.remove(0);
			visited.add(url);
			
			String content = HTMLTool.fetchURL(url, "gb2312", "get");

			if (content == null)
			{
				continue;
			}
			try {
				
				parser.setInputHTML(content);
				parser.setEncoding("gb2312");
				int ss = content.indexOf("<strong class=\"f14 fb_blue\">");
				
				while (ss != -1)
				{
					int en = content.indexOf("</strong>", ss + "<strong class=\"f14 fb_blue\">".length());
					if (en != -1)
					{
						String sub = content.substring(ss, en);
						
						int rfs = sub.indexOf("href=\"");
						if (rfs != -1)
						{
							int rfe = sub.indexOf("\"", rfs + "href=\"".length());
							if (rfe != -1)
							{
								String purl = sub.substring(rfs + "href=\"".length(), rfe);
								String poi = parseNewBuilding(purl);
								if (poi != null)
								{
									synchronized(BJ_NEWHOUSE)
									{
										FileTool.Dump(poi, FOLDER, "UTF-8");
										System.out.println(poi);
									}
								}
							else
								break;
						}
						else
							break;
					}
					else
						break;
					
					ss = content.indexOf("<strong class=\"f14 fb_blue\">", en + "</strong>".length());
					
					try {
						Thread.sleep(500 * ((int) (Math
							.max(1, Math.random() * 3))));
					} catch (final InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}				
				parser.reset();
				// <div class="fanye gray6">  <a class="pageNow">
				NodeFilter filter = new AndFilter(new TagNameFilter("div"), new HasParentFilter(new HasAttributeFilter("class", "searchListPage"))); 
				
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				
				if (nodes != null)
				{
					for (int nn = 0; nn < nodes.size(); nn ++)
					{
						Node ni = nodes.elementAt(nn);
						NodeList cld = ni.getChildren();
						if (cld != null)
						{
							for (int kkk = 0; kkk < cld.size(); kkk ++)
							{
								if (cld.elementAt(kkk) instanceof TagNode)
								{
									String href = ((TagNode)cld.elementAt(kkk)).getAttribute("href");
									if (href != null)
									{
										if (!href.startsWith("http://"))
										{
											if (href.startsWith("/house"))
												href = "http://newhouse.fang.com" + href;
											else
												continue;
										}
										
										if (!visited.contains(href))
										{
											int kk = 0;
											for (; kk < urls.size(); kk ++)
											{
												if (urls.elementAt(kk).equalsIgnoreCase(href))
												{
													break;
												}
											}
											
											if (kk == urls.size())
												urls.add(href);
										}
									}
								}
							}
						}						
					}
				}
			}
			}catch (ParserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		
	}
	
	public static void main(String[] args) {
		
		getNewBuildingInfo(2016,1);//已经调试好917
	}
}


