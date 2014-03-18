package in.kmbs.vlethyme.converter;

import in.kmbs.vlethyme.model.Group;

public class EntityToVOConverter {
	
	public static Group convert(in.kmbs.vlethyme.entity.Group group) {
		Group groupModel = new Group();
		groupModel.setId(group.getId());
		groupModel.setName(group.getName());

		return groupModel;
	}
}
