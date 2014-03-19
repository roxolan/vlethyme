package in.kmbs.vlethyme.enums;

public enum GroupMemberRoleEnum {
	ROLE_GROUP_MEMBER_MEMBER("GROUP_MEMBER_MEMBER", (short)2, 5), ROLE_GROUP_MEMBER_MANAGER("GROUP_MEMBER_MANAGER", (short)2, 4);
	
	private String roleType;
	private Short roleTypeId;
	private Integer roleId;
	
	
	private GroupMemberRoleEnum(String roleType, Short roleTypeId, Integer roleId) {
		this.roleType = roleType;
		this.roleTypeId = roleTypeId;
		this.roleId = roleId;
	}


	public String getRoleType() {
		return roleType;
	}
	
	public Short getRoleTypeId() {
		return roleTypeId;
	}

	public Integer getRoleId() {
		return roleId;
	}
}
