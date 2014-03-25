package in.kmbs.vlethyme.enums;

public enum ForumMemberRoleEnum {
	ROLE_FORUM_MEMBER_VIEW("FORUM_MEMBER_VIEW", (short) 3, 6), ROLE_FORUM_MEMBER_MANAGE("FORUM_MEMBER_MANAGE", (short) 3, 7);

	private String roleType;
	private Short roleTypeId;
	private Integer roleId;

	private ForumMemberRoleEnum(String roleType, Short roleTypeId, Integer roleId) {
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
