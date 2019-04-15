package dao.impl;

import com.alibaba.fastjson.JSONArray;
import dao.BizClaimVoucherDao;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import pojo.*;
import java.util.HashSet;
import java.util.List;

public class BizClaimVoucherDaoImpl extends HibernateDaoSupport implements BizClaimVoucherDao {


    @Override  //按登录人、状态、开始时间、结束时间查询表记录数
    public int findCountByName(Hiredate hiredate) {
        StringBuilder hql=new StringBuilder(" select count(*) from BizClaimVoucher as b where 1=1 ");
        if (hiredate.getSn()!=null){
            hql.append(" and b.sysEmployeeByCreateSn.sn = '"+hiredate.getSn()+"' ");
            }
        if (!hiredate.getStatus().isEmpty() && !"".equals(hiredate.getStatus())){
            hql.append(" and b.status = '"+hiredate.getStatus()+"' ");
        }
        if (hiredate.getStartDate()!=null && !"".equals(hiredate.getStartDate())){
            hql.append(" and b.createTime >= to_date('"+hiredate.getStartDate()+"','yyyy-MM-dd') ");
        }
        if (hiredate.getEndDate().trim()!=null && !"".equals(hiredate.getEndDate())){
            hql.append(" and b.createTime <= to_date('"+hiredate.getEndDate()+"','yyyy-MM-dd') ");
        }

        List<Long> list = this.getHibernateTemplate().find(hql.toString());
        if (list.size() > 0) {
            return list.get(0).intValue();
        }

        return 0;
    }

    @Override //按登角色 查询所有记录数  默认显示页面
    public int findCount(String sn) {
        StringBuilder hql=new StringBuilder(" select count(*) from BizClaimVoucher as b where 1=1 ");
        if (sn!=null){
            if (!sn.equals("A1")){  //带处理人除员工外
                hql.append(" and b.sysEmployeeByNextDealSn.sn = '"+sn+"' ");
            }else{
                hql.append(" and b.sysEmployeeByCreateSn.sn = '"+sn+"' ");
            }
        }
        List<Long> list = this.getHibernateTemplate().find(hql.toString());
        if (list.size() > 0) {
            return list.get(0).intValue();
        }
        return 0;
    }

    @Override  //多条件动态查询
    public List<BizClaimVoucher> findByConditionList(Page page, Hiredate hiredate, BizClaimVoucher b) {
        StringBuilder hql=new StringBuilder(" from BizClaimVoucher as b where 1=1 ");

        if (b.getSysEmployeeByCreateSn().getSn().equals("A1")){
            hql.append(" and b.sysEmployeeByCreateSn.sn ='"+b.getSysEmployeeByCreateSn().getSn()+"' ");
        }
        if (b.getSysEmployeeByNextDealSn().getSn().equals("A2")){
            hql.append(" and b.sysEmployeeByNextDealSn.sn ='"+b.getSysEmployeeByNextDealSn().getSn()+"' ");
        }
        if (b.getSysEmployeeByNextDealSn().getSn().equals("A3")){
            hql.append(" and b.sysEmployeeByNextDealSn.sn ='"+b.getSysEmployeeByNextDealSn().getSn()+"' ");
        }
        if (b.getSysEmployeeByNextDealSn().getSn().equals("A4")){
            hql.append(" and b.sysEmployeeByNextDealSn.sn ='"+b.getSysEmployeeByNextDealSn().getSn()+"' ");
        }
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        if (!hiredate.getStatus().isEmpty() && !"".equals(hiredate.getStatus())){
            hql.append(" and b.status = '"+hiredate.getStatus()+"' ");
        }
        if (hiredate.getStartDate()!=null && !"".equals(hiredate.getStartDate())){
            hql.append(" and b.createTime >= to_date('"+hiredate.getStartDate()+"','yyyy-MM-dd') ");
        }
        if (hiredate.getEndDate().trim()!=null && !"".equals(hiredate.getEndDate())){
            hql.append(" and b.createTime <= to_date('"+hiredate.getEndDate()+"','yyyy-MM-dd') ");
        }

        return this.getSession().createQuery(hql.toString()).setMaxResults(page.getPageSize()).
                setFirstResult((page.getPageIndex()-1)*page.getPageSize()).list();
    }

    @Override  //按条件查询展示到默认列表
    public List<BizClaimVoucher> query(Page page,String sn) {
        StringBuilder hql=new StringBuilder("from BizClaimVoucher as b where  1=1  ");
        if (sn.equals("A1")){
            hql.append(" and b.sysEmployeeByCreateSn.sn ='"+sn+"' ");
        }
        if (sn.equals("A2")){
            hql.append(" and b.sysEmployeeByNextDealSn.sn ='"+sn+"' ");
        }
        if (sn.equals("A3")){
            hql.append(" and b.sysEmployeeByNextDealSn.sn ='"+sn+"' ");
        }
        if (sn.equals("A4")){
            hql.append(" and b.sysEmployeeByNextDealSn.sn ='"+sn+"' ");
        }
        Query query = this.getSession().createQuery(hql.toString());
        query.setMaxResults(page.getPageSize()).setFirstResult((page.getPageIndex()-1)*page.getPageSize());
        return query.list();
    }

    @Override  //添加报销单
    public int save(BizClaimVoucher biz) {
        System.out.println("save.........");
        int id =(int) this.getHibernateTemplate().save(biz);
        System.out.println("最新id="+id);
        return id;
    }

    @Override  //查看报销单信息
    public BizClaimVoucher getBizClaimVoucherInfoById(Integer id) {
        BizClaimVoucher bizClaimVoucher =(BizClaimVoucher) this.getHibernateTemplate().get(BizClaimVoucher.class, id);
        return bizClaimVoucher;
    }

    @Override //删除报销单
    public void delete(Integer id) {
        BizClaimVoucher bizClaimVoucher =(BizClaimVoucher) this.getHibernateTemplate().get(BizClaimVoucher.class, id);
        this.getHibernateTemplate().delete(bizClaimVoucher);
    }

    @Override  //提交报销单
    public void sub(BizClaimVoucher biz) {
        this.getHibernateTemplate().update(biz);
    }

    @Override  //根据main_id为null 值查询 并删除
    public void DelDetail(){
        StringBuilder hql=new StringBuilder(" from BizClaimVoucherDetail as b where b.bizClaimVoucherByMainId is null ");
        List<BizClaimVoucherDetail> list = this.getHibernateTemplate().find(hql.toString());
        for (BizClaimVoucherDetail t : list) {
            this.getHibernateTemplate().delete(t);
        }
    }

    @Override  //修改报销单
    public void update(BizClaimVoucher bizClaimVoucher,Integer id,String json) {
        BizClaimVoucher b =(BizClaimVoucher) this.getHibernateTemplate().get(BizClaimVoucher.class, id);

        List <BizClaimVoucherDetail> list= JSONArray.parseArray(json,BizClaimVoucherDetail.class);//将JSON对象转换为List集合
        b.setBizClaimVoucherDetailsById(new HashSet<BizClaimVoucherDetail>(list));  //报销单明细表
        b.setEvent(bizClaimVoucher.getEvent()); //事由
        b.setStatus(bizClaimVoucher.getStatus());  //状态
        if (bizClaimVoucher.getStatus().equals("已提交")){
            SysEmployee sys=new SysEmployee();
            sys.setSn("A2");
            b.setSysEmployeeByNextDealSn(sys); //下一个审批人
        }
        b.setTotalAccount(bizClaimVoucher.getTotalAccount()); //总金额

        this.getHibernateTemplate().update(b);
    }

    @Override   //部门经理、总经理、财务、审批
    public void doDetail234(BizClaimVoucher biz) {
        System.out.println("doDetail234~~~~~~~~~~~");
         this.getHibernateTemplate().update(biz);
    }

    @Override  //总经理添加审批表
    public void doDetai3(BizCheckResult biz) {
        this.getHibernateTemplate().save(biz);
    }

}
