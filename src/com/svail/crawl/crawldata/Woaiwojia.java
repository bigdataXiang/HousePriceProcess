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
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;
public class Woaiwojia {


	private static Random random = new Random();
	private static String BJ_NEWHOUSE = "NEW";
	private static String BJ_RENTOUT = "RENTOUT";
	private static String BJ_RESOLDS = "RESOLD";

	public static String LOG = "D:/test";

	public static String regions[] = {
		"/anzhen/","/aolinpikegongyuan/","/beishatan/","/beiyuan/","/baiziwan/","/changying/",
		"/cbd/","/chaoqing/","/chaoyangbeilu/","/chaoyanggongyuan/","/chaoyangmen/","/dashanzi/",
		"/dongba/","/dongbalizhuang/","/dingfuzhuang/","/dongdaqiao/","/dawanglu/","/dougezhuang/",
		"/fatou/","/ganluyuan/","/gaobeidian/","/guanzhuang/","/gongti/","/guomao/","/guozhan/",
		"/huajiadi/","/hujialou/","/hongmiao/","/huixinxijie/","/hepingjie/","/huaweiqiao/","/jianxiangqiao/",
		"/jiuxianqiao/","/jingsong/","/jianguomenwai/","/laiguangying/","/liufang/","/madian/","/panjiayuan/",
		"/shaoyaoju/","/shifoying/","/sihui/","/shuangqiao/","/shibalidian/","/sanlitun/","/shilipu/",
		"/sifangqiao/","/taiyanggong/","/tuanjiehu/","/tianshuiyuan/","/wangjing/","/xibahe/","/yaao/",
		"/yayuncun/","/yayuncunxiaoying/","/yansha/","/zuojiazhuang/","/haidian/","/fengtai/","/dongcheng/",
		"/xicheng/","/shijingshan/","/daxing/","/tongzhou/","/changping/","/tongzhou/","/daxing/","/shunyi/",
	};
	public static String loupan[]= {"/p1/","/p2/","/p3/","/p4/","/p5/","/p6/"};

	public static String RENTOUT_URL = "http://bj.5i5j.com/rent";
	public static String RESOLDAPARTMENT_URL = "http://bj.5i5j.com/exchange";
	public static String NEWBUILDING_URL = "http://bj.5i5j.com/loupan/list";
	public static String DOMAIN_URL = "http://bj.5i5j.com";


	public static void main(String[] args) {
		for(int i=9; i<regions.length; i++)
			getRentOutInfo(regions[i]);//租房


		//for(int i=0; i<loupan.length; i++)
		//	getNewBuildingInfo(loupan[i]);//新楼盘

		//for(int i=0; i<regions.length; i++) 
		//	getResoldApartmentInfo(regions[i]);//二手房
		

	}

	//抓取出租数据
	public static void getRentOutInfo(String region) {

		Vector<String> log = null;
		synchronized(BJ_RENTOUT) {
			log = FileTool.Load(LOG + File.separator + region + "_rentout.log", "UTF-8");
		}

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		Date latestdate = null;
		Date newest = null;

		if (log != null) {
			try {
				latestdate = sdf.parse(log.elementAt(0));
				latestdate = new Date(latestdate.getTime() - 1);
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

		String url = RENTOUT_URL + region;
		Vector<String> urls = new Vector<String>();

		Set<String> visited = new TreeSet<String>();
		urls.add(url);

		Parser parser = new Parser();
		boolean quit = false;

		while (urls.size() > 0) {

			url = urls.get(0);

			urls.remove(0);
			visited.add(url);

			String content = HTMLTool.fetchURL(url, "utf-8","get");
			System.out.println("下一页-->"+url);
			if (content == null) {
				continue;
			}
			try {

				parser.setInputHTML(content);
				parser.setEncoding("utf-8");


				NodeFilter filter=new AndFilter(new TagNameFilter("h2"),new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","list-info"))));
				NodeList nodes = parser.extractAllNodesThatMatch(filter);

				if( nodes!=null&& nodes.size()>0 )
{
					for (int n = 0; n < nodes.size(); n ++) {

						TagNode tn=(TagNode)nodes.elementAt(n);

						String purl=((TagNode)tn.getChildren().elementAt(0)).getAttribute("href");
						if(purl.startsWith("/rent")) {
							purl=DOMAIN_URL+purl;
							String poi2 = parseRentOut(purl);
							if (poi2 == null)
								continue;
							String poi=poi2.replace("&nbsp;", "").replace("&nbsp", "").replace("()", "");
							System.out.println(poi);
							if (poi != null) {

								int m = poi.indexOf("<TIME>");
								int k = poi.indexOf("</TIME>");

								if (m != -1 && k != -1) {
									assert(m < k);
									String tm = poi.substring(m + "<TIME>".length(), k);
									try {
										Date date = sdf.parse(tm);
										if (latestdate != null) {
											if (date.before(latestdate)) {
												quit = true;
											} else if (newest == null) {
												newest = date;
											} else {
												if (newest.before(date))
													newest = date;
											}

										}
									} catch (ParseException e) {

										e.printStackTrace();

										newest = new Date();
									}

									if (quit) {
										break;
									} else {
										synchronized(BJ_RENTOUT) {

											FileTool.Dump(poi, "D:/出租_我爱我家_北京108.txt", "UTF-8");
											
										}
									}
								}
							}

							try {
								Thread.sleep(500 * ((int) (Math.max(1, Math.random()*3))));
							} catch (final InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				}

				parser.reset();

				/*
				<div class="rent-page">
				<a href="/rent/n1" class="current">1</a>
				<a href="/rent/n2">2</a>
				<a href="/rent/n3">3</a>
				<a href="/rent/n2">下一页</a></div>
				*/
				filter=new AndFilter(new TagNameFilter("a"),new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","rent-page"))));
				nodes = parser.extractAllNodesThatMatch(filter);

				if (nodes != null) {
					TagNode tni = (TagNode) nodes.elementAt(nodes.size()-1);
					String href =DOMAIN_URL+ tni.getAttribute("href");
					if ( href != null&&href.startsWith("http")) {
						if (!visited.contains( href)) {
							int kk = 0;
							for (; kk < urls.size(); kk ++) {
								if (urls.elementAt(kk).equalsIgnoreCase( href)) {
									break;
								}
							}

							if (kk == urls.size())
								urls.add(href);
						}
					}



				}
				parser.reset();
				if (quit)
					break;
			} catch (ParserException e1) {

				e1.printStackTrace();
			}
		}

		synchronized(BJ_RENTOUT) {
			File f = new File(LOG + File.separator + region + ".log");
			f.delete();
			if (newest != null) {
				FileTool.Dump(sdf.format(newest), LOG + File.separator + region + ".log", "UTF-8");
			}
		}
	}


	private static String parseRentOut(String url) {


		System.out.println("Crawled->"+url);
		String content = HTMLTool.fetchURL(url, "utf-8","get");//GB2312是汉字书写国家标准。
		//System.out.println("url.ok!");


		Parser parser = new Parser();//获取解析器
		if (content == null) {
			return null;
		}

		String poi ="";
		try {

			parser.setInputHTML(content);
			parser.setEncoding("utf-8");


			NodeFilter filter=new AndFilter(new TagNameFilter("h2"),new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","house-main"))));
			NodeList nodes= parser.extractAllNodesThatMatch(filter);
			if (nodes != null && nodes.size() == 1) {

				String str = nodes.elementAt(0).toPlainTextString().replace("&nbsp","").replace("随时", "").replace("入住", "").replace("看房", "").replace("\r\n", "").replace("\t", "").replace("拎包", "").replace("新上房源", "").trim();

				String tt=str.replace("，","").replace("、","").replace("《","【").replace("》","】").replace("[","【").replace("]","】").replace("=", "").replace("-", "").replace(",", "").replace("！", "").trim();

				int ix = tt.indexOf("【");
				int ix2=tt.indexOf("】");
				if(ix!=-1&&ix2!=-1) {
					String t1=tt.substring(0,ix);
					String t2=tt.substring(ix2+"】".length());
					poi += "<TITLE>" + t1.replace(" ", "").trim() + t2.replace(" ", "").trim() +"</TITLE>";//大标题
				} else {
					poi += "<TITLE>" + tt.replace(" ", "").trim() + "</TITLE>";//大标题
				}
				parser.reset();


				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				poi += "<TIME>" + sdf.format(d) + "</TIME>";//发布时间

				filter = new AndFilter(new TagNameFilter("li"), new HasParentFilter(new AndFilter(new TagNameFilter("ul"), new HasAttributeFilter("class", "house-info"))));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int mm = 0; mm < 3; mm ++) {
						Node ni = nodes.elementAt(mm);

						if (ni instanceof TagNode && ((TagNode)ni).getTagName().equalsIgnoreCase("li"))

						{
							tt = ni.toPlainTextString().trim();
							tt = tt.replace("（", "(").replace("）", ")").replace("、", "");


							ix = tt.indexOf("租金");

							if (ix != -1) {
								String sub = tt.substring(ix + "租金：".length()).replace("\r\n", "").replace("\t", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1)
									if(sub.contains("整租"))
										poi+="<RENT_TYPE>整租</RENT_TYPE>";
									else if(sub.contains("合租"))
										poi+="<RENT_TYPE>合租</RENT_TYPE>";
									else
										poi+="<RENT_TYPE>暂无</RENT_TYPE>";
								poi += "<PRICE>" + sub.replace(" ", "").replace("\r\n", "").replace("\t", "").replace("整租", "").replace("合租","") + "</PRICE>";
								continue;
							}

							ix = tt.indexOf("小区");

							if (ix != -1) {

								String sub = tt.substring(ix + "小区：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<COMMUNITY>" + sub.replace(" ", "") + "</COMMUNITY>";
								continue;

							}
						}

					}
				}
				parser.reset();
				filter = new AndFilter(new TagNameFilter("li"), new HasParentFilter(new AndFilter(new TagNameFilter("ul"), new HasAttributeFilter("class", "house-info-2"))));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int mm = 0; mm < 6; mm ++) {
						Node ni = nodes.elementAt(mm);

						if (ni instanceof TagNode && ((TagNode)ni).getTagName().equalsIgnoreCase("li"))

						{
							tt = ni.toPlainTextString().trim();
							tt = tt.replace("（", "(").replace("）", ")").replace("、", "");

							ix = tt.indexOf("户型");

							if (ix != -1) {
								String sub = tt.substring(ix + "户型：".length()).replace("\r\n", "").replace("\t", "").replace("相似户型", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<HOUSE_TYPE>" + sub.replace(" ", "") + "</HOUSE_TYPE>";
								continue;
							}
							ix = tt.indexOf("装修");
							if (ix != -1) {

								String sub = tt.substring(ix + "装修：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<FITMENT>" + sub.replace(" ", "") + "</FITMENT>";

								continue;
							}

							ix = tt.indexOf("面积");
							if (ix != -1) {

								String sub = tt.substring(ix + "面积：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<AREA>" + sub.replace(" ", "") + "</AREA>";

								continue;
							}

							ix = tt.indexOf("朝向");
							if (ix != -1) {

								String sub = tt.substring(ix + "朝向：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<ORIENTATION>" + sub.replace(" ", "") + "</ORIENTATION>";

								continue;
							}

							ix = tt.indexOf("楼层");
							if (ix != -1) {

								String sub = tt.substring(ix + "楼层：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<FLOOR>" + sub.replace(" ", "") + "</FLOOR>";

								continue;
							}


							ix = tt.indexOf("年代：");
							if (ix != -1) {

								String sub = tt.substring(ix + "年代：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<BUILT_YEAR>" + sub.replace(" ", "") + "</BUILT_YEAR>";

								continue;
							}




						}

					}
				}
				parser.reset();

//##############################租房小区信息#################################
				/*
				<div class="xq-intro-info">
				<h2><a href="/community/47538" target="_blank">潘家园</a></h2>
				<p class="small-font">本小区共有<a href="/community/zf_47538#intro_8" target="_blank">租房71套</a>，
				<a href="/community/47538" target="_blank">二手房40套</a></p>
				<ul>
				<li><b>建筑年代：</b>1998-12-01</li>
				<li><b>建筑面积：</b></li>
				<li><b>所在版块：</b>朝阳-潘家园</li>
				<li><b>总户数：</b></li>
				<li><b>容积率：</b></li>
				<li><b>绿化率：</b></li>
				<li><b>物业费用：</b></li>
				<li><b>物业公司：</b></li>
				<li><b>物业类型：</b>公寓、普通住宅</li>
				<li><b>供暖方式：</b>集中供暖</li>
				<li class="w-full"><b>开发商：</b></li>
				</ul>
				<a href="/community/47538" class="btn-w-s" target="_blank"><span class="btn-view-all-icon"></span>点击查看全部</a>
				</div>
				*/

				filter = new AndFilter(new TagNameFilter("ul"), new HasParentFilter(new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "xq-intro-info"))));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {

					Node ni = nodes.elementAt(0).getFirstChild();

					do {

						tt = ni.toPlainTextString().trim();
						tt = tt.replace("（", "(").replace("）", ")").replace("、", "");

						ix = tt.indexOf("建筑年代：");

						if (ix != -1) {
							String sub = tt.substring(ix + "建筑年代：".length()).replace("\r\n", "").replace("\t", "").replace("相似户型", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<BUILT_Date>" + sub.replace(" ", "") + "</BUILT_Date>";
							ni=ni.getNextSibling();
							continue;
						}
						ix = tt.indexOf("建筑面积：");
						if (ix != -1) {

							String sub = tt.substring(ix + "建筑面积：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<TOTAL_AREA>" + sub.replace(" ", "") + "</TOTAL_AREA>";
							ni=ni.getNextSibling();
							continue;
						}

						ix = tt.indexOf("所在版块：");
						if (ix != -1) {

							String sub = tt.substring(ix + "所在版块：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<REGION>" + sub.replace(" ", "") + "</REGION>";
							ni=ni.getNextSibling();
							continue;
						}

						ix = tt.indexOf("总户数：");
						if (ix != -1) {

							String sub = tt.substring(ix + "总户数：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<HOUSEHOLDS>" + sub.replace(" ", "") + "</HOUSEHOLDS>";
							ni=ni.getNextSibling();
							continue;
						}

						ix = tt.indexOf("容积率：");
						if (ix != -1) {

							String sub = tt.substring(ix + "容积率：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<VOLUME_RATE>" + sub.replace(" ", "") + "</VOLUME_RATE>";
							ni=ni.getNextSibling();
							continue;
						}


						ix = tt.indexOf("绿化率：");
						if (ix != -1) {

							String sub = tt.substring(ix + "绿化率：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<GREEN_RATE>" + sub.replace(" ", "") + "</GREEN_RATE>";
							ni=ni.getNextSibling();
							continue;
						}

						ix = tt.indexOf("物业费用：");

						if (ix != -1) {

							String sub = tt.substring(ix + "物业费用：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY_FEE>" + sub.replace(" ", "") + "</PROPERTY_FEE>";
							ni=ni.getNextSibling();
							continue;

						}
						ix = tt.indexOf("物业公司：");

						if (ix != -1) {

							String sub = tt.substring(ix + "物业公司：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY>" + sub.replace(" ", "") + "</PROPERTY>";
							ni=ni.getNextSibling();
							continue;

						}
						ix = tt.indexOf("物业类型：");
						if (ix != -1) {

							String sub = tt.substring(ix + "物业类型：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY_TYPE>" + sub.replace(" ", "") + "</PROPERTY_TYPE>";
							ni=ni.getNextSibling();
							continue;

						}
						ix = tt.indexOf("供暖方式：");

						if (ix != -1) {

							String sub = tt.substring(ix + "供暖方式：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<HEAT_SUPPLY>" + sub.replace(" ", "") + "</HEAT_SUPPLY>";
							ni=ni.getNextSibling();
							continue;

						}
						ix = tt.indexOf("开发商：");

						if (ix != -1) {

							String sub = tt.substring(ix + "开发商：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<DEVELOPER>" + sub.replace(" ", "") + "</DEVELOPER>";
							ni=ni.getNextSibling();
							continue;

						}

						ni=ni.getNextSibling();
					} while(ni!=null);


				}
			}
		} catch (ParserException e1) {
// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//System.out.println("poi.ok!");

		return "<POI>" + poi + "<URL>" + url + "</URL></POI>";
	}


//###############        #############
//            ###             ###
//            ###       ###############
//            ###             ###
//###############       ###############
//###	                      ###
//###                      #  ###
//###                      ## ###
//###############           # ###

	//抓取二手房数据
	public static void getResoldApartmentInfo(String region) {

		Vector<String> log = null;
		synchronized(BJ_RESOLDS) {
			log = FileTool.Load(LOG + File.separator + region + "_resold.log", "UTF-8");
		}
		// 2014/12/8 17:16:42
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");//

		Date latestdate = null;
		Date newest = null;

		if (log != null) {
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

		while (urls.size() > 0) {
			url = urls.get(0);

			urls.remove(0);
			visited.add(url);

			String content = HTMLTool.fetchURL(url, "utf-8","get");
			System.out.println("下一页-->"+url);
			if (content == null) {
				continue;
			}
			try {

				parser.setInputHTML(content);
				parser.setEncoding("utf-8");


				NodeFilter filter=new AndFilter(new TagNameFilter("h2"),new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","list-info"))));
				NodeList nodes = parser.extractAllNodesThatMatch(filter);

				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n ++) {

						TagNode tn=(TagNode)nodes.elementAt(n);

						String purl=((TagNode)tn.getChildren().elementAt(0)).getAttribute("href");
						if(purl.startsWith("/exchange")) {
							purl=DOMAIN_URL+purl;
							String poi2 = parseResold(purl);
							if (poi2 == null)
								continue;
							String poi=poi2.replace("&nbsp;", "").replace("&nbsp", "").replace("()", "");
							System.out.println(poi);
							if (poi != null) {

								int m = poi.indexOf("<TIME>");
								int k = poi.indexOf("</TIME>");

								if (m != -1 && k != -1) {
									assert(m < k);
									String tm = poi.substring(m + "<TIME>".length(), k);
									try {
										Date date = sdf.parse(tm);
										if (latestdate != null) {
											if (date.before(latestdate)) {
												quit = true;
											} else if (newest == null) {
												newest = date;
											} else {
												if (newest.before(date))
													newest = date;
											}

										}
									} catch (ParseException e) {

										e.printStackTrace();

										newest = new Date();
									}

									if (quit) {
										break;
									} else {
										synchronized(BJ_RESOLDS)

										{

											FileTool.Dump(poi, "D:\\二手房_我爱我家_北京.csv", "UTF-8");
										}
									}
								}
							}

							try {
								Thread.sleep(500 * ((int) (Math
								                           .max(1, Math.random() * 3))));
							} catch (final InterruptedException e1) {
								e1.printStackTrace();
							} catch (NullPointerException e1) {

								e1.printStackTrace();
							}
						}
					}
				}

				parser.reset();

				filter=new AndFilter(new TagNameFilter("a"),new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","list-page"))));
				nodes = parser.extractAllNodesThatMatch(filter);

				if (nodes != null) {
					TagNode tni = (TagNode) nodes.elementAt(nodes.size()-1);
					String href = DOMAIN_URL+tni.getAttribute("href");
					if ( href != null&&href.startsWith("http")) {
						if (!visited.contains(href)) {
							int kk = 0;
							for (; kk < urls.size(); kk ++) {
								if (urls.elementAt(kk).equalsIgnoreCase( href)) {
									break;
								}
							}

							if (kk == urls.size())
								urls.add(href);
						}
					}


				}
				parser.reset();
				if (quit)
					break;
			} catch (ParserException e1) {

				e1.printStackTrace();
			}
		}

		synchronized(BJ_RESOLDS) {
			File f = new File(LOG + File.separator + region + ".log");
			f.delete();
			if (newest != null) {
				FileTool.Dump(sdf.format(newest), LOG + File.separator + region + ".log", "UTF-8");
			}
		}
	}


	private static String parseResold(String url) {

		System.out.println("Cralwed->"+url);
		String content = HTMLTool.fetchURL(url, "utf-8","get");//GB2312是汉字书写国家标准。
		System.out.println("url.ok!");


		Parser parser = new Parser();
		if (content == null) {
			return null;
		}

		String poi ="";
		try {

			parser.setInputHTML(content);
			parser.setEncoding("utf-8");


			NodeFilter filter=new AndFilter(new TagNameFilter("h2"),new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","house-main"))));
			NodeList nodes= parser.extractAllNodesThatMatch(filter);
			if (nodes != null && nodes.size() == 1) {

				String str = nodes.elementAt(0).toPlainTextString().replace("&nbsp","").replace("随时", "").replace("入住", "").replace("看房", "").replace("\r\n", "").replace("\t", "").replace("拎包", "").replace("新上房源%", "").trim();

				String tt=str.replace("，","").replace("、","").replace("《","【").replace("》","】").replace("[","【").replace("]","】").replace("=", "").replace("-", "").replace(",", "").replace("！", "").trim();

				int ix = tt.indexOf("【");
				int ix2=tt.indexOf("】");
				if(ix!=-1&&ix2!=-1) {
					String t1=tt.substring(0,ix);
					String t2=tt.substring(ix2+"】".length());
					poi += "<TITLE>" + t1.replace(" ", "").trim() + t2.replace(" ", "").trim() +"</TITLE>";//大标题
				} else {
					poi += "<TITLE>" + tt.replace(" ", "").trim() + "</TITLE>";//大标题
				}
				parser.reset();


				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
				poi += "<TIME>" + sdf.format(d) + "</TIME>";//发布时间

				filter = new AndFilter(new TagNameFilter("li"), new HasParentFilter(new AndFilter(new TagNameFilter("ul"), new HasAttributeFilter("class", "house-info"))));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {

					for (int mm = 0; mm < 3; mm ++) {
						Node ni = nodes.elementAt(mm);
						tt = ni.toPlainTextString().trim();
						if (mm==0) {
							/*<li>
							<b>售价：</b>
							<span class="font-price">355</span>
							万元&nbsp;&nbsp;&nbsp;
							<span>首付：</span>
							107万&nbsp;&nbsp;&nbsp;
							<span>月供：</span>
							14315元
							</li>*/

							Node nichild= ni.getFirstChild();
							do {
								tt = nichild.toPlainTextString().trim();
								ix = tt.indexOf("售价：");
								if (ix != -1) {
									nichild=nichild.getNextSibling().getNextSibling();
									tt = nichild.toPlainTextString().trim();
									String sub = tt+"万元".replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
									if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
										poi += "<PRICE>" + sub.replace(" ", "") + "</PRICE>";
									nichild=nichild.getNextSibling();
									continue;

								}

								ix = tt.indexOf("首付：");
								if (ix != -1) {
									nichild=nichild.getNextSibling();
									tt = nichild.toPlainTextString().trim();
									String sub = tt.replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
									if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
										poi += "<DOWN_PAYMENT>" + sub.replace(" ", "") + "</DOWN_PAYMENT>";
									nichild=nichild.getNextSibling();
									continue;

								}
								ix = tt.indexOf("月供：");
								if (ix != -1) {
									nichild=nichild.getNextSibling();
									tt = nichild.toPlainTextString().trim();
									String sub = tt.replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
									if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
										poi += "<MONTH_PAYMENT>" + sub.replace(" ", "") + "</MONTH_PAYMENT>";
									nichild=nichild.getNextSibling();
									continue;

								}
								nichild=nichild.getNextSibling();
							} while (nichild!=null);
						}

						else {


							if (ni instanceof TagNode && ((TagNode)ni).getTagName().equalsIgnoreCase("li"))

							{

								tt = tt.replace("（", "(").replace("）", ")").replace("、", "");

								ix = tt.indexOf("小区");

								if (ix != -1) {

									String sub = tt.substring(ix + "小区：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
									if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
										poi += "<COMMUNITY>" + sub.replace(" ", "") + "</COMMUNITY>";
									continue;

								}
							}
						}

					}
				}
				parser.reset();
				filter = new AndFilter(new TagNameFilter("li"), new HasParentFilter(new AndFilter(new TagNameFilter("ul"), new HasAttributeFilter("class", "house-info-2"))));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int mm = 0; mm < 6; mm ++) {
						Node ni = nodes.elementAt(mm);

						if (ni instanceof TagNode && ((TagNode)ni).getTagName().equalsIgnoreCase("li"))

						{
							tt = ni.toPlainTextString().trim();
							tt = tt.replace("（", "(").replace("）", ")").replace("、", "");

							ix = tt.indexOf("户型");

							if (ix != -1) {
								String sub = tt.substring(ix + "户型：".length()).replace("\r\n", "").replace("\t", "").replace("相似户型", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<HOUSE_TYPE>" + sub.replace(" ", "") + "</HOUSE_TYPE>";
								continue;
							}
							ix = tt.indexOf("单价：");
							if (ix != -1) {

								String sub = tt.substring(ix + "单价：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<UNIT_PRICE>" + sub.replace(" ", "") + "</UNIT_PRICE>";

								continue;
							}

							ix = tt.indexOf("面积");
							if (ix != -1) {

								String sub = tt.substring(ix + "面积：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<AREA>" + sub.replace(" ", "") + "</AREA>";

								continue;
							}

							ix = tt.indexOf("朝向");
							if (ix != -1) {

								String sub = tt.substring(ix + "朝向：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<ORIENTATION>" + sub.replace(" ", "") + "</ORIENTATION>";

								continue;
							}

							ix = tt.indexOf("楼层");
							if (ix != -1) {

								String sub = tt.substring(ix + "楼层：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<FLOOR>" + sub.replace(" ", "") + "</FLOOR>";

								continue;
							}


							ix = tt.indexOf("年代：");
							if (ix != -1) {

								String sub = tt.substring(ix + "年代：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
								if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
									poi += "<BUILT_YEAR>" + sub.replace(" ", "") + "</BUILT_YEAR>";

								continue;
							}




						}

					}
				}
				parser.reset();

//##############################二手房小区信息#################################
				/*
				<div class="xq-intro-info">
				<h2><a href="/community/47538" target="_blank">潘家园</a></h2>
				<p class="small-font">本小区共有<a href="/community/zf_47538#intro_8" target="_blank">租房71套</a>，
				<a href="/community/47538" target="_blank">二手房40套</a></p>
				<ul>
				<li><b>建筑年代：</b>1998-12-01</li>
				<li><b>建筑面积：</b></li>
				<li><b>所在版块：</b>朝阳-潘家园</li>
				<li><b>总户数：</b></li>
				<li><b>容积率：</b></li>
				<li><b>绿化率：</b></li>
				<li><b>物业费用：</b></li>
				<li><b>物业公司：</b></li>
				<li><b>物业类型：</b>公寓、普通住宅</li>
				<li><b>供暖方式：</b>集中供暖</li>
				<li class="w-full"><b>开发商：</b></li>
				</ul>
				<a href="/community/47538" class="btn-w-s" target="_blank"><span class="btn-view-all-icon"></span>点击查看全部</a>
				</div>
				*/

				filter = new AndFilter(new TagNameFilter("ul"), new HasParentFilter(new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "xq-intro-info"))));
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {

					Node ni = nodes.elementAt(0).getFirstChild();

					do {

						tt = ni.toPlainTextString().trim();
						tt = tt.replace("（", "(").replace("）", ")").replace("、", "");

						ix = tt.indexOf("建筑年代：");

						if (ix != -1) {
							String sub = tt.substring(ix + "建筑年代：".length()).replace("\r\n", "").replace("\t", "").replace("相似户型", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<BUILT_YEAR>" + sub.replace(" ", "") + "</BUILT_YEAR>";
							ni=ni.getNextSibling();
							continue;
						}
						ix = tt.indexOf("建筑面积：");
						if (ix != -1) {

							String sub = tt.substring(ix + "建筑面积：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<TOTAL_AREA>" + sub.replace(" ", "") + "</TOTAL_AREA>";
							ni=ni.getNextSibling();
							continue;
						}

						ix = tt.indexOf("所在版块：");
						if (ix != -1) {

							String sub = tt.substring(ix + "所在版块：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<REGION>" + sub.replace(" ", "") + "</REGION>";
							ni=ni.getNextSibling();
							continue;
						}

						ix = tt.indexOf("总户数：");
						if (ix != -1) {

							String sub = tt.substring(ix + "总户数：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<HOUSEHOLDS>" + sub.replace(" ", "") + "</HOUSEHOLDS>";
							ni=ni.getNextSibling();
							continue;
						}

						ix = tt.indexOf("容积率：");
						if (ix != -1) {

							String sub = tt.substring(ix + "容积率：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<VOLUME_RATE>" + sub.replace(" ", "") + "</VOLUME_RATE>";
							ni=ni.getNextSibling();
							continue;
						}


						ix = tt.indexOf("绿化率：");
						if (ix != -1) {

							String sub = tt.substring(ix + "绿化率：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<GREEN_RATE>" + sub.replace(" ", "") + "</GREEN_RATE>";
							ni=ni.getNextSibling();
							continue;
						}

						ix = tt.indexOf("物业费用：");

						if (ix != -1) {

							String sub = tt.substring(ix + "物业费用：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY_FEE>" + sub.replace(" ", "") + "</PROPERTY_FEE>";
							ni=ni.getNextSibling();
							continue;

						}
						ix = tt.indexOf("物业公司：");

						if (ix != -1) {

							String sub = tt.substring(ix + "物业公司：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY>" + sub.replace(" ", "") + "</PROPERTY>";
							ni=ni.getNextSibling();
							continue;

						}
						ix = tt.indexOf("物业类型：");
						if (ix != -1) {

							String sub = tt.substring(ix + "物业类型：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY_TYPE>" + sub.replace(" ", "") + "</PROPERTY_TYPE>";
							ni=ni.getNextSibling();
							continue;

						}
						ix = tt.indexOf("供暖方式：");

						if (ix != -1) {

							String sub = tt.substring(ix + "供暖方式：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<HEAT_SUPPLY>" + sub.replace(" ", "") + "</HEAT_SUPPLY>";
							ni=ni.getNextSibling();
							continue;

						}
						ix = tt.indexOf("开发商：");

						if (ix != -1) {

							String sub = tt.substring(ix + "开发商：".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<DEVELOPER>" + sub.replace(" ", "") + "</DEVELOPER>";
							ni=ni.getNextSibling();
							continue;

						}

						ni=ni.getNextSibling();
					} while(ni!=null);


				}
			}
		} catch (ParserException e1) {

			e1.printStackTrace();
		}
		System.out.println("poi.ok!");
		//函数定义在920行
		if (poi != null) {
			poi = poi.replace("&nbsp;", "").replace("&nbsp", "");
			int ss = poi.indexOf("[");
			while (ss != -1) {
				int ee = poi.indexOf("]", ss + 1);
				if (ee != -1) {
					String sub = poi.substring(ss, ee + 1);
					poi = poi.replace(sub, "");
				} else
					break;
				ss = poi.indexOf("[", ss);
			}
		}
		return "<POI>" + poi + "<URL>" + url + "</URL></POI>";
	}

////\               ////   |||||||||||||||||   \\\\                ///\\\\                ////
////\\\             ////   |||||||||||||||||    \\\\              //// \\\\              ////
//// \\\\           ////   ||||                  \\\\            ////   \\\\            ////
////   \\\\         ////   |||||||||||||||||      \\\\          ////     \\\\          ////
////     \\\\       ////   |||||||||||||||||       \\\\        ////       \\\\        ////
////       \\\\     ////   ||||                     \\\\      ////         \\\\      ////
////         \\\\   ////   ||||                      \\\\    ////           \\\\    ////
////           \\\\ ////   |||||||||||||||||          \\\\  ////             \\\\  ////
////             \\\////   |||||||||||||||||           \\\\////               \\\\////

	//抓取新房数据

	public static void getNewBuildingInfo(String region) {
		Vector<String> log = null;
		synchronized(BJ_NEWHOUSE) {
			log = FileTool.Load(LOG + File.separator + region + "_new.log", "UTF-8");
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		Date latestdate = null;
		Date newest = null;

		if (log != null) {
			try {
				latestdate = sdf.parse(log.elementAt(0));
				latestdate = new Date(latestdate.getTime() - 1);
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

		String url = NEWBUILDING_URL + region;
		Vector<String> urls = new Vector<String>();

		Set<String> visited = new TreeSet<String>();
		urls.add(url);

		Parser parser = new Parser();
		boolean quit = false;

		while (urls.size() > 0) {

			url = urls.get(0);

			urls.remove(0);
			visited.add(url);

			String content = HTMLTool.fetchURL(url, "utf-8","get");
			System.out.println("下一页-->"+url);
			if (content == null) {
				continue;
			}
			try {

				parser.setInputHTML(content);


				NodeFilter filter=new AndFilter(new TagNameFilter("a"),new HasParentFilter(new AndFilter(new TagNameFilter("p"), new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class","h-content-m"))))));
				NodeList nodes= parser.extractAllNodesThatMatch(filter);

				if (nodes != null) {
					NodeList ll= nodes.extractAllNodesThatMatch(new TagNameFilter("a"));
					for (int n = 0; n < ll.size(); n ++) {

						TagNode tn =  (TagNode)nodes.elementAt(n);

						String purl = tn.getAttribute("href");

						if(purl.startsWith("/loupan")) {
							purl=DOMAIN_URL+purl;
							String poi2 = parseNewBuilding(purl);
							String poi=poi2.replace("&nbsp;", "").replace("&nbsp", "").replace("()", "");
							System.out.println(poi);

							if (poi != null) {

								int m = poi.indexOf("<TIME>");
								int k = poi.indexOf("</TIME>");

								if (m != -1 && k != -1) {
									assert(m < k);
									String tm = poi.substring(m + "<TIME>".length(), k);
									try {
										Date date = sdf.parse(tm);
										if (latestdate != null) {
											if (date.before(latestdate)) {
												quit = true;
											} else if (newest == null) {
												newest = date;
											} else {
												if (newest.before(date))
													newest = date;
											}

										}
									} catch (ParseException e) {
										e.printStackTrace();

										newest = new Date();
									}

									if (quit) {
										break;
									} else {
										synchronized(BJ_NEWHOUSE) {

											FileTool.Dump(poi, "D:/新房_我爱我家_北京.csv", "UTF-8");
										}
									}
								}
							}

							try {
								Thread.sleep(500 * ((int) (Math
								                           .max(1, Math.random() * 3))));
							} catch (final InterruptedException e1) {

								e1.printStackTrace();
							}

						}
					}
				}

				parser.reset();


				filter=new AndFilter(new TagNameFilter("a"),new HasParentFilter(new AndFilter(new TagNameFilter("li"),new HasAttributeFilter("class","downpage"))));

				nodes = parser.extractAllNodesThatMatch(filter);

				if (nodes != null&& nodes.size()==1) {
					TagNode tni = (TagNode) nodes.elementAt(0);
					String href = DOMAIN_URL+tni.getAttribute("href");
					if ( href != null&&href.startsWith("http")) {
						if (!visited.contains(href)) {
							int kk = 0;
							for (; kk < urls.size(); kk ++) {
								if (urls.elementAt(kk).equalsIgnoreCase(href)) {
									break;
								}
							}

							if (kk == urls.size())
								urls.add(href);
						}
					}

				}


				parser.reset();
				if (quit)
					break;
			}

			catch (ParserException e1) {

				e1.printStackTrace();
			}
		}


		synchronized(BJ_NEWHOUSE) {
			File f = new File(LOG + File.separator + region + ".log");
			f.delete();
			if (newest != null) {
				FileTool.Dump(sdf.format(newest), LOG + File.separator + region + ".log", "UTF-8");
			}
		}
	}


	private static String parseNewBuilding(String url) {
		System.out.println("Crawled:"+url);
		String content = HTMLTool.fetchURL(url, "utf-8","get");//GB2312是汉字书写国家标准。
		System.out.println("url..ok!");

		Parser parser = new Parser();
		if (content == null) {
			return null;
		}

		String poi ="";
		try {

			parser.setInputHTML(content);
			parser.setEncoding("utf-8");

			/*<h3>龙湖双珑原著</h3>*/
			NodeFilter filter = new AndFilter(new TagNameFilter("h3"), new HasParentFilter(new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "h-con"))));
			NodeList nodes= parser.extractAllNodesThatMatch(filter);
			if (nodes != null && nodes.size() == 1) {

				String str = nodes.elementAt(0).toPlainTextString().replace("\r\n", "").replace("\t", "").trim();
				if(str!=null)
					poi += "<TITLE>" + str.replace(" ", "").trim() +"</TITLE>";//标题
			}
			parser.reset();

			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			poi += "<TIME>" + sdf.format(d) + "</TIME>";

			parser.reset();

			/*<ul>
			<li><strong>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：</strong><b>1900-3850万元/套</b></li>
			<li><strong>均&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：</strong><b>50000-70000元/平米</b></li>
			<li><strong>开盘时间：</strong>2013年6月</li>
			<li><strong>主力户型：</strong>类独栋（360-540平米）</li>
			<li><strong>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</strong>朝阳京密路与顺黄路交叉口往西1000米</li>
			</ul>*/
			filter = new AndFilter(new TagNameFilter("ul"), new HasParentFilter(new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "h-con-font"))));
			nodes = parser.extractAllNodesThatMatch(filter);
			if(nodes!=null&&nodes.size()==1) {
				String str = nodes.elementAt(0).toPlainTextString().replace("\r\n", "").replace("\t", "").replace("，", "").replace(",", "").replace("&nbsp;", "").trim();

				int ix=str.indexOf("总价：");
				int ix2=str.indexOf("均价");
				if(ix!=-1&&ix2!=-1) {
					String sub=str.substring(ix+"总价：".length(), ix2).trim();
					poi+="<PRICE>"+sub+"</PRICE>";
				}
				ix=str.indexOf("均价：");
				ix2=str.indexOf("开盘时间");
				if(ix!=-1&&ix2!=-1) {
					String sub=str.substring(ix+"均价：".length(), ix2).replace("元/平米", "").trim();
					poi+="<UNIT_PRICE>"+sub+"元/平米</UNIT_PRICE>";
				}

				ix=str.indexOf("开盘时间：");
				ix2=str.indexOf("主力");
				if(ix!=-1&&ix2!=-1) {
					String sub=str.substring(ix+"开盘时间：".length(), ix2).trim();
					if(sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
						poi+="<OPENING_DATE>"+sub+"</OPENING_DATE>";
				}

				ix=str.indexOf("主力户型：");
				ix2=str.indexOf("地址");
				if(ix!=-1&&ix2!=-1) {
					String sub=str.substring(ix+"主力户型".length(), ix2).replace("&nbsp", "").replace(" ", "").trim();
					if(sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
						poi+="<TYPE>"+sub+"</TYPE>";
				}

				ix=str.indexOf("地址：");
				ix2=str.indexOf("4008");
				if(ix!=-1&&ix2!=-1) {
					String sub=str.substring(ix+"地址".length(), ix2).trim();
					if(sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
						poi+="<ADDRESS>"+sub+"</ADDRESS>";
				}

			}
			parser.reset();
			/*
			<li class="w450"><strong>所在城市</strong>：北京</li>
			<li class="w450"><strong>装修情况</strong>：毛坯</li>
			<li class="w450"><strong>容&nbsp;积&nbsp;&nbsp;率</strong>：2.0</li>
			<li class="w450"><strong>入住时间</strong>：预计2017年6月一期入住</li>
			<li class="w450"><strong>产权年限</strong>：70年</li>
			<li class="w450"><strong>绿&nbsp;化&nbsp;&nbsp;率</strong>： 30%</li>
			<li class="w450"><strong>项目均价</strong>：7000</li>
			<li class="w450"><strong>开&nbsp;发&nbsp;&nbsp;商</strong>：大厂京御房地产开发有限公司 </li>
			<li class="w450"><strong>占地面积</strong>：55000平方米 </li>
			<li class="w450"><strong>项目地址</strong>：廊坊通州东侧潮白河畔友谊大桥东侧（距国贸三十公里）</li>
			<li class="w960"><strong>小区配套</strong>：1)通州商圈：通州区域知名的商场和超市有蓝岛大厦、贵友大厦、淘宝城、九棵树家乐福、乐天玛特、物美超市、新世纪华联、苏宁电器等，主要位于八通线和新华大街区域，娱乐休闲配套有位于八里桥的KTV一条街、车站路的餐饮街、北苑博纳电影院；知名休闲酒店有月亮河度假酒店、运河湾酒店、隆鹤温泉。另外通州拥有多家公园如：占地17000亩的大运河森林公园、梨园公园、西海子公园以及韩美林艺术馆、宋庄艺术馆；
			<li class="w960"><strong>交通配套</strong>：地铁：临近地铁六号线，八通线； 周边公车：910路车友谊大桥东站下车即到。</li>
			<li class="w960"><strong>楼层情况</strong>：1-3号楼为9层花园洋房产品，4、5、7号楼为11层小高层产品，6号楼为24层高层产品、8、9、10为31层高层产品。</li>
			<li class="w450"><strong>项目简称</strong>：学府澜湾</li>
			<li class="w450"><strong>建筑面积</strong>：124000平方米 </li>
			<li class="w450"><strong>套内面积</strong>：暂无资料 </li>
			<li class="w450"><strong>建筑结构</strong>：暂无资料</li>
			<li class="w450"><strong>物业公司</strong>：暂无资料</li>
			<li class="w450"><strong>总&nbsp;套&nbsp;&nbsp;数</strong>：12561256户</li>
			<li class="w450"><strong>开盘日期</strong>：2015年2月7日</li>
			<li class="w450"><strong>车&nbsp;位&nbsp;&nbsp;费</strong>：暂无资料</li>
			<li class="w450"><strong>车位比例</strong>：暂无资料</li>
			<li class="w450"><strong>物&nbsp;业&nbsp;&nbsp;费</strong>：暂无资料</li>
			<li class="w450"><strong>物业类型</strong>：暂无资料</li>
			<li class="w450"><strong>项目方位</strong>：大厂</li>
			<li class="w450"><strong>销售许可证</strong>：暂无资料</li>

			*/
			filter = new AndFilter(new TagNameFilter("li"), new OrFilter( new HasAttributeFilter("class", "w450"),new HasAttributeFilter("class", "w960")));
			nodes = parser.extractAllNodesThatMatch(filter);
			if (nodes != null) {
				for (int mm = 0; mm < nodes.size(); mm ++) {
					Node ni = nodes.elementAt(mm);

					if (ni instanceof TagNode && ((TagNode)ni).getTagName().equalsIgnoreCase("li"))

					{
						String tt = ni.toPlainTextString().trim();
						tt = tt.replace("（", "(").replace("）", ")").replace("、", ",").replace("&nbsp;","").replace(":","");

						int ix = tt.indexOf("所在城市");
						if (ix != -1) {

							String sub = tt.substring(ix + "所在城市".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<CITY>" + sub.replace(" ", "") + "</CITY>";

							continue;
						}
						ix = tt.indexOf("装修情况");
						if (ix != -1) {

							String sub = tt.substring(ix + "装修情况".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<FITMENT>" + sub.replace(" ", "") + "</FITMENT>";

							continue;
						}
						ix = tt.indexOf("容积率");
						if (ix != -1) {

							String sub = tt.substring(ix + "容积率".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<VOLUME_RATE>" + sub.replace(" ", "") + "</VOLUME_RATE>";

							continue;
						}
						ix = tt.indexOf("入住时间");

						if (ix != -1) {
							String sub = tt.substring(ix + "入住时间".length()).replace("\r\n", "").replace("\t", "").replace("开盘通知我", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROSSESSION_DATE>" + sub.replace(" ", "") + "</PROSSESSION_DATE>";
							continue;
						}
						if (ix != -1) {

							String sub = tt.substring(ix + "产权年限".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<EQUILT_TIME>" + sub.replace(" ", "") + "</EQUILT_TIME>";

							continue;
						}

						ix = tt.indexOf("绿化率");
						if (ix != -1) {

							String sub = tt.substring(ix + "绿化率".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").replace("(绿化率高)","").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<GREEN_RATE>" + sub.replace(" ", "") + "</GREEN_RATE>";

							continue;
						}
						ix = tt.indexOf("项目均价");
						if (ix != -1) {

							String sub = tt.substring(ix + "项目均价".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").replace("(绿化率高)","").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<AVERAGE_PRICE>" + sub.replace(" ", "") + "</AVERAGE_PRICE>";

							continue;
						}
						ix = tt.indexOf("开发商");
						if (ix != -1) {

							String sub = tt.substring(ix + "开发商".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<DEVELOPER>" + sub.replace(" ", "") + "</DEVELOPER>";

							continue;

						}
						ix = tt.indexOf("占地面积");
						if (ix != -1) {

							String sub = tt.substring(ix + "占地面积".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROJECT_AREA>" + sub.replace(" ", "") + "</PROJECT_AREA>";

							continue;

						}
						ix = tt.indexOf("项目地址");
						if (ix != -1) {

							String sub = tt.substring(ix + "项目地址".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROJECT_ADDRESS>" + sub.replace(" ", "") + "</PROJECT_ADDRESS>";

							continue;

						}
						ix = tt.indexOf("小区配套");
						if (ix != -1) {

							String sub = tt.substring(ix + "小区配套".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<NEARBY_DISTRICT>" + sub.replace(" ", "") + "</NEARBY_DISTRICT>";

							continue;

						}
						ix = tt.indexOf("交通配套");
						if (ix != -1) {

							String sub = tt.substring(ix + "交通配套".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<NEARBY_TRAFFIC>" + sub.replace(" ", "") + "</NEARBY_TRAFFIC>";

							continue;

						}
						ix = tt.indexOf("楼层情况");
						if (ix != -1) {

							String sub = tt.substring(ix + "楼层情况".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<FLOORS_LAYOUT>" + sub.replace(" ", "") + "</FLOORS_LAYOUT>";

							continue;

						}
						ix = tt.indexOf("项目简称");
						if (ix != -1) {

							String sub = tt.substring(ix + "项目简称".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROJECT_NAME>" + sub.replace(" ", "") + "</PROJECT_NAME>";

							continue;

						}
						ix = tt.indexOf("建筑面积");
						if (ix != -1) {

							String sub = tt.substring(ix + "建筑面积".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<BUILDING_AREA>" + sub.replace(" ", "") + "</BUILDING_AREA>";

							continue;

						}
						ix = tt.indexOf("套内面积");
						if (ix != -1) {

							String sub = tt.substring(ix + "套内面积".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<HOUSE_AREA>" + sub.replace(" ", "") + "</HOUSE_AREA>";

							continue;

						}
						ix = tt.indexOf("建筑类型");

						if (ix != -1) {

							String sub = tt.substring(ix + "建筑类型".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").replace(",", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<BUILDING_TYPE>" + sub.replace(" ", "") + "</BUILDING_TYPE>";

							continue;
						}

						ix = tt.indexOf("物业公司");
						if (ix != -1) {

							String sub = tt.substring(ix + "物业公司".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY>" + sub.replace(" ", "") + "</PROPERTY>";

							continue;

						}
						ix = tt.indexOf("总套数");

						if (ix != -1) {
							String sub = tt.substring(ix + "总套数".length()).replace("\r\n", "").replace("\t", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<HOUSEHOLDS>" + sub.replace(" ", "") + "</HOUSEHOLDS>";
							continue;
						}
						ix = tt.indexOf("开盘日期");

						if (ix != -1) {
							String sub = tt.substring(ix + "开盘日期".length()).replace("\r\n", "").replace("\t", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<OPENING_DATE>" + sub.replace(" ", "") + "</OPENING_DATE>";
							continue;
						}

						ix = tt.indexOf("车位费");

						if (ix != -1) {
							String sub = tt.substring(ix + "车位费".length()).replace("\r\n", "").replace("\t", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PARKING_FEE>" + sub.replace(" ", "") + "</PARKING_FEE>";
							continue;
						}
						ix = tt.indexOf("车位比例");

						if (ix != -1) {
							String sub = tt.substring(ix + "车位比例".length()).replace("\r\n", "").replace("\t", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PARKING_RATIO>" + sub.replace(" ", "") + "</PARKING_RATIO>";
							continue;
						}

						ix = tt.indexOf("物业费");

						if (ix != -1) {

							String sub = tt.substring(ix + "物业费".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY_FEE>" + sub.replace(" ", "") + "</PROPERTY_FEE>";
							ni=ni.getNextSibling();
							continue;

						}

						ix = tt.indexOf("物业类型");
						if (ix != -1) {

							String sub = tt.substring(ix + "物业类型".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROPERTY_TYPE>" + sub.replace(" ", "") + "</PROPERTY_TYPE>";
							ni=ni.getNextSibling();
							continue;

						}



						ix = tt.indexOf("项目方位");
						if (ix != -1) {

							String sub = tt.substring(ix + "项目方位".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<PROJECT_REGION>" + sub.replace(" ", "") + "</PROJECT_REGION>";
							continue;
						}


						ix = tt.indexOf("销售许可证");

						if (ix != -1) {

							String sub = tt.substring(ix + "销售许可证".length()).replace("\r\n", "").replace("\t", "").replace(" ", "").trim();
							if (sub!=null&&sub.indexOf("暂无") == -1&&sub.indexOf("待定")==-1)
								poi += "<LICENSE>" + sub.replace(" ", "") + "</LICENSE>";

							continue;
						}






					}
				}



				System.out.println("poi.ok!");

				return "<POI>" + poi + "<URL>" + url + "</URL></POI>";
			}




		} catch (ParserException e1) {

			e1.printStackTrace();
		}

		if (poi != null) {
			poi = poi.replace("&nbsp;", "").replace("&nbsp", "");
			int ss = poi.indexOf("[");
			while (ss != -1) {
				int ee = poi.indexOf("]", ss + 1);
				if (ee != -1) {
					String sub = poi.substring(ss, ee + 1);
					poi = poi.replace(sub, "");
				} else
					break;
				ss = poi.indexOf("[", ss);
			}
		}
		return poi;
	}

}


