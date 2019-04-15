package service.developer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.developer.DeveloperDao;
import pojo.AppCategory;
import pojo.AppInfo;
import pojo.AppVersion;
import pojo.DataDictionary;
import pojo.DevUser;

@Service("developerService")
public class DeveloperServiceImpl implements DeveloperService {
	
	@Autowired
	private DeveloperDao developerDao;
	
	@Override	
	public DevUser Logindev(String devCode,String devPassword) {
		return developerDao.Logindev(devCode,devPassword);
	}

	@Override
	public List<AppInfo> appInfolist(AppInfo appInfo) {
		return developerDao.appInfolist(appInfo);
	}

	@Override
	public int countPage(AppInfo appInfo) {
		return developerDao.countPage(appInfo);
	}

	@Override
	public List<DataDictionary> getStatus() {
		return developerDao.getStatus();
	}

	@Override
	public List<DataDictionary> getflatformId() {
		return developerDao.getflatformId();
	}

	@Override
	public List<AppCategory> getcategoryLevel1() {
		return developerDao.getcategoryLevel1();
	}

	@Override
	public List<AppCategory> getChildrenByParentId(Integer parentId) {
		return developerDao.getChildrenByParentId(parentId);
	}

	@Override
	public int addAppInfo(AppInfo appInfo) {
		return developerDao.addAppInfo(appInfo);
	}

	@Override
	public AppInfo getAPKName(String APKName) {
		return developerDao.getAPKName(APKName);
	}

	@Override
	public int appinfomodify(AppInfo appInfo) {
		return developerDao.appinfomodify(appInfo);
	}

	@Override
	public AppInfo appview(int id) {
		return developerDao.appview(id);
	}

	@Override
	public int delapp(int id) {
		return developerDao.delapp(id);
	}

	@Override
	public List<AppVersion> appversioninfo(int id) {
		return developerDao.appversioninfo(id);
	}

	@Override
	public int delfile(int id) {
		return developerDao.delfile(id);
	}

	@Override
	public int saleSwitchAjax(int status,int id) {
		return developerDao.saleSwitchAjax(status,id);
	}

	@Override
	public int delapk(int id) {
		return developerDao.delapk(id);
	}

	@Override
	public int addversionsave(AppVersion appVersion) {
		return developerDao.addversionsave(appVersion);
	}

	@Override
	public AppVersion UpVersionInfo(int id) {
		return developerDao.UpVersionInfo(id);
	}

	@Override
	public int appversionmodify(AppVersion AppVersion) {
		return developerDao.appversionmodify(AppVersion);
	}

	@Override
	public int addAppInfoVersionId(AppInfo appInfo) {
		return developerDao.addAppInfoVersionId(appInfo);
	}

	@Override
	public int getIdByAppId(int appId) {
		return developerDao.getIdByAppId(appId);
	}
	
}
