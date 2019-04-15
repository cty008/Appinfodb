package dao.backend;

import java.util.List;

import pojo.AppCategory;
import pojo.AppInfo;
import pojo.AppVersion;
import pojo.BackendUser;
import pojo.DataDictionary;

public interface BackendDao {
	// 登陆
	public BackendUser LoginBack(String userCode, String userPassword);

	// 查询列表
	public List<AppInfo> BackInfo(AppInfo appInfo);

	// 根据开发者Id查询列表行数
	public int countPage(AppInfo appInfo);

	// 下拉框所属平台
	public List<DataDictionary> getflatformId();

	// 查询父级下拉框
	public List<AppCategory> getcategoryLevel1();

	// 根据父级Id查询子级下拉框
	public List<AppCategory> getChildrenByParentId(Integer parentId);
	
	//查看基础信息
	public AppInfo appview(int id);
	
	//根据appId查询版本信息
	public AppVersion UpVersionInfo(int id);
	
	//审核  根据Id修改状态
	public int UpdateInfo(int statusId,int id);
}
