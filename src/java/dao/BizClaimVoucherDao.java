package dao;

import pojo.*;

import java.util.List;

public interface BizClaimVoucherDao {

    public int findCountByName(Hiredate hiredate);

    public int findCount(String sn);

    public List<BizClaimVoucher> findByConditionList(Page page, Hiredate hiredate,BizClaimVoucher b);

    public List<BizClaimVoucher> query(Page page, String sn);

    public int save(BizClaimVoucher biz);

    public BizClaimVoucher getBizClaimVoucherInfoById(Integer id);

    public void delete(Integer id);

    public void sub(BizClaimVoucher biz);

    public void DelDetail();
    public void update(BizClaimVoucher bizClaimVoucher,Integer id,String json);

    public void doDetail234(BizClaimVoucher biz);
    public void doDetai3(BizCheckResult biz);
}
