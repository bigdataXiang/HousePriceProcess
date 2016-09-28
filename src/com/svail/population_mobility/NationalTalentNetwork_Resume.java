package com.svail.population_mobility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
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

public class NationalTalentNetwork_Resume{
	public static void main(String[] args) throws IOException {
		getResume();

	}

	public static void getResume() throws IOException {
		String resume = "";
		try {
			Vector<String> pois = FileTool.Load("D:/zhouxiang/人口数据/简历数据/resume-link.txt", "utf-8");
			for (int n = 0; n < pois.size(); n++) {
				String rurl = pois.get(n);
				String poi = parseResume(rurl);
				if(poi!=null){
					resume = "<POI>" + poi.replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "").trim()
							+ "</POI>";
					FileTool.Dump(resume, "D:/zhouxiang/人口数据/简历数据/resume.txt", "utf-8");
					System.out.println(poi);
				}
				

			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}

	}

	public static String parseResume(String url) throws IOException {
		String resume = "";
		try {
			String content = HTMLTool.fetchURL(url, "utf-8", "get");
			Parser parser = new Parser();
			if (content == null) {
				
				write_append(url, "D:/zhouxiang/人口数据/简历数据/problem-link.txt");
				return null;
			} else {
				parser.setInputHTML(content);
				parser.setEncoding("utf-8");
				HasParentFilter parentFilter2 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "resumeList")));
				NodeFilter filter = new AndFilter(new TagNameFilter("ul"), parentFilter2);
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").trim();
						//System.out.println(str);
						if (str.indexOf("出生日期：") != -1) {
							resume += "<Birthday>" + str.replace("出生日期：", "") + "</Birthday>";
							continue;
						}
						if (str.indexOf("性别：") != -1) {
							resume += "<Gender>" + str.replace("性别：", "") + "</Gender>";
							continue;
						}
						if (str.indexOf("求职状态：") != -1) {
							resume += "<Job_Status>" + str.replace("求职状态：", "") + "</Job_Status>";
							continue;
						}
						if (str.indexOf("现居住地：") != -1) {
							resume += "<Current_Residence>" + str.replace("现居住地：", "") + "</Current_Residence>";
							continue;
						}
						if (str.indexOf("户籍地址：") != -1) {
							resume += "<RegisterAddr>" + str.replace("户籍地址：", "") + "</RegisterAddr>";
							continue;
						}
						if (str.indexOf("最高学历：") != -1) {
							resume += "<Highest_Education>" + str.replace("最高学历：", "") + "</Highest_Education>";
							continue;
						}
						if (str.indexOf("最高学位：") != -1) {
							resume += "<Highest_Degree>" + str.replace("最高学位：", "") + "</Highest_Degree>";
							continue;
						}
						if (str.indexOf("工作经验：") != -1) {
							resume += "<Work_Experience>" + str.replace("工作经验：", "") + "</Work_Experience>";
							continue;
						}
						if (str.indexOf("毕业时间：") != -1) {
							resume += "<Graduation_Time>" + str.replace("毕业时间：", "") + "</Graduation_Time>";
							continue;
						}
						if (str.indexOf("专业水平：") != -1) {
							resume += "<Professional_Level>" + str.replace("专业水平：", "") + "</Professional_Level>";
							continue;
						}
						if (str.indexOf("政治面貌：") != -1) {
							resume += "<Political_Status>" + str.replace("政治面貌：", "") + "</Political_Status>";
							continue;
						}
						if (str.indexOf("民族：") != -1) {
							resume += "<National>" + str.replace("民族：", "") + "</National>";
							continue;
						}
						if (str.indexOf("身高：") != -1) {
							resume += "<Height>" + str.replace("身高：", "") + "</Height>";
							continue;
						}
						if (str.indexOf("体重：") != -1) {
							resume += "<Weight>" + str.replace("体重：", "") + "</Weight>";
							continue;
						}
						if (str.indexOf("国籍：") != -1) {
							resume += "<Nationality>" + str.replace("国籍：", "") + "</Nationality>";
							continue;
						}
						if (str.indexOf("婚姻状况：") != -1) {
							resume += "<Marital_Status>" + str.replace("婚姻状况：", "") + "</Marital_Status>";
							continue;
						}
						if (str.indexOf("证件类型：") != -1) {
							resume += "<Document_Type>" + str.replace("证件类型：", "") + "</Document_Type>";
							continue;
						}
						if (str.indexOf("证件号码：") != -1) {
							resume += "<Certificate_Number>" + str.replace("证件号码：", "") + "</Certificate_Number>";
							continue;
						}
					}
				}
				parser.reset();
				HasParentFilter parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r2")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				filter = new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolName")),
						parentFilter2);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						resume += "<School_" + (n + 1) + ">" + str + "</School_" + (n + 1) + ">";
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r2")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				HasParentFilter parentFilter3 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
						parentFilter2));
				filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);

						if (str.indexOf("所学专业：") != -1) {
							resume += "<Major>" + str.replace("所学专业：", "") + "</Major>";
							continue;
						}
						if (str.indexOf("培养方式：") != -1) {
							resume += "<Cultivation_Mode>" + str.replace("培养方式：", "") + "</Cultivation_Mode>";
							continue;
						}
						if (str.indexOf("学历：") != -1) {
							resume += "<Education>" + str.replace("学历：", "") + "</Education>";
							continue;
						}
						if (str.indexOf("学位：") != -1) {
							resume += "<Academic_Degree>" + str.replace("学位：", "") + "</Academic_Degree>";
							continue;
						}
						if (str.indexOf("专业名称：") != -1) {
							resume += "<Professional_Name>" + str.replace("专业名称：", "") + "</Professional_Name>";
							continue;
						}
						if (str.indexOf("就学城市：") != -1) {
							resume += "<School_City>" + str.replace("就学城市：", "") + "</School_City>";
							continue;
						}
						if (str.indexOf("专业描述：") != -1) {
							resume += "<Professional_Description>" + str.replace("专业描述：", "")
									+ "</Professional_Description>";
							continue;
						}
						if (str.indexOf("班级人数：") != -1) {
							resume += "<Class_Size>" + str.replace("班级人数：", "") + "</Class_Size>";
							continue;
						}
						if (str.indexOf("成绩排名：") != -1) {
							resume += "<Score_Ranking>" + str.replace("成绩排名：", "") + "</Score_Ranking>";
							continue;
						}
						if (str.indexOf("学生干部：") != -1) {
							resume += "<Student_Cadre>" + str.replace("学生干部：", "") + "</Student_Cadre>";
							continue;
						}
						if (str.indexOf("其他奖励：") != -1) {
							resume += "<Other_Awards:>" + str.replace("其他奖励：", "") + "</Other_Awards:>";
							continue;
						}
						if (str.indexOf("活动实践：") != -1) {
							resume += "<Activity_Practice>" + str.replace("活动实践：", "") + "</Activity_Practice>";
							continue;
						}
						if (str.indexOf("奖学金一：") != -1) {
							resume += "<Scholarship_One>" + str.replace("奖学金一：", "") + "</Scholarship_One>";
							continue;
						}
						if (str.indexOf("奖学金二：") != -1) {
							resume += "<Scholarship_Two>" + str.replace("奖学金二：", "") + "</Scholarship_Two>";
							continue;
						}
						if (str.indexOf("获奖时间：") != -1) {
							resume += "<Winning_Time>" + str.replace("获奖时间：", "") + "</Winning_Time>";
							continue;
						}

					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r3")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				filter = new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolName")),
						parentFilter2);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						resume += "<Train_" + (n + 1) + ">" + str + "</Train" + (n + 1) + ">";
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r3")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				parentFilter3 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
						parentFilter2));
				filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						if (str.indexOf("培训机构：") != -1) {
							resume += "<Training_Institutions>" + str.replace("培训机构：", "") + "</Training_Institutions>";
							continue;
						}
						if (str.indexOf("培训内容：") != -1) {
							resume += "<Training_Content>" + str.replace("培训内容：", "") + "</Training_Content>";
							continue;
						}
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r4")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				filter = new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolName")),
						parentFilter2);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						resume += "<Company_" + (n + 1) + ">" + str + "</Company" + (n + 1) + ">";
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r4")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				parentFilter3 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
						parentFilter2));
				filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						if (str.indexOf("单位行业：") != -1) {
							resume += "<Company_Industry>" + str.replace("单位行业：", "") + "</Company_Industry>";
							continue;
						}
						if (str.indexOf("单位性质：") != -1) {
							resume += "<Company_Property>" + str.replace("单位性质：", "") + "</Company_Property>";
							continue;
						}
						if (str.indexOf("单位规模：") != -1) {
							resume += "<Company_Size>" + str.replace("单位规模：", "") + "</Company_Size>";
							continue;
						}
						if (str.indexOf("职位类别：") != -1) {
							resume += "<Job_Category>" + str.replace("职位类别：", "") + "</Job_Category>";
							continue;
						}
						if (str.indexOf("职位名称：") != -1) {
							resume += "<Job_Title>" + str.replace("职位名称：", "") + "</Job_Title>";
							continue;
						}
						if (str.indexOf("职位级别：") != -1) {
							resume += "<Post-Level>" + str.replace("职位级别：", "") + "</Post-Level>";
							continue;
						}
						if (str.indexOf("所在部门：") != -1) {
							resume += "<Local_Department>" + str.replace("所在部门：", "") + "</Local_Department>";
							continue;
						}
						if (str.indexOf("工作方式：") != -1) {
							resume += "<Work_Way>" + str.replace("工作方式：", "") + "</Work_Way>";
							continue;
						}
						if (str.indexOf("汇报对象：") != -1) {
							resume += "<Report_Object>" + str.replace("汇报对象：", "") + "</Report_Object>";
							continue;
						}

						if (str.indexOf("下属人数：") != -1) {
							resume += "<Subordinate_Number>" + str.replace("下属人数：", "") + "</Subordinate_Number>";
							continue;
						}
						if (str.indexOf("薪酬") != -1) {
							resume += "<Pay>" + str.replace("薪酬", "") + "</Pay>";
							continue;
						}
						if (str.indexOf("工作地点：") != -1) {
							resume += "<WorkPlace>" + str.replace("工作地点：", "") + "</WorkPlace>";
							continue;
						}
						if (str.indexOf("工作描述：") != -1) {
							resume += "<Job_Description>" + str.replace("工作描述：", "") + "</Job_Description>";
							continue;
						}
						if (str.indexOf("公司描述：") != -1) {
							resume += "<Company_Description>" + str.replace("公司描述：", "") + "</Company_Description>";
							continue;
						}
						if (str.indexOf("离职原因：") != -1) {
							resume += "<Leaving_Reasons>" + str.replace("离职原因：", "") + "</Leaving_Reasons>";
							continue;
						}

					}
					parser.reset();
					parentFilter1 = new HasParentFilter(
							new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r5")));
					parentFilter2 = new HasParentFilter(new AndFilter(
							new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
							parentFilter1));
					parentFilter3 = new HasParentFilter(new AndFilter(
							new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
							parentFilter2));
					filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
					nodes = parser.extractAllNodesThatMatch(filter);
					if (nodes != null) {
						for (int n = 0; n < nodes.size(); n++) {
							TagNode tn = (TagNode) nodes.elementAt(n);
							String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
									.replace("\n", "").replace("&nbsp;", "").trim();
							//System.out.println(str);
							if (str.indexOf("近期职位名称：") != -1) {
								resume += "<Recent_Job>" + str.replace("近期职位名称：", "") + "</Recent_Job>";
								continue;
							}
							if (str.indexOf("任职时间：") != -1) {
								resume += "<Serving_Time>" + str.replace("任职时间：", "") + "</Serving_Time>";
								continue;
							}
							if (str.indexOf("税前月薪：") != -1) {
								resume += "<Pre_Tax_Monthly_Salary>" + str.replace("税前月薪：", "")
										+ "</Pre_Tax_Monthly_Salary>";
								continue;
							}
							if (str.indexOf("期望职位：") != -1) {
								resume += "<Expected_Position>" + str.replace("期望职位：", "") + "</Expected_Position>";
								continue;
							}
							if (str.indexOf("期望行业：") != -1) {
								resume += "<Expected_Industry>" + str.replace("期望行业：", "") + "</Expected_Industry>";
								continue;
							}
							if (str.indexOf("期望地区：") != -1) {
								resume += "<Expected_Area>" + str.replace("期望地区：", "") + "</Expected_Area>";
								continue;
							}
							if (str.indexOf("到岗时间：") != -1) {
								resume += "<Arrival_Time>" + str.replace("到岗时间：", "") + "</Arrival_Time>";
								continue;
							}
							if (str.indexOf("期望薪水") != -1) {
								resume += "<Expected_Salary>" + str.replace("期望薪水", "") + "</Expected_Salary>";
								continue;
							}
							if (str.indexOf("海外工作经历：") != -1) {
								resume += "<Overseas_Experience>" + str.replace("海外工作经历：", "")
										+ "</Overseas_Experience>";
								continue;
							}
						}
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r6")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				parentFilter3 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
						parentFilter2));
				filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						if (str.indexOf("自我评价：") != -1) {
							resume += "<Self_Evaluation>" + str.replace("自我评价：", "") + "</Self_Evaluation>";
							continue;
						}
						if (str.indexOf("职业目标：") != -1) {
							resume += "<Career_Goals>" + str.replace("职业目标：", "") + "</Career_Goals>";
							continue;
						}
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r7")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				filter = new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolName")),
						parentFilter2);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						resume += "<Language_" + (n + 1) + ">" + str + "</Language_" + (n + 1) + ">";
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r7")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				parentFilter3 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
						parentFilter2));
				filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						if (str.indexOf("语言水平：") != -1) {
							resume += "<Language_Level>" + str.replace("语言水平：", "") + "</Language_Level>";
							continue;
						}
						if (str.indexOf("听说能力：") != -1) {
							resume += "<Listening_Speaking>" + str.replace("听说能力：", "") + "</Listening_Speaking>";
							continue;
						}
						if (str.indexOf("读写能力：") != -1) {
							resume += "<Reading_Writing>" + str.replace("读写能力：", "") + "</Reading_Writing>";
							continue;
						}
						if (str.indexOf("等级考试：") != -1) {
							resume += "<Examination_Rank>" + str.replace("等级考试：", "") + "</Examination_Rank>";
							continue;
						}
						if (str.indexOf("考试成绩：") != -1) {
							resume += "<Score>" + str.replace("考试成绩：", "") + "</Score>";
							continue;
						}
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r8")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				filter = new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolName")),
						parentFilter2);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						resume += "<IT_Skill_" + (n + 1) + ">" + str + "</IT_Skill_" + (n + 1) + ">";
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r8")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				parentFilter3 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
						parentFilter2));
				filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						if (str.indexOf("语言水平：") != -1) {
							resume += "<Use_Level>" + str.replace("使用水平：", "") + "</Use_Level>";
							continue;
						}
						if (str.indexOf("使用时间：") != -1) {
							resume += "<Use_Time>" + str.replace("使用时间：", "") + "</Use_Time>";
							continue;
						}
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r9")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				filter = new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolName")),
						parentFilter2);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						resume += "<Project_Experience_" + (n + 1) + ">" + str + "</Project_Experience_" + (n + 1)
								+ ">";
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r9")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				parentFilter3 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
						parentFilter2));
				filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						if (str.indexOf("项目描述：") != -1) {
							resume += "<Project_Description>" + str.replace("项目描述：", "") + "</Project_Description>";
							continue;
						}
						if (str.indexOf("角色及职责：") != -1) {
							resume += "<Role_Duty>" + str.replace("角色及职责：", "") + "</Role_Duty>";
							continue;
						}
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r10")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				filter = new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolName")),
						parentFilter2);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						resume += "<Else_" + (n + 1) + ">" + str + "</Else_" + (n + 1) + ">";
					}
				}
				parser.reset();
				parentFilter1 = new HasParentFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("id", "r10")));
				parentFilter2 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "school")),
						parentFilter1));
				parentFilter3 = new HasParentFilter(new AndFilter(
						new AndFilter(new TagNameFilter("div"), new HasAttributeFilter("class", "schoolList")),
						parentFilter2));
				filter = new AndFilter(new TagNameFilter("ul"), parentFilter3);
				nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes != null) {
					for (int n = 0; n < nodes.size(); n++) {
						TagNode tn = (TagNode) nodes.elementAt(n);
						String str = tn.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "")
								.replace("\n", "").replace("&nbsp;", "").trim();
						//System.out.println(str);
						if (str.indexOf("信息描述：") != -1) {
							resume += "<Info_Description>" + str.replace("信息描述：", "") + "</Info_Description>";
							continue;
						}
					}
				}
				write_append(resume, "D:/Crawl_Test/20160104.txt");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resume;

	}

	public static void write_append(String line, String pathname) throws IOException {
		try {

			File writefile = new File(pathname);
			if (!writefile.exists()) {
				writefile.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(writefile, true), "UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(line);
			writer.write("\r\n");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
