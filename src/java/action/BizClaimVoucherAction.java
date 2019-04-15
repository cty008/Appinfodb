package action;

import alipayConfig.AlipayConfig;
import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import pojo.*;
import service.BizClaimVoucherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class BizClaimVoucherAction extends ActionSupport {

    private BizClaimVoucherService bizClaimVoucherService; //业务逻辑对象

    private BizClaimVoucher bizClaimVoucher; //报销单对象1
    private Hiredate hiredate;   //条件查询类对象
    private List<BizClaimVoucher> claims; //报销单列表对象
    private Page page;  //分页类对象

    private BizClaimVoucher claim;   //报销单对象2
    private int hidd;                //1:保存  2:提交
    private String json;             //对应报销单的明细信息
    private int id;  //从list.jsp穿过来的ID值 用来操作 查看、修改、提交、删除
    private String comm;    //审批意见
    private  BizCheckResult bizCheckResult;  //报销单审核对象



    //财务审批
    public String doDetail4() throws Exception{
        System.out.println("doDetail4...............hidd="+hidd);
        System.out.println("doDetail4...............id="+id);
        System.out.println("doDetail4...............comm="+comm);

        //报销申请表初始化
        if (bizCheckResult==null) {
            bizCheckResult=new BizCheckResult();
            bizCheckResult.setComm(comm);
            bizCheckResult.setResult("通过");
            bizCheckResult.setCheckTime(new Date());
            SysEmployee sys=new SysEmployee();
            sys.setSn("A4"); //审核通过人
            bizCheckResult.setSysEmployeeByCheckerSn(sys);
            BizClaimVoucher b=new BizClaimVoucher();
            b.setId(id);
            bizCheckResult.setBizClaimVoucherByClaimId(b);
        }
        if (hidd==1){
            claim = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单

            SysEmployee sys=new SysEmployee();
            sys.setSn("");
            claim.setSysEmployeeByNextDealSn(sys);  //下一个处理人
            claim.setStatus("已付款");
            bizClaimVoucherService.doDetail234(claim); //审批方法
            bizClaimVoucherService.doDetai3(bizCheckResult); //总经理、财务、添加审批表方法
        }


        //处理响应乱码
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //设置页面跳转同步通知页面路径  对应AlipayConfig中的return_url
        alipayRequest.setReturnUrl(AlipayConfig.return_url);


        //商户订单号，商户网站订单系统中唯一订单号，必填
        String id=""+claim.getId();
        String out_trade_no = id;
        //付款金额，必填
        String total_amount = claim.getTotalAccount().toString();
        //订单名称，必填
        String subject = "尼玛";
        //商品描述，可空
        String body = "哈哈";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //调用SDK生成表单
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //输出
        out.println(result);

        return Action.NONE;
    }

    //总经理审批
    public String doDetail3() throws Exception{
        System.out.println("doDetail3...............hidd="+hidd);
        System.out.println("doDetail3...............id="+id);
        System.out.println("doDetail3...............comm="+comm);

        //报销申请表初始化
        if (bizCheckResult==null) {
            bizCheckResult=new BizCheckResult();
            bizCheckResult.setComm(comm);
            bizCheckResult.setResult("通过");
            bizCheckResult.setCheckTime(new Date());
            SysEmployee sys=new SysEmployee();
            sys.setSn("A3"); //审核通过人
            bizCheckResult.setSysEmployeeByCheckerSn(sys);
            BizClaimVoucher b=new BizClaimVoucher();
            b.setId(id);
            bizCheckResult.setBizClaimVoucherByClaimId(b);
        }

        if (hidd==1){
            claim = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
            SysEmployee sys=new SysEmployee();
            sys.setSn("A4");
            claim.setSysEmployeeByNextDealSn(sys);  //下一个处理人
            claim.setStatus("待付款");
            bizClaimVoucherService.doDetail234(claim); //审批方法
            bizClaimVoucherService.doDetai3(bizCheckResult); //总经理添加审批表方法
        }else if(hidd==2){
            claim = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
            claim.setStatus("已拒绝");
            SysEmployee sys=new SysEmployee();
            sys.setSn("");
            claim.setSysEmployeeByNextDealSn(sys);
            bizClaimVoucherService.doDetail234(claim); //审批方法
        }else if(hidd==3){
            claim = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
            claim.setStatus("已打回");
            SysEmployee sys=new SysEmployee();
            sys.setSn("A1");
            claim.setSysEmployeeByNextDealSn(sys);
            bizClaimVoucherService.doDetail234(claim); //审批方法
        }

        return Action.SUCCESS;
    }

    //部门经理审批
    public String doDetail2() throws Exception{
        System.out.println("doDetail2...............hidd="+hidd);
        System.out.println("doDetail2...............id="+id);
        System.out.println("doDetail2...............comm="+comm);

        //报销申请表初始化
        if (bizCheckResult==null) {
            bizCheckResult=new BizCheckResult();
            bizCheckResult.setComm(comm);
            bizCheckResult.setResult("通过");
            bizCheckResult.setCheckTime(new Date());
            SysEmployee sys=new SysEmployee();
            sys.setSn("A2"); //审核通过人
            bizCheckResult.setSysEmployeeByCheckerSn(sys);
        }

        if (hidd==1){
            claim = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
             SysEmployee sys=new SysEmployee();
             sys.setSn("A3");
             claim.setSysEmployeeByNextDealSn(sys);  //下一个处理人
             claim.setStatus("待审批");
            //级联增加到审批表
            List<BizCheckResult> list=new ArrayList<BizCheckResult>();
            list.add(bizCheckResult);
            claim.setBizCheckResultsById(new HashSet<BizCheckResult>(list));
            bizClaimVoucherService.doDetail234(claim); //审批方法
        }else if(hidd==2){
            claim = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
            claim.setStatus("已拒绝");
            SysEmployee sys=new SysEmployee();
            sys.setSn("");
            claim.setSysEmployeeByNextDealSn(sys);
            bizClaimVoucherService.doDetail234(claim); //审批方法
        }else if(hidd==3){
            claim = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
            claim.setStatus("已打回");
            SysEmployee sys=new SysEmployee();
            sys.setSn("A1");
            claim.setSysEmployeeByNextDealSn(sys);
            bizClaimVoucherService.doDetail234(claim); //审批方法
        }
        return Action.SUCCESS;
    }

    //部门经理、总经理、财务、 跳转到审批页面
    public String detail() throws Exception{
        System.out.println("detail.................."+id);
        claim = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
        return Action.SUCCESS;
    }

    //修改报销单
    public String updateInfo() throws Exception{
        System.out.println("updateInfo.................."+id);
        System.out.println("hidd="+hidd);
        System.out.println("json="+json);
        System.out.println("claim.getTotalAccount()="+claim.getTotalAccount());
        System.out.println("claim.getEvent()"+claim.getEvent());

        if (claim!=null){
            if (claim.getStatus()==null) {
                if (hidd == 1) {
                    claim.setStatus("新创建");
                } else if (hidd == 2) {
                    claim.setStatus("已提交");
                }
            }
            bizClaimVoucherService.update(claim,id,json); //修改
            bizClaimVoucherService.DelDetail(); //根据main_id为null 值查询 并删除
        }
        return Action.SUCCESS;
    }

    //查询信息并跳转到修改页面
    public String update() throws Exception{
        System.out.println("update.................."+id);
        claim=bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
        if (claim!=null){
            return Action.SUCCESS;
        }
        return Action.ERROR;
    }

    //提交报销单
    public String sub() throws Exception{
        System.out.println("sub.................."+id);

        bizClaimVoucher = bizClaimVoucherService.getBizClaimVoucherInfoById(id);//根据ID获得报销单
        if (bizClaimVoucher!=null){
            bizClaimVoucher.setStatus("已提交");
            SysEmployee sys=new SysEmployee();
            sys.setSn("A2");
            bizClaimVoucher.setSysEmployeeByNextDealSn(sys);
        }
        bizClaimVoucherService.sub(bizClaimVoucher); //修改
        return Action.SUCCESS;
    }

    //删除报销单
    public String delete() throws Exception{
        System.out.println("delete.................."+id);
        bizClaimVoucherService.delete(id);
        return Action.SUCCESS;
    }

    //查看报销单
    public String find() throws Exception{
        System.out.println("find..........."+id);
        claim=bizClaimVoucherService.getBizClaimVoucherInfoById(id);
        if (claim!=null){
            return Action.SUCCESS;
        }
        return Action.ERROR;
    }

    //添加报销单
    public String add() throws Exception{
        System.out.println("add..................");

        HttpServletResponse response=ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();

        //获取保存到session回话作用域里 登陆人的姓名
        SysEmployee user =(SysEmployee) ActionContext.getContext().getSession().get("user");
        claim.setSysEmployeeByCreateSn(user);  //填报人

        if(hidd!=0){
            if (hidd==1){
                claim.setStatus("新创建");
                claim.setSysEmployeeByNextDealSn(user);  //下一个审批人
            }else{
                claim.setStatus("已提交");
                if (claim.getSysEmployeeByNextDealSn()==null){
                    user=new SysEmployee();
                    user.setSn("A2");
                    claim.setSysEmployeeByNextDealSn(user);
                }
            }
        }

        List <BizClaimVoucherDetail> list= JSONArray.parseArray(json,BizClaimVoucherDetail.class);//将JSON对象转换为List集合
        claim.setBizClaimVoucherDetailsById(new HashSet<BizClaimVoucherDetail>(list));  //报销单明细表

        int i=bizClaimVoucherService.saveService(claim);
        if (i>0){
            out.println("<script>alert('保存成功');</script>");
           return Action.SUCCESS;
        }else{
            out.println("<script>alert('保存失败');location='add.jsp' </script>");
        }

        return NONE;
    }

    //默认方法显示列表信息
    public void validate()   {
        System.out.println("validate~~~~~~~~");

        //获取保存到session回话作用域里 登陆人的姓名
        SysEmployee user =(SysEmployee) ActionContext.getContext().getSession().get("user");

        //如果page类为空就赋初始值
        if (page==null){
            page =new Page();
            page.setPageSize(5);
            page.setPageIndex(1);
            if (hiredate!=null){
                page.setCount(bizClaimVoucherService.findCountByName(hiredate));
            }else {
                page.setCount(bizClaimVoucherService.findCount(user.getSn()));
                page.setPageAll((page.getCount() % page.getPageSize() == 0) ? (page.getCount() / page.getPageSize()) : (page.getCount() / page.getPageSize() + 1));
            }
        }else{
                //如果分页类里的当前页为空就赋值为1
                if (page.getPageIndex()==null){
                    page.setPageIndex(1);
                }
                page.setPageSize(5);
                //如果当前条件类不为空，就按条件查询记录行数
                if (hiredate!=null){
                    page.setCount(bizClaimVoucherService.findCountByName(hiredate));
                }else{
                    page.setCount(bizClaimVoucherService.findCount(user.getSn()));
                }
                page.setPageAll((page.getCount()%page.getPageSize()==0)?(page.getCount()/page.getPageSize()):(page.getCount()/page.getPageSize()+1));
        }
        claims = bizClaimVoucherService.query(page,user.getSn());

    }

    //按条件查询
    public String List() throws Exception{
        System.out.println("List.........");

        //获取保存到session回话作用域里 登陆人的姓名
        SysEmployee user =(SysEmployee) ActionContext.getContext().getSession().get("user");

            //如果分页类里的当前页为空就赋值为1
            if (page.getPageIndex()==null){
                page.setPageIndex(1);
            }
            page.setPageSize(5);
            //如果当前条件类不为空，就按条件查询记录行数
            if (hiredate!=null){
                hiredate.setSn(user.getSn());  //登陆人
                page.setCount(bizClaimVoucherService.findCountByName(hiredate));
            }else{
                page.setCount(bizClaimVoucherService.findCount(user.getSn()));
            }
            page.setPageAll((page.getCount() % page.getPageSize() == 0) ? (page.getCount() / page.getPageSize()) : (page.getCount() / page.getPageSize() + 1));


            if (bizClaimVoucher==null){
                bizClaimVoucher=new BizClaimVoucher();  //初始化
                bizClaimVoucher.setSysEmployeeByCreateSn(user);   //根据登陆人查询填报人
                bizClaimVoucher.setSysEmployeeByNextDealSn(user); //根据登陆人查询待处理人
            }
            //按条件查询方法
            claims= bizClaimVoucherService.findByConditionListService(page,hiredate,bizClaimVoucher);
            if (claims!=null && claims.size()>0){
                return Action.SUCCESS;
            }else{
                return "query";
            }
    }


    public BizClaimVoucherService getBizClaimVoucherService() {
        return bizClaimVoucherService;
    }
    public void setBizClaimVoucherService(BizClaimVoucherService bizClaimVoucherService) {
        this.bizClaimVoucherService = bizClaimVoucherService;
    }
    public List<BizClaimVoucher> getClaims() {
        return claims;
    }
    public void setClaims(List<BizClaimVoucher> claims) {
        this.claims = claims;
    }
    public Hiredate getHiredate() {
        return hiredate;
    }
    public void setHiredate(Hiredate hiredate) {
        this.hiredate = hiredate;
    }
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
    public BizClaimVoucher getBizClaimVoucher() {
        return bizClaimVoucher;
    }
    public void setBizClaimVoucher(BizClaimVoucher bizClaimVoucher) {
        this.bizClaimVoucher = bizClaimVoucher;
    }
    public BizClaimVoucher getClaim() {
        return claim;
    }
    public void setClaim(BizClaimVoucher claim) {
        this.claim = claim;
    }
    public int getHidd() {
        return hidd;
    }
    public void setHidd(int hidd) {
        this.hidd = hidd;
    }
    public String getJson() {
        return json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getComm() {
        return comm;
    }
    public void setComm(String comm) {
        this.comm = comm;
    }
    public BizCheckResult getBizCheckResult() {
        return bizCheckResult;
    }
    public void setBizCheckResult(BizCheckResult bizCheckResult) {
        this.bizCheckResult = bizCheckResult;
    }
}
