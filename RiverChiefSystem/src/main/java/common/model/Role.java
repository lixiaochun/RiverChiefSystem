package common.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class Role {

    @NotNull(message="roleId不为null")
    private int roleId;

    @NotNull(message="roleName不为null")
    private String roleName;

    @NotNull(message="status不为null")
    @DecimalMin(value="0",message="status取值为0或1")
    @DecimalMax(value="1",message="status取值为0或1")
    private int status;

    private String roleDesc;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if ((object == null)|| (object.getClass() != this.getClass()))
            return false;
        // object must be Test at this point
        Role role = (Role) object;
        return ((this.roleId == role.roleId)
                && (this.roleName != null && this.roleName.equals(role.roleName))
                &&(this.status == role.status)
                &&(this.roleDesc != null && this.roleDesc.equals(role.roleDesc)));
    }
}
