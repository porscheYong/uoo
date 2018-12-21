package cn.ffcs.uoo.core.organization;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;

/**
 * <p>
 *  表自动生成类
 * </p>
 *
 * @author ffcs-gzb
 * @since 2018/10/15
 */
public class GeneratingObjectClass2 {
    public static String scDir = "cn.ffcs.uoo.system.controller";
    public static String scXmlDir = "src.main.resources";
    public static String authorName = "ffcs-gzb";
    public static String dbuser = "octopus";
    public static String dbpwd = "octopus";
    public static String dbUrl = "jdbc:oracle:thin:@134.96.167.19:1521:uomdb1";
//    public static String[] tabeNamesList={"TB_PERSONNEL","TB_PSNJOB","TB_FAMILY","TB_EDU","TB_CONTACT","TB_CERT"};
//    public static String modelName="uoo-personnel";
//
//    public static String modelName1="uoo-user";
//    public static String[] tabeNamesList1={"TB_ACCT","TB_ACCT_ORG","TB_ACCT_EXT","TB_SLAVE_ACCT","TB_SLAVE_ACCT_ORG_REF","TB_SLVACCT_ACCT_REF"
//    ,"TB_SLVACCT_USER_REF","TB_USER","TB_ACCT_CROSS_REL"};
//    public static String[] tabeNamesList={"TB_ORG","TB_ORG_REF","TB_ORG_PSNDOC_REF","TB_ORG_REF_TYPE","TB_ORG_TREE",
//"TB_OGT_ORG_REFTYPE_CONF","TB_OGT_ORGTYPE_CONF","TB_ORG_TYPE","TB_ORG_TYPE_REF","TB_ORG_CONTACT_REL","TB_ORG_CERT_REF",
//"TB_ORG_CROSS_REL"};
//public static String[] tabeNamesList={"TB_ORG_PSNDOC_REF","TB_ORG_CONTACT_REL","TB_ORG_CERT_REF","TB_ORG_ORGTREE_REF"};
//public static String[] tabeNamesList={"TB_ORG","TB_ORG_ORGTYPE_REL","TB_ORG_REL","TB_ORG_TYPE","TB_ORG_CROSS_REL"
//,"TB_ORG_CONTACT_REL","TB_ORG_CERT_REL","TB_ORG_TREE","TB_ORG_PERSON_REL","TB_ORG_REL_TYPE","TB_OGT_ORGTYPE_CONF"
//,"TB_OGT_ORG_RELTYPE_CONF","TB_ORG_ORGTREE_REL","TB_ORG_LEVEL"};
  //  public static String[] tabeNamesList={"TB_ORG_LEVEL"};

//    public static String[] tabeNamesList={"TB_POSITION200","TB_POST","TB_POST_LOCATION","TB_POSITION","TB_ORG_POSITION_REL"
//    ,"TB_POST2","TB_ORG_POST_REL"};
    public static String[] tabeNamesList = {"SYS_POSITION","SYS_ORGANIZATION","SYS_DEPT_POSITION_REF"};
public static String modelName="public-gg";
    public static void main(String[] args) {
        File f = new File(modelName);
        String path = f.getAbsolutePath();
        System.out.println(path);
        generateCode(tabeNamesList,path);
    }

    public static void generateCode(String[] tabelNames,String path) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 创建的文件输出目录
        gc.setOutputDir(path);
        gc.setFileOverride(true);// 覆盖已有文件
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("ffcs-gzb");// 类作者
        gc.setIdType(IdType.AUTO);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.ORACLE);
        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        dsc.setUsername(dbuser);
        dsc.setPassword(dbpwd);
        dsc.setUrl(dbUrl);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(new String[] { "TB_"});
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tabelNames); // 需要生成相关代码的表名
        // 自定义 dao 父类
        strategy.setSuperMapperClass("com.baomidou.mybatisplus.mapper.BaseMapper");
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        //src.main.java
        pc.setEntity(scDir+".entity");
        pc.setMapper(scDir+".dao");// Dao接口包名
        pc.setXml(scXmlDir+".mapper");// XML包名
        pc.setService(scDir+".service");
        pc.setServiceImpl(scDir+".service.impl");
        pc.setController(scDir+".controller");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
        System.out.println("run over");
    }


}
