package usermanage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import common.model.Menu;
import common.model.Permissions;
import common.model.Role;
import common.model.RolePermissionsRelation;
import common.model.User;

public interface RoleMapper {

    public List<Role> queryRoleList(Map<Object, Object> map);

    public int countRole(Map<Object, Object> map);

    public int newRole(@Param("map") Map<Object, Object> map);

    public void updateRole(Map<Object, Object> map);

    public void newOrgRoleRelation(Map<Object, Object> map);

    public List<Permissions> queryPermissions(Map<Object, Object> map);

    public List<RolePermissionsRelation> queryRolePermissionsRelation(
            Map<Object, Object> map);

    public void updateRolePermissionsRelation(Map<Object, Object> map);

    public void newRolePermissionsRelation(RolePermissionsRelation rpr);

    public void updatePermission(Map<Object, Object> map);

    public List<Menu> queryMenuList(Map<Object, Object> map);
    
    public int insertRole(List<Role> role);

    public int updateRoleList(List<Role> role);
}
