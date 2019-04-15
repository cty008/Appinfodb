package pojo;

import java.sql.Time;
import java.util.Collection;
import java.util.Date;

public class BizClaimVoucher {
    private int id;
    private Date createTime;
    private String event;
    private Long totalAccount;
    private String status;
    private Date modifyTime;
    private Collection<BizCheckResult> bizCheckResultsById;
    private SysEmployee sysEmployeeByNextDealSn;
    private SysEmployee sysEmployeeByCreateSn;
    private Collection<BizClaimVoucherDetail> bizClaimVoucherDetailsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Long getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(Long totalAccount) {
        this.totalAccount = totalAccount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BizClaimVoucher that = (BizClaimVoucher) o;

        if (id != that.id) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (totalAccount != null ? !totalAccount.equals(that.totalAccount) : that.totalAccount != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (totalAccount != null ? totalAccount.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        return result;
    }

    public Collection<BizCheckResult> getBizCheckResultsById() {
        return bizCheckResultsById;
    }

    public void setBizCheckResultsById(Collection<BizCheckResult> bizCheckResultsById) {
        this.bizCheckResultsById = bizCheckResultsById;
    }

    public SysEmployee getSysEmployeeByNextDealSn() {
        return sysEmployeeByNextDealSn;
    }

    public void setSysEmployeeByNextDealSn(SysEmployee sysEmployeeByNextDealSn) {
        this.sysEmployeeByNextDealSn = sysEmployeeByNextDealSn;
    }

    public SysEmployee getSysEmployeeByCreateSn() {
        return sysEmployeeByCreateSn;
    }

    public void setSysEmployeeByCreateSn(SysEmployee sysEmployeeByCreateSn) {
        this.sysEmployeeByCreateSn = sysEmployeeByCreateSn;
    }

    public Collection<BizClaimVoucherDetail> getBizClaimVoucherDetailsById() {
        return bizClaimVoucherDetailsById;
    }

    public void setBizClaimVoucherDetailsById(Collection<BizClaimVoucherDetail> bizClaimVoucherDetailsById) {
        this.bizClaimVoucherDetailsById = bizClaimVoucherDetailsById;
    }
}
