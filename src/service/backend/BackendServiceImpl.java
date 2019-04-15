package service.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.backend.BackendDao;
import pojo.AppCategory;
import pojo.AppInfo;
import pojo.AppVersion;
import pojo.BackendUser;
import pojo.DataDictionary;

@Service("backendService")
public class BackendServiceImpl implements BackendService{
	
	@Autowired
	private BackendDao backendDao;
	
	@Override
	public BackendUser LoginBack(String userCode, String userPassword) {
		return backendDao.LoginBack(userCode, userPassword);
	}

	@Override
	public List<AppInfo> BackInfo(AppInfo appInfo) {
		return backendDao.BackInfo(appInfo);
	}

	@Override
	public int countPage(AppInfo appInfo) {
		return backendDao.countPage(appInfo);
	}

	@Override
	public List<DataDictionary> getflatformId() {
		return backendDao.getflatformId();
	}

	@Override
	public List<AppCategory> getcategoryLevel1() {
		return backendDao.getcategoryLevel1();
	}

	@Override
	public List<AppCategory> getChildrenByParentId(Integer parentId) {
		return backendDao.getChildrenByParentId(parentId);
	}

	@Override
	public AppInfo appview(int id) {
		return backendDao.appview(id);
	}

	@Override
	public AppVersion UpVersionInfo(int id) {
		return backendDao.UpVersionInfo(id);
	}

	@Override
	public int UpdateInfo(int statusId, int id) {
		return backendDao.UpdateInfo(statusId, id);
	}

}
