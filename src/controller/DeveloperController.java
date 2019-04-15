package controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import pojo.AppInfo;
import pojo.AppVersion;
import pojo.DevUser;
import service.developer.DeveloperService;
import tools.Constants;

@Controller
@RequestMapping(value="/dev")
public class DeveloperController {
	@Autowired
	private DeveloperService developerService;
	
	//��ת����½ҳ��
	@RequestMapping(value="/login")
	public String Login() {
		System.out.println("Login");
		return "devlogin";
	}
	
	//��½�ж�
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String devCode,@RequestParam String devPassword,
			HttpSession session,HttpServletRequest request) {
		request.getSession().setAttribute("isLogin", devCode );	//���ص�½
		  DevUser logindev = developerService.Logindev(devCode, devPassword);
		if (logindev!=null) {
			System.out.println("dologin--if");
			session.setAttribute(Constants.devUserSession,logindev);
			return "redirect:/dev/main";
		}else {
			System.out.println("dologin--else");
			request.setAttribute("error", "�û��������벻��ȷ");
			return "devlogin";
		}
	}
	
	//�ж�session���Ƿ�����û�
	@RequestMapping(value="/main")
	public String main(HttpSession session) {
		System.out.println("main");
		if(session.getAttribute(Constants.devUserSession)==null) {
			return "redirect:/dev/login";
		}
		return "developer/main";
	}
	
	//ע��
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		System.out.println("logout");
		session.removeAttribute(Constants.devUserSession);
		return "devlogin";
	}
	
	//��ת��ѯ�б���
	@RequestMapping(value="/flatform/app/list")
	public String list() {
		System.out.println("appInfolist");
		return "redirect:/dev/_appInfolist";
	}
	
	//�첽��ѯ(ajax�첽)  ����������(���JSON��������)
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET,
	produces={"application/json;charset=UTF-8"})  
	@ResponseBody 
	public Object categorylevellist(String pid)  {
		System.out.println("�첽��ѯ ����������");
		
		String	jsonString="";
		if (pid!=null && pid!="") {									//����һ�������ѯ����������
			jsonString = JSONArray.toJSONString(developerService.getChildrenByParentId(Integer.valueOf(pid)));
		}else {														//һ������
			jsonString = JSONArray.toJSONString(developerService.getcategoryLevel1());
		}
		System.out.println(jsonString);
		return jsonString;
		
	}
	
	//��ѯ�б�
	@RequestMapping(value="/_appInfolist")
	public String _appInfolist(String querySoftwareName,String queryStatus,String queryFlatformId,
			String queryCategoryLevel1,String queryCategoryLevel2, String queryCategoryLevel3,
			String pageIndex,HttpSession session,
			AppInfo appinfo,Model model) {
		System.out.println("_appInfolist");
		if(querySoftwareName!=null) {
			appinfo.setSoftwareName(querySoftwareName);
		}
		if(queryStatus!=null && !"".equals(queryStatus)) {
			appinfo.setStatus(Integer.valueOf(queryStatus));
		}
		if(queryFlatformId!=null && !"".equals(queryFlatformId) ) {
			appinfo.setFlatformId(Integer.valueOf(queryFlatformId));
		}
		if(queryCategoryLevel1!=null && !"".equals(queryCategoryLevel1)) {
			appinfo.setCategoryLevel1(Integer.valueOf(queryCategoryLevel1));
		}
		if(queryCategoryLevel2!=null && !"".equals(queryCategoryLevel2)) {
			appinfo.setCategoryLevel2(Integer.valueOf(queryCategoryLevel2));
		}
		if(queryCategoryLevel3!=null && !queryCategoryLevel3.equals("")) {
			appinfo.setCategoryLevel3(Integer.valueOf(queryCategoryLevel3));
		}
		
		int currentPageNo=1;//��ǰҳ	
		
		if(pageIndex!=null) {//�ж���ҳ����Ϊ��
			currentPageNo=Integer.valueOf(pageIndex);
		}
		
//		Integer devId = ((DevUser)(session.getAttribute(Constants.devUserSession))).getId(); //��ȡ������Id
//		appinfo.setDevId(Integer.valueOf(devId));
		
		int totalCount=developerService.countPage(appinfo);//����ʵ��������ѯ��¼����
		//������ҳ��
		int totalPageCount=totalCount%Constants.pageSize==0?(totalCount/Constants.pageSize):(totalCount/Constants.pageSize+1);
		appinfo.setPageSize(Constants.pageSize); //ÿҳ��ʾ������
		appinfo.setCurrPage((currentPageNo-1)*Constants.pageSize); //��ǰҳ=��ǰҳ-1*ÿҳ��ʾ������
		
		model.addAttribute("totalPageCount",totalPageCount);//��ȡ�����ҳ��
		model.addAttribute("totalCount", totalCount);//���¼����
		model.addAttribute("currentPageNo", currentPageNo);//��ǰҳ
		
		model.addAttribute("statusList",developerService.getStatus());  //APP״̬������
		model.addAttribute("flatFormList",developerService.getflatformId());//����ƽ̨������
		model.addAttribute("categoryLevel1List",developerService.getcategoryLevel1());//һ������������
		
		model.addAttribute("querySoftwareName",querySoftwareName);//�������
		model.addAttribute("queryStatus",queryStatus);//APP״̬
		model.addAttribute("queryFlatformId",queryFlatformId);//����ƽ̨
		if (queryCategoryLevel1!=null) {
			model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);//һ������
		}
		if (queryCategoryLevel2!=null) {
			model.addAttribute("categoryLevel2List",developerService.getChildrenByParentId(Integer.parseInt(queryCategoryLevel1)));//��������
		}
		if (queryCategoryLevel3!=null) {
			model.addAttribute("categoryLevel3List",developerService.getChildrenByParentId(Integer.parseInt(queryCategoryLevel2)));//��������
		}
		
		model.addAttribute("appInfoList",developerService.appInfolist(appinfo)); //ת��ҳ�� ��ѯ�б��浽��������
		return "developer/appinfolist";
	}

	//��ת����APP������Ϣҳ��
	@RequestMapping(value="/flatform/app/appinfoadd")
	public String appinfoadd() {
		System.out.println("add");
		return "developer/appinfoadd";
	}
	
	//����APP������Ϣ
	@RequestMapping(value="/flatform/app/appinfoaddsave",method=RequestMethod.POST)
	public String appinfoaddsave(AppInfo appInfo,
			HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_logoPicPath",required=false) MultipartFile attach){
		
		System.out.println("--------------appinfoaddsave");
		String logoPicPath=null;
		String logoLocPath=null;
		if(!attach.isEmpty()) {
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			System.out.println(path);
			String oldFileName=attach.getOriginalFilename(); //ԭ�ļ���
			String prefix=FilenameUtils.getExtension(oldFileName);//ԭ�ļ���׺
			int filesize=200000;
			if (attach.getSize()>filesize) {//�ϴ��ļ���С���ó���200K
				request.setAttribute("fileUploadError", "�ϴ���С���ó���200K");
				return "developer/appinfoadd";
			}else if(prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("png")||
					prefix.equalsIgnoreCase("jepg")||prefix.equalsIgnoreCase("pneg")) { //�ϴ�ͼƬ��ʽ
					String fileName=appInfo.getAPKName()+".jpg";//�ϴ�LOGOͼƬ����:apk����.apk
					File targetFile=new File(path,fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
						try {
							attach.transferTo(targetFile);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							request.setAttribute("fileUploadError", "�ϴ�ʧ��");
							return "developer/appinfoadd";
						}
					logoPicPath=request.getContextPath()+"/statics/uploadfiles/"+fileName;
					logoLocPath=path+File.separator+fileName;
			}else{
					request.setAttribute("fileUploadError", "�ϴ�ͼƬ��ʽ����ȷ��");
					return "developer/appinfoadd";
				}
		}
		
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		int addAppInfo = developerService.addAppInfo(appInfo);
		if (addAppInfo>0) {
			System.out.println("add --if");
			return "redirect:/dev/_appInfolist";  //�����ɹ�
		}
		return "developer/appinfoadd";	//����ʧ��
		
		
	}
	
	//(����������Ϣ)ajax�첽����ƽ̨������
	@RequestMapping(value="/datadictionarylist.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String datadictionarylist(String tcode) {
		String jsonString = JSONArray.toJSONString(developerService.getflatformId());
		return jsonString;
	}
	
	//(����������Ϣ)ajax�첽һ������������
	@RequestMapping(value="/categorylevellist1.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object categorylevellist1(String pid) {
		String jsonString = JSONArray.toJSONString(developerService.getcategoryLevel1());
		return jsonString;
	}
	
	//�����첽  ajax��̨��֤--APKName�Ƿ��Ѵ���
	@RequestMapping(value="/apkexist.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object apkexist(@RequestParam String APKName) {
		Map<String, String> resultMap=new HashMap<String, String>();
		if (StringUtils.isNullOrEmpty(APKName)) {
			resultMap.put("APKName", "empty");
		}else {
			AppInfo apkName2 = developerService.getAPKName(APKName);
			if (apkName2!=null) {
				resultMap.put("APKName", "exist");
			}else {
				resultMap.put("APKName", "noexist");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	//��ת�޸Ļ�����Ϣjspҳ��
	@RequestMapping(value="/appinfomodify",method=RequestMethod.GET)
	public String appinfomodify(String id,Model model) {
		System.out.println("appinfomodify======="+id);
		AppInfo appview = developerService.appview(Integer.valueOf(id)); //������Ϣ
		model.addAttribute("appInfo", appview);
		return "developer/appinfomodify";
	}
	
	//�޸Ļ�����Ϣ
	@RequestMapping(value="/appinfomodifysave",method=RequestMethod.POST)
	public String appinfomodifysave(AppInfo appInfo,
			HttpSession session,HttpServletRequest request,
			String statusName,String status,
			@RequestParam(value="attach",required=false) MultipartFile attach) {
		System.out.println("�޸Ļ�����Ϣstatus"+status);
		String logoPicPath=null;
		String logoLocPath=null;
		if(!attach.isEmpty()) {
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			System.out.println(path);
			String oldFileName=attach.getOriginalFilename(); //ԭ�ļ���
			String prefix=FilenameUtils.getExtension(oldFileName);//ԭ�ļ���׺
			int filesize=200000;
			System.out.println(attach.getSize());
			if (attach.getSize()>filesize) {//�ϴ��ļ���С���ó���200K
				request.setAttribute("fileUploadError", "�ϴ���С���ó���200K");
				return "developer/appinfoadd";
			}else if(prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("png")||
					prefix.equalsIgnoreCase("jepg")||prefix.equalsIgnoreCase("pneg")) { //�ϴ�ͼƬ��ʽ
					String fileName=appInfo.getAPKName()+".jpg";//�ϴ�LOGOͼƬ����:apk����.apk
					File targetFile=new File(path,fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
						try {
							attach.transferTo(targetFile);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							request.setAttribute("fileUploadError", "�ϴ�ʧ��");
							return "developer/appinfoadd";
						}
					logoPicPath=request.getContextPath()+"/statics/uploadfiles/"+fileName;
					logoLocPath=path+File.separator+fileName;
			}else{
					request.setAttribute("fileUploadError", "�ϴ�ͼƬ��ʽ����ȷ��");
					return "developer/appinfoadd";
				}
		}
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		if (statusName.equals("�����")) {
			appInfo.setStatus(1);
		}
		if (statusName.equals("���ͨ��")) {
			appInfo.setStatus(2);		
		}
		if (statusName.equals("���δͨ��")) {
			appInfo.setStatus(3);
		}
		if (statusName.equals("���ϼ�")) {
			appInfo.setStatus(4);
		}
		if (statusName.equals("���¼�")) {
			appInfo.setStatus(5);
		}
		if (status!=null||status!="0") {
			appInfo.setStatus(Integer.parseInt(status));
		}
		int appinfomodify = developerService.appinfomodify(appInfo);
		if (appinfomodify>0) {
			return "redirect:/dev/_appInfolist";  //�޸ĳɹ�
		}
		return "developer/appinfomodify"; //�޸�ʧ��
	}
	
	//(�޸Ļ�����Ϣ)�첽ɾ��LOGOͼƬ��apk�ļ�
	@RequestMapping(value="/delfile.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	 public String delfile(String id,String flag) {
		System.out.println("�첽ɾ��==========="+id);
		Map<String , String> resultMap=new HashMap<String , String>();
		if (flag.equals("logo")) {
			System.out.println("�첽ɾ��LOGOͼƬ"+id);
			System.out.println("�첽ɾ��LOGOͼƬflag"+flag);
			int delfile = developerService.delfile(Integer.parseInt(id));
			if (delfile>0) {
				resultMap.put("result", "success");
			}else {
				resultMap.put("result", "failed");
			}
		} 
			if(flag.equals("apk")){
			System.out.println("�첽ɾ��apk�ļ�"+id);
			System.out.println("�첽ɾ���ļ�flag"+flag);
			int delapk = developerService.delapk(Integer.parseInt(id));
			System.out.println(delapk);
			if (delapk>0) {
				resultMap.put("result", "success");
			}else {
				resultMap.put("result", "failed");
			}
		}
		return JSONArray.toJSONString(resultMap);
	 }
	
	//�鿴������Ϣ
	@RequestMapping(value="/appview/{id}",method=RequestMethod.GET)
	public String appview(@PathVariable String id,Model model) {
		System.out.println("appview=========="+id);
		List<AppVersion> appversioninfo = developerService.appversioninfo(Integer.valueOf(id)); //��ʷ�汾��Ϣ
		AppInfo appview = developerService.appview(Integer.valueOf(id)); //������Ϣ
		model.addAttribute("appInfo",appview);
		model.addAttribute("appVersionList", appversioninfo);
		return "developer/appinfoview";
	}
	
	//ɾ��������Ϣ(ajax�첽ɾ��)
	@RequestMapping(value="/delapp.json",method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String delapp(String id) {
		System.out.println("ɾ��������Ϣ(ajax�첽ɾ��)");
		
		Map<String , String> resultMap=new HashMap<String , String>();
		if (id.isEmpty()) {
			resultMap.put("delResult", "notexist");
		}
		int delfile = developerService.delapp(Integer.parseInt(id));
		if (delfile>0) {
			resultMap.put("delResult", "true");
		}else {
			resultMap.put("delResult", "false");
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	//�첽���¼�
	@RequestMapping(value="/sale.json",method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String sale(String appId,String saleSwitch) {
		System.out.println("���¼�"+appId+saleSwitch);
		Map<String , String> resultMap=new HashMap<String , String>();
		
		try {
			int saleSwitchAjax=0;
			if (saleSwitch.equals("open")) {
				 saleSwitchAjax = developerService.saleSwitchAjax(4,Integer.parseInt(appId));
			}else if(saleSwitch.equals("close")) {
				saleSwitchAjax = developerService.saleSwitchAjax(5,Integer.parseInt(appId));
			}
			if (saleSwitchAjax>0) {
				resultMap.put("errorCode", "0");
				resultMap.put("resultMsg", "success");
			}else {
				resultMap.put("resultMsg", "failed");
			}
			if (appId.isEmpty()) {
				resultMap.put("errorCode", "param000001");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			resultMap.put("errorCode", "exception000001");
		}
		
		return JSONArray.toJSONString(resultMap);
	}
	
	//��ת�����汾��Ϣ
	@RequestMapping(value="/appversionadd")
	public String appversionadd(String id,AppVersion appVersion,
			Model model) {
		appVersion.setAppId(Integer.valueOf(id));
		List<AppVersion> appversioninfo = developerService.appversioninfo(Integer.parseInt(id));
		model.addAttribute("appVersionList",appversioninfo);
		return "developer/appversionadd";
	}
	
	//�����汾��Ϣ
	@RequestMapping(value="/addversionsave",method=RequestMethod.POST)
	public String addversionsave(AppVersion appVersion,
			HttpSession session,HttpServletRequest request,
			String appId,AppInfo appinfo,
			@RequestParam(value="a_downloadLink",required=false) MultipartFile attach
			) {
		System.out.println("addversionsave------------");
		String apkLocPath=null;	// apk�ļ��ķ������洢·��
		String apkFileName=null;// �ϴ���apk�ļ�����
		String downloadLink=null;// ��������
		if(!attach.isEmpty()) {
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");//·��
			String oldFileName=attach.getOriginalFilename(); //ԭ�ļ���
			apkFileName=oldFileName;
			String prefix=FilenameUtils.getExtension(oldFileName);//ԭ�ļ���׺
			int filesize=200000;//�ļ�����
			if (attach.getSize()>filesize) {//�ϴ��ļ���С���ó���200K
				request.setAttribute("fileUploadError", "�ϴ���С���ó���200K");
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")) { //�ϴ���ʽ
					String fileName=apkFileName;//�ϴ��ļ�����
					System.out.println(fileName);
					File targetFile=new File(path,fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
						try {
							attach.transferTo(targetFile);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							request.setAttribute("fileUploadError", "�ϴ�ʧ��");
							return "developer/appversionadd";
						}
						
					downloadLink=request.getContextPath()+"/statics/uploadfiles/"+fileName;
					apkLocPath=path+File.separator+fileName;
			}else{
					request.setAttribute("fileUploadError", "�ϴ���ʽ����ȷ��");
					return "developer/appversionadd";
				}
		}
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		appVersion.setCreationDate(new Date());
		 int addversionsave = developerService.addversionsave(appVersion);
		 System.out.println(addversionsave);
		if (addversionsave>0) {
			//����AppInfo���°汾��
			int vid = developerService.getIdByAppId(Integer.valueOf(appId));//����appId��ѯ����id  version��
			appinfo.setVersionId(Integer.valueOf(vid));
			appinfo.setId(Integer.valueOf(appId));
			developerService.addAppInfoVersionId(appinfo);	// ��AppInfo����   ����appId�޸����µİ汾VersionId
			
			return "redirect:/dev/_appInfolist";  //�����ɹ�
		}
		return "developer/appversionadd";	//����ʧ��
	}

	//��ת�޸İ汾��Ϣҳ��
	@RequestMapping(value="/appversionmodify")
	public String appversionmodify(String vid, String aid,
			Model model) {
		System.out.println("appversionmodify----------");
		System.out.println("vid"+vid);
		System.out.println("aid"+aid);
		
		List<AppVersion> appversioninfo = developerService.appversioninfo(Integer.parseInt(aid));
		model.addAttribute("appVersionList",appversioninfo);
		
		AppVersion appVersion = developerService.UpVersionInfo(Integer.valueOf(vid));
		model.addAttribute(appVersion);
		
		return "developer/appversionmodify";
	}

	//�޸����°汾��Ϣ
	@RequestMapping(value="/appversionmodifysave")
	public String appversionmodifysave(String id,String appId,
			AppVersion AppVersion) {
		System.out.println("appversionmodifysave-------");
		System.out.println("id"+id);
		System.out.println("appId"+appId);
		AppVersion.setModifyDate(new Date());
		int appversionmodify = developerService.appversionmodify(AppVersion);
		if (appversionmodify>0) {
			return "redirect:/dev/_appInfolist";
		}
		return "developer/appversionmodify";
	}
}
