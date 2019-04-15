package service.developer;

import java.util.List;

import pojo.AppCategory;
import pojo.AppInfo;
import pojo.AppVersion;
import pojo.DataDictionary;
import pojo.DevUser;

public interface DeveloperService {
	//��½
	public DevUser Logindev(String devCode,String devPassword);
	
	//��ѯ�б�
	public List<AppInfo> appInfolist(AppInfo appInfo);
		
	//���ݿ�����Id��ѯ�б�����
	public int countPage(AppInfo appInfo);
	
	// ������APP״̬
	public List<DataDictionary> getStatus();

	// ����������ƽ̨
	public List<DataDictionary> getflatformId();

	// ��ѯ����������
	public List<AppCategory> getcategoryLevel1();

	// ���ݸ���Id��ѯ�Ӽ�������Id
	public List<AppCategory> getChildrenByParentId(Integer parentId);
	
	//����APP������Ϣ
	public int addAppInfo(AppInfo appInfo);
	
	//���� ajax��̨��֤--APKName�Ƿ��Ѵ���
	public AppInfo getAPKName(String APKName);
	
	//�޸�
	public int appinfomodify(AppInfo appInfo);
		
	//�鿴
	public AppInfo appview(int id);
		
	//ɾ��(ajax�첽)
	public int delapp(int id);
	
	//�鿴��ʷ�汾��Ϣ
	public List<AppVersion> appversioninfo(int id);

	//����IDɾ��LOGOͼƬ
	public int delfile(int id);
	
	//����ID�޸����¼�
	public int saleSwitchAjax(int status,int id);
	
	//����IDɾ��apk�ļ�
	public int delapk(int id);
	
	//�����汾��Ϣ
	public int addversionsave(AppVersion appVersion);
	
	//����appId��ѯ�汾��Ϣ
	public AppVersion UpVersionInfo(int id);
		
	//�޸İ汾��Ϣ
	public int appversionmodify(AppVersion AppVersion);
	
	//��AppInfo����  �������µİ汾id
	public int addAppInfoVersionId(AppInfo appInfo);
	
	//����version��appId��ѯ����id
	public int getIdByAppId(int appId);
}
