package in.kmbs.vlethyme.enums;

public enum UserRoleEnum {
	ROLE_USER_MEMBER("USER_STUDENT", (short) 1, 1), ROLE_USER_TEACHER("USER_TEACHER", (short) 1, 2), ROLE_USER_MANAGER("USER_MANAGER", (short) 1, 3);

	private String roleType;
	private Short roleTypeId;
	private Integer roleId;

	private UserRoleEnum(String roleType, Short roleTypeId, Integer roleId) {
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
