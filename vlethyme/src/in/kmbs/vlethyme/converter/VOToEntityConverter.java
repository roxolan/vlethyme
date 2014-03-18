package in.kmbs.vlethyme.converter;

import in.kmbs.vlethyme.entity.Group;

public class VOToEntityConverter {
	
	public static Group convert(in.kmbs.vlethyme.model.Group group) {
		Group groupEntity = new Group();
		groupEntity.setId(group.getId());
		groupEntity.setName(group.getName());

		return groupEntity;
	}
}
