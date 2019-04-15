package pojo;

import java.util.Collection;

public class SysEmployee {
    private String sn;
    private String name;
    private String password;
    private String status;
    private Collection<BizCheckResult> bizCheckResultsBySn;
    private Collection<BizClaimVoucher> bizClaimVouchersBySn;
    private Collection<BizClaimVoucher> bizClaimVouchersBySn_0;
    private SysPosition sysPositionByPositionId;
    private SysDepartment sysDepartmentByDepartmentId;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysEmployee that = (SysEmployee) o;

        if (sn != null ? !sn.equals(that.sn) : that.sn != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sn != null ? sn.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public Collection<BizCheckResult> getBizCheckResultsBySn() {
        return bizCheckResultsBySn;
    }

    public void setBizCheckResultsBySn(Collection<BizCheckResult> bizCheckResultsBySn) {
        this.bizCheckResultsBySn = bizCheckResultsBySn;
    }

    public Collection<BizClaimVoucher> getBizClaimVouchersBySn() {
        return bizClaimVouchersBySn;
    }

    public void setBizClaimVouchersBySn(Collection<BizClaimVoucher> bizClaimVouchersBySn) {
        this.bizClaimVouchersBySn = bizClaimVouchersBySn;
    }

    public Collection<BizClaimVoucher> getBizClaimVouchersBySn_0() {
        return bizClaimVouchersBySn_0;
    }

    public void setBizClaimVouchersBySn_0(Collection<BizClaimVoucher> bizClaimVouchersBySn_0) {
        this.bizClaimVouchersBySn_0 = bizClaimVouchersBySn_0;
    }

    public SysPosition getSysPositionByPositionId() {
        return sysPositionByPositionId;
    }

    public void setSysPositionByPositionId(SysPosition sysPositionByPositionId) {
        this.sysPositionByPositionId = sysPositionByPositionId;
    }

    public SysDepartment getSysDepartmentByDepartmentId() {
        return sysDepartmentByDepartmentId;
    }

    public void setSysDepartmentByDepartmentId(SysDepartment sysDepartmentByDepartmentId) {
        this.sysDepartmentByDepartmentId = sysDepartmentByDepartmentId;
    }
}
