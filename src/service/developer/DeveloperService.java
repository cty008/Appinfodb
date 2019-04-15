package service.developer;

import java.util.List;

import pojo.AppCategory;
import pojo.AppInfo;
import pojo.AppVersion;
import pojo.DataDictionary;
import pojo.DevUser;

public interface DeveloperService {
	//登陆
	public DevUser Logindev(String devCode,String devPassword);
	
	//查询列表
	public List<AppInfo> appInfolist(AppInfo appInfo);
		
	//根据开发者Id查询列表行数
	public int countPage(AppInfo appInfo);
	
	// 下拉框APP状态
	public List<DataDictionary> getStatus();

	// 下拉框所属平台
	public List<DataDictionary> getflatformId();

	// 查询父级下拉框
	public List<AppCategory> getcategoryLevel1();

	// 根据父级Id查询子级下拉框Id
	public List<AppCategory> getChildrenByParentId(Integer parentId);
	
	//新增APP基础信息
	public int addAppInfo(AppInfo appInfo);
	
	//新增 ajax后台验证--APKName是否已存在
	public AppInfo getAPKName(String APKName);
	
	//修改
	public int appinfomodify(AppInfo appInfo);
		
	//查看
	public AppInfo appview(int id);
		
	//删除(ajax异步)
	public int delapp(int id);
	
	//查看历史版本信息
	public List<AppVersion> appversioninfo(int id);

	//根据ID删除LOGO图片
	public int delfile(int id);
	
	//根据ID修改上下架
	public int saleSwitchAjax(int status,int id);
	
	//根据ID删除apk文件
	public int delapk(int id);
	
	//新增版本信息
	public int addversionsave(AppVersion appVersion);
	
	//根据appId查询版本信息
	public AppVersion UpVersionInfo(int id);
		
	//修改版本信息
	public int appversionmodify(AppVersion AppVersion);
	
	//往AppInfo表里  增加最新的版本id
	public int addAppInfoVersionId(AppInfo appInfo);
	
	//根据version表appId查询最新id
	public int getIdByAppId(int appId);
}
