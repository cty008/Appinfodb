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
	
	//跳转到登陆页面
	@RequestMapping(value="/login")
	public String Login() {
		System.out.println("Login");
		return "devlogin";
	}
	
	//登陆判断
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String devCode,@RequestParam String devPassword,
			HttpSession session,HttpServletRequest request) {
		request.getSession().setAttribute("isLogin", devCode );	//拦截登陆
		  DevUser logindev = developerService.Logindev(devCode, devPassword);
		if (logindev!=null) {
			System.out.println("dologin--if");
			session.setAttribute(Constants.devUserSession,logindev);
			return "redirect:/dev/main";
		}else {
			System.out.println("dologin--else");
			request.setAttribute("error", "用户名或密码不正确");
			return "devlogin";
		}
	}
	
	//判断session里是否存有用户
	@RequestMapping(value="/main")
	public String main(HttpSession session) {
		System.out.println("main");
		if(session.getAttribute(Constants.devUserSession)==null) {
			return "redirect:/dev/login";
		}
		return "developer/main";
	}
	
	//注销
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		System.out.println("logout");
		session.removeAttribute(Constants.devUserSession);
		return "devlogin";
	}
	
	//跳转查询列表方法
	@RequestMapping(value="/flatform/app/list")
	public String list() {
		System.out.println("appInfolist");
		return "redirect:/dev/_appInfolist";
	}
	
	//异步查询(ajax异步)  二三级分类(解决JSON数据乱码)
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET,
	produces={"application/json;charset=UTF-8"})  
	@ResponseBody 
	public Object categorylevellist(String pid)  {
		System.out.println("异步查询 二三级分类");
		
		String	jsonString="";
		if (pid!=null && pid!="") {									//根据一级分类查询二三级分类
			jsonString = JSONArray.toJSONString(developerService.getChildrenByParentId(Integer.valueOf(pid)));
		}else {														//一级分类
			jsonString = JSONArray.toJSONString(developerService.getcategoryLevel1());
		}
		System.out.println(jsonString);
		return jsonString;
		
	}
	
	//查询列表
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
		
		int currentPageNo=1;//当前页	
		
		if(pageIndex!=null) {//判断首页不能为空
			currentPageNo=Integer.valueOf(pageIndex);
		}
		
//		Integer devId = ((DevUser)(session.getAttribute(Constants.devUserSession))).getId(); //获取开发者Id
//		appinfo.setDevId(Integer.valueOf(devId));
		
		int totalCount=developerService.countPage(appinfo);//根据实体类对象查询记录条数
		//计算总页数
		int totalPageCount=totalCount%Constants.pageSize==0?(totalCount/Constants.pageSize):(totalCount/Constants.pageSize+1);
		appinfo.setPageSize(Constants.pageSize); //每页显示的数量
		appinfo.setCurrPage((currentPageNo-1)*Constants.pageSize); //当前页=当前页-1*每页显示的数量
		
		model.addAttribute("totalPageCount",totalPageCount);//获取表的总页数
		model.addAttribute("totalCount", totalCount);//表记录条数
		model.addAttribute("currentPageNo", currentPageNo);//当前页
		
		model.addAttribute("statusList",developerService.getStatus());  //APP状态下拉框
		model.addAttribute("flatFormList",developerService.getflatformId());//所属平台下拉框
		model.addAttribute("categoryLevel1List",developerService.getcategoryLevel1());//一级分类下拉框
		
		model.addAttribute("querySoftwareName",querySoftwareName);//软件名称
		model.addAttribute("queryStatus",queryStatus);//APP状态
		model.addAttribute("queryFlatformId",queryFlatformId);//所属平台
		if (queryCategoryLevel1!=null) {
			model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);//一级分类
		}
		if (queryCategoryLevel2!=null) {
			model.addAttribute("categoryLevel2List",developerService.getChildrenByParentId(Integer.parseInt(queryCategoryLevel1)));//二级分类
		}
		if (queryCategoryLevel3!=null) {
			model.addAttribute("categoryLevel3List",developerService.getChildrenByParentId(Integer.parseInt(queryCategoryLevel2)));//三级分类
		}
		
		model.addAttribute("appInfoList",developerService.appInfolist(appinfo)); //转发页面 查询列表保存到作用域里
		return "developer/appinfolist";
	}

	//跳转新增APP基础信息页面
	@RequestMapping(value="/flatform/app/appinfoadd")
	public String appinfoadd() {
		System.out.println("add");
		return "developer/appinfoadd";
	}
	
	//新增APP基础信息
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
			String oldFileName=attach.getOriginalFilename(); //原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize=200000;
			if (attach.getSize()>filesize) {//上传文件大小不得超过200K
				request.setAttribute("fileUploadError", "上传大小不得超过200K");
				return "developer/appinfoadd";
			}else if(prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("png")||
					prefix.equalsIgnoreCase("jepg")||prefix.equalsIgnoreCase("pneg")) { //上传图片格式
					String fileName=appInfo.getAPKName()+".jpg";//上传LOGO图片命名:apk名称.apk
					File targetFile=new File(path,fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
						try {
							attach.transferTo(targetFile);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							request.setAttribute("fileUploadError", "上传失败");
							return "developer/appinfoadd";
						}
					logoPicPath=request.getContextPath()+"/statics/uploadfiles/"+fileName;
					logoLocPath=path+File.separator+fileName;
			}else{
					request.setAttribute("fileUploadError", "上传图片格式不正确！");
					return "developer/appinfoadd";
				}
		}
		
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		int addAppInfo = developerService.addAppInfo(appInfo);
		if (addAppInfo>0) {
			System.out.println("add --if");
			return "redirect:/dev/_appInfolist";  //新增成功
		}
		return "developer/appinfoadd";	//新增失败
		
		
	}
	
	//(新增基础信息)ajax异步所属平台下拉框
	@RequestMapping(value="/datadictionarylist.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String datadictionarylist(String tcode) {
		String jsonString = JSONArray.toJSONString(developerService.getflatformId());
		return jsonString;
	}
	
	//(新增基础信息)ajax异步一级分类下拉框
	@RequestMapping(value="/categorylevellist1.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public Object categorylevellist1(String pid) {
		String jsonString = JSONArray.toJSONString(developerService.getcategoryLevel1());
		return jsonString;
	}
	
	//新增异步  ajax后台验证--APKName是否已存在
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
	
	//跳转修改基础信息jsp页面
	@RequestMapping(value="/appinfomodify",method=RequestMethod.GET)
	public String appinfomodify(String id,Model model) {
		System.out.println("appinfomodify======="+id);
		AppInfo appview = developerService.appview(Integer.valueOf(id)); //基础信息
		model.addAttribute("appInfo", appview);
		return "developer/appinfomodify";
	}
	
	//修改基础信息
	@RequestMapping(value="/appinfomodifysave",method=RequestMethod.POST)
	public String appinfomodifysave(AppInfo appInfo,
			HttpSession session,HttpServletRequest request,
			String statusName,String status,
			@RequestParam(value="attach",required=false) MultipartFile attach) {
		System.out.println("修改基础信息status"+status);
		String logoPicPath=null;
		String logoLocPath=null;
		if(!attach.isEmpty()) {
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			System.out.println(path);
			String oldFileName=attach.getOriginalFilename(); //原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize=200000;
			System.out.println(attach.getSize());
			if (attach.getSize()>filesize) {//上传文件大小不得超过200K
				request.setAttribute("fileUploadError", "上传大小不得超过200K");
				return "developer/appinfoadd";
			}else if(prefix.equalsIgnoreCase("jpg")||prefix.equalsIgnoreCase("png")||
					prefix.equalsIgnoreCase("jepg")||prefix.equalsIgnoreCase("pneg")) { //上传图片格式
					String fileName=appInfo.getAPKName()+".jpg";//上传LOGO图片命名:apk名称.apk
					File targetFile=new File(path,fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
						try {
							attach.transferTo(targetFile);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							request.setAttribute("fileUploadError", "上传失败");
							return "developer/appinfoadd";
						}
					logoPicPath=request.getContextPath()+"/statics/uploadfiles/"+fileName;
					logoLocPath=path+File.separator+fileName;
			}else{
					request.setAttribute("fileUploadError", "上传图片格式不正确！");
					return "developer/appinfoadd";
				}
		}
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		if (statusName.equals("待审核")) {
			appInfo.setStatus(1);
		}
		if (statusName.equals("审核通过")) {
			appInfo.setStatus(2);		
		}
		if (statusName.equals("审核未通过")) {
			appInfo.setStatus(3);
		}
		if (statusName.equals("已上架")) {
			appInfo.setStatus(4);
		}
		if (statusName.equals("已下架")) {
			appInfo.setStatus(5);
		}
		if (status!=null||status!="0") {
			appInfo.setStatus(Integer.parseInt(status));
		}
		int appinfomodify = developerService.appinfomodify(appInfo);
		if (appinfomodify>0) {
			return "redirect:/dev/_appInfolist";  //修改成功
		}
		return "developer/appinfomodify"; //修改失败
	}
	
	//(修改基础信息)异步删除LOGO图片和apk文件
	@RequestMapping(value="/delfile.json",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	 public String delfile(String id,String flag) {
		System.out.println("异步删除==========="+id);
		Map<String , String> resultMap=new HashMap<String , String>();
		if (flag.equals("logo")) {
			System.out.println("异步删除LOGO图片"+id);
			System.out.println("异步删除LOGO图片flag"+flag);
			int delfile = developerService.delfile(Integer.parseInt(id));
			if (delfile>0) {
				resultMap.put("result", "success");
			}else {
				resultMap.put("result", "failed");
			}
		} 
			if(flag.equals("apk")){
			System.out.println("异步删除apk文件"+id);
			System.out.println("异步删除文件flag"+flag);
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
	
	//查看基础信息
	@RequestMapping(value="/appview/{id}",method=RequestMethod.GET)
	public String appview(@PathVariable String id,Model model) {
		System.out.println("appview=========="+id);
		List<AppVersion> appversioninfo = developerService.appversioninfo(Integer.valueOf(id)); //历史版本信息
		AppInfo appview = developerService.appview(Integer.valueOf(id)); //基础信息
		model.addAttribute("appInfo",appview);
		model.addAttribute("appVersionList", appversioninfo);
		return "developer/appinfoview";
	}
	
	//删除基础信息(ajax异步删除)
	@RequestMapping(value="/delapp.json",method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String delapp(String id) {
		System.out.println("删除基础信息(ajax异步删除)");
		
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
	
	//异步上下架
	@RequestMapping(value="/sale.json",method=RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String sale(String appId,String saleSwitch) {
		System.out.println("上下架"+appId+saleSwitch);
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
	
	//跳转新增版本信息
	@RequestMapping(value="/appversionadd")
	public String appversionadd(String id,AppVersion appVersion,
			Model model) {
		appVersion.setAppId(Integer.valueOf(id));
		List<AppVersion> appversioninfo = developerService.appversioninfo(Integer.parseInt(id));
		model.addAttribute("appVersionList",appversioninfo);
		return "developer/appversionadd";
	}
	
	//新增版本信息
	@RequestMapping(value="/addversionsave",method=RequestMethod.POST)
	public String addversionsave(AppVersion appVersion,
			HttpSession session,HttpServletRequest request,
			String appId,AppInfo appinfo,
			@RequestParam(value="a_downloadLink",required=false) MultipartFile attach
			) {
		System.out.println("addversionsave------------");
		String apkLocPath=null;	// apk文件的服务器存储路径
		String apkFileName=null;// 上传的apk文件名称
		String downloadLink=null;// 下载链接
		if(!attach.isEmpty()) {
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");//路径
			String oldFileName=attach.getOriginalFilename(); //原文件名
			apkFileName=oldFileName;
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			int filesize=200000;//文件限制
			if (attach.getSize()>filesize) {//上传文件大小不得超过200K
				request.setAttribute("fileUploadError", "上传大小不得超过200K");
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")) { //上传格式
					String fileName=apkFileName;//上传文件命名
					System.out.println(fileName);
					File targetFile=new File(path,fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
						try {
							attach.transferTo(targetFile);
						} catch (IllegalStateException | IOException e) {
							e.printStackTrace();
							request.setAttribute("fileUploadError", "上传失败");
							return "developer/appversionadd";
						}
						
					downloadLink=request.getContextPath()+"/statics/uploadfiles/"+fileName;
					apkLocPath=path+File.separator+fileName;
			}else{
					request.setAttribute("fileUploadError", "上传格式不正确！");
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
			//新增AppInfo最新版本号
			int vid = developerService.getIdByAppId(Integer.valueOf(appId));//根据appId查询最新id  version表
			appinfo.setVersionId(Integer.valueOf(vid));
			appinfo.setId(Integer.valueOf(appId));
			developerService.addAppInfoVersionId(appinfo);	// 往AppInfo表里   根据appId修改最新的版本VersionId
			
			return "redirect:/dev/_appInfolist";  //新增成功
		}
		return "developer/appversionadd";	//新增失败
	}

	//跳转修改版本信息页面
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

	//修改最新版本信息
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
