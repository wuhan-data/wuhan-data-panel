package com.wuhan_data.app.mapper;

import java.util.List;
import java.util.Map;

import com.wuhan_data.pojo.IndexSpecial;
import com.wuhan_data.pojo.Plate;
import com.wuhan_data.pojo.indi_TF;

public interface TopicMapper {
	
	public IndexSpecial getTopicById(int id);

//*************topic1-经济发展高质量分析************	
//	-----------图表数据部分-------------
	//图表1
	public List<indi_TF> getValue1_1(); //图表1的指标数据---高质量发展指数
	//图表2
	public List<indi_TF> getValue2_1(); //图表2的指标1数据---税收收入占地方一般公共预算收入比重
	public List<indi_TF> getValue2_2(); //图表2的指标2数据---金融机构存贷比
	public List<indi_TF> getValue2_3(); //图表2的指标3数据---规模以上工业企业利润率
	public List<indi_TF> getValue2_4(); //图表2的指标4数据---居民人均可支配收入
	//图表3
	public List<indi_TF> getValue3_1(); //图表3的指标1数据---规模以上工业企业资产负债率
	//图表4
	public List<indi_TF> getValue4_1();//图表4的指标1数据---单位GDP建设用地面积
	//图表5
	public List<indi_TF> getValue5_1();//图表4的指标1数据---服务业增加值占地区生产总值比重
	//图表6
	public List<indi_TF> getValue6_1();//图表5的指标1数据---高新技术产业增加值			
//	-----------card部分-------------
	//card1部分
	public String getTopic1Card1_1();
	public String getTopic1Card1_2();
	public String getTopic1Card1_3();	
	public String getTopic1Card1_4();	
	//card2部分
	public String getTopic1Card2_1();
	//card3部分
	public String getTopic1Card3_1();
	//card4部分
	public String getTopic1Card4_1();
	//card5部分
	public String getTopic1Card5_1();

	
//*************topic2-宏观经济************	
//	-----------图表数据部分-------------
	public List<indi_TF> getTopic2Value1_1(); //图表1的指标数据--宏观综合景气评分	
	public List<indi_TF> getTopic2Value2_1(); //图表2的指标数据--地区生产总值（湖北省）
	public List<indi_TF> getTopic2Value2_2(); //图表2的指标数据--地区生产总值（全国）
	public List<indi_TF> getTopic2Value3_1(); //图表3的指标数据--税收收入占地方一般公共预算收入比重	
	public List<indi_TF> getTopic2Value4_1(); //图表4的指标数据--固定资产投资（不含农户）完成额
	public List<indi_TF> getTopic2Value4_2(); //图表4的指标数据--社会消费品零售总额
	public List<indi_TF> getTopic2Value4_3(); //图表4的指标数据--出口总额	
	public List<indi_TF> getTopic2Value5_1(); //图表5的指标数据--投资贡献率
	public List<indi_TF> getTopic2Value5_2(); //图表5的指标数据--消费贡献率	
	public List<indi_TF> getTopic2Value6_1(); //图表5的指标数据--第二产业拉动百分点
	public List<indi_TF> getTopic2Value6_2(); //图表5的指标数据--第三产业拉动百分点
	public List<indi_TF> getTopic2Value7_1(); //图表7的指标数据--居民人均可支配收入
	public List<indi_TF> getTopic2Value7_2(); //图表7的指标数据--居民人均可支配收入	
	public List<indi_TF> getTopic2Value8_1(); //图表8的指标数据--规模以上工业企业利润
	public List<indi_TF> getTopic2Value8_2(); //图表8的指标数据--规模以上工业企业利润	
	public List<indi_TF> getTopic2Value9_1(); //图表8的指标数据--地方一般公共预算收入
	public List<indi_TF> getTopic2Value9_2(); //图表8的指标数据--地方一般公共预算收入
//	-----------card部分-------------
	public String getTopic2Card1_1();
	public String getTopic2Card2_1();	
	public String getTopic2Card3_1();
	public String getTopic2Card3_2();
	public String getTopic2Card3_3();
	public String getTopic2Card4_1();
	public String getTopic2Card4_2();
	public String getTopic2Card5_1();
	public String getTopic2Card5_2();
	public String getTopic2Card6_1();
	public String getTopic2Card7_1();
	public String getTopic2Card8_1();
	
	//*************topic3-产业经济************	
//	-----------图表数据部分-------------
	public List<indi_TF> getTopic3Value1_1(); //图表1的指标数据--工业综合景气评分
	public List<indi_TF> getTopic3Value2_1(); //图表2的指标数据--规模以上工业增加值增速
	public List<indi_TF> getTopic3Value3_1(); //图表3的指标数据--规模以上工业主营业务收入（累计值）
	public List<indi_TF> getTopic3Value3_2(); //图表3的指标数据--规模以上工业主营业务收入（增速）
	public List<indi_TF> getTopic3Value3_3(); //图表3的指标数据--规模以上服务业企业营业收入（累计值）
	public List<indi_TF> getTopic3Value3_4(); //图表3的指标数据--规模以上服务业企业营业收入（增速）
	public List<indi_TF> getTopic3Value3_5(); //图表3的指标数据--规模以上工业企业利润率
	public List<indi_TF> getTopic3Value3_6(); //图表3的指标数据--规模以上服务业企业利润率
	public List<indi_TF> getTopic3Value4_1(); //图表4的指标数据--互联网宽带接入用户数
	public List<indi_TF> getTopic3Value5_1(); //图表5的指标数据--金融机构本外币贷款余额
	
//	-----------card部分-------------
	public String getTopic3Card1_1();
	public String getTopic3Card2_1();
	public String getTopic3Card2_2();
	public String getTopic3Card3_1();
	public String getTopic3Card4_1();
	
	//*************topic4-固定资产投资************	
//	-----------图表数据部分-------------
	public List<indi_TF> getTopic4Value1_1();  //图表1的指标数据--投资先行指数
	public List<indi_TF> getTopic4Value2_1();  //图表2的指标数据--固定资产投资（不含农户）完成额(累计值)
	public List<indi_TF> getTopic4Value2_2();  //图表2的指标数据--固定资产投资（不含农户）完成额（增速）	
	public List<indi_TF> getTopic4Value3_1();  //图表3的指标数据--本年新开工项目个数（累计值）
	public List<indi_TF> getTopic4Value3_2();  //图表3的指标数据--本年新开工项目个数（累计增速）
	public List<indi_TF> getTopic4Value3_3();  //图表3的指标数据--施工项目个数（累计值）
	public List<indi_TF> getTopic4Value3_4();  //图表3的指标数据--施工项目个数（累计增速）
	public List<indi_TF> getTopic4Value4_1();  //图表4的指标数据--固定资产投资对GDP的拉动（累计值）
	public List<indi_TF> getTopic4Value51_1();  //图表4的指标数据--民间投资
	public List<indi_TF> getTopic4Value51_2();  //图表4的指标数据--民间投资占固定资产投资比重
//	-----------card部分-------------
	public String getTopic4Card1_1();
	public String getTopic4Card2_1();
	public String getTopic4Card3_1();
	public String getTopic4Card4_1();
	
	
	
}
