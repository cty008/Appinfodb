package dao.backend;

import java.util.List;

import pojo.AppCategory;
import pojo.AppInfo;
import pojo.AppVersion;
import pojo.BackendUser;
import pojo.DataDictionary;

public interface BackendDao {
	// ��½
	public BackendUser LoginBack(String userCode, String userPassword);

	// ��ѯ�б�
	public List<AppInfo> BackInfo(AppInfo appInfo);

	// ���ݿ�����Id��ѯ�б�����
	public int countPage(AppInfo appInfo);

	// ����������ƽ̨
	public List<DataDictionary> getflatformId();

	// ��ѯ����������
	public List<AppCategory> getcategoryLevel1();

	// ���ݸ���Id��ѯ�Ӽ�������
	public List<AppCategory> getChildrenByParentId(Integer parentId);
	
	//�鿴������Ϣ
	public AppInfo appview(int id);
	
	//����appId��ѯ�汾��Ϣ
	public AppVersion UpVersionInfo(int id);
	
	//���  ����Id�޸�״̬
	public int UpdateInfo(int statusId,int id);
}
