package in.kmbs.vlethyme.controllers;

import in.kmbs.vlethyme.converter.EntityToVOConverter;
import in.kmbs.vlethyme.entity.Group;
import in.kmbs.vlethyme.entity.GroupUser;
import in.kmbs.vlethyme.entity.User;
import in.kmbs.vlethyme.service.GroupService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/group")
public class GroupController {

	@Autowired
	GroupService groupService;

	@RequestMapping(value = "mygroups")
	public @ResponseBody
	List<in.kmbs.vlethyme.model.Group> myGroups() {
		in.kmbs.vlethyme.model.User loggedInUser = ((in.kmbs.vlethyme.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		List<Group> groupsEntity = groupService.getGroupsByUserId(loggedInUser.getId());

		List<in.kmbs.vlethyme.model.Group> groups = new ListVOConverter<in.kmbs.vlethyme.model.Group>() {

			@Override
			public List<in.kmbs.vlethyme.model.Group> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
				if (CollectionUtils.isNotEmpty(entityList)) {
					@SuppressWarnings("unchecked")
					List<Group> groupsEntity = (List<Group>) entityList;
					List<in.kmbs.vlethyme.model.Group> groups = new ArrayList<in.kmbs.vlethyme.model.Group>(entityList.size());
					for (Group groupEntity : groupsEntity) {
						in.kmbs.vlethyme.model.Group group = EntityToVOConverter.convert(groupEntity);
						groups.add(group);
					}
					return groups;
				}
				return null;
			}
		}.convertTOVO(groupsEntity);

		return groups;
	}
	
	@RequestMapping(value = "getUserAllGroups")
	public @ResponseBody
	List<in.kmbs.vlethyme.model.Group> getUserAllGroups(@RequestParam Integer userId) {
		List<Group> groupsEntity = groupService.getGroupsByUserId(userId);

		List<in.kmbs.vlethyme.model.Group> groups = new ListVOConverter<in.kmbs.vlethyme.model.Group>() {

			@Override
			public List<in.kmbs.vlethyme.model.Group> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
				if (CollectionUtils.isNotEmpty(entityList)) {
					@SuppressWarnings("unchecked")
					List<Group> groupsEntity = (List<Group>) entityList;
					List<in.kmbs.vlethyme.model.Group> groups = new ArrayList<in.kmbs.vlethyme.model.Group>(entityList.size());
					for (Group groupEntity : groupsEntity) {
						in.kmbs.vlethyme.model.Group group = EntityToVOConverter.convert(groupEntity);
						groups.add(group);
					}
					return groups;
				}
				return null;
			}
		}.convertTOVO(groupsEntity);

		return groups;
	}

	@RequestMapping(value = "getGroupById")
	public @ResponseBody
	in.kmbs.vlethyme.model.Group getGroupById(@RequestParam Integer groupId) {
		// UserDetails user = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Group groupEntity = groupService.getGroupById(groupId);

		in.kmbs.vlethyme.model.Group groupModel = EntityToVOConverter.convert(groupEntity);
		return groupModel;
	}

	@RequestMapping(value = "createGroup", method = RequestMethod.POST)
	public @ResponseBody
	in.kmbs.vlethyme.model.Group createGroup(@RequestBody String requestString) throws JsonParseException, JsonMappingException, IOException {
		in.kmbs.vlethyme.model.User loggedInUser = ((in.kmbs.vlethyme.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Group group = mapper.readValue(requestString, in.kmbs.vlethyme.entity.Group.class);
		User user = new User();
		user.setId(loggedInUser.getId());
		group.setUser(user);
		groupService.createGroup(group);
		in.kmbs.vlethyme.model.Group groupModel = EntityToVOConverter.convert(group);
		return groupModel;
	}
	
	@Transactional
	@RequestMapping(value = "getGroupMembers")
	public @ResponseBody
	List<in.kmbs.vlethyme.model.GroupUser> getGroupMembers(@RequestParam Integer groupId) {
		Group groupEntity = groupService.getGroupById(groupId);
		if (CollectionUtils.isNotEmpty(groupEntity.getGroupUsers())) {
			List<in.kmbs.vlethyme.model.GroupUser> groupUsers = new ListVOConverter<in.kmbs.vlethyme.model.GroupUser>() {
	
				@Override
				public List<in.kmbs.vlethyme.model.GroupUser> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
					if (CollectionUtils.isNotEmpty(entityList)) {
						@SuppressWarnings("unchecked")
						List<GroupUser> groupUsersEntity = (List<GroupUser>) entityList;
						List<in.kmbs.vlethyme.model.GroupUser> groupUsers = new ArrayList<in.kmbs.vlethyme.model.GroupUser>(entityList.size());
						for (GroupUser groupUserEntity : groupUsersEntity) {
							in.kmbs.vlethyme.model.GroupUser groupUser = EntityToVOConverter.convert(groupUserEntity);
							groupUser.setUser(EntityToVOConverter.convert(groupUserEntity.getUser()));
							groupUser.setRole(EntityToVOConverter.convert(groupUserEntity.getRole()));
							groupUsers.add(groupUser);
						}
						return groupUsers;
					}
					return null;
				}
			}.convertTOVO(groupEntity.getGroupUsers());
			return groupUsers;
		}
		return null;
	}
	
	@Transactional
	@RequestMapping(value = "updateGroupMembers", method = RequestMethod.POST)
	public @ResponseBody
	List<in.kmbs.vlethyme.model.GroupUser> updateGroupMembers(@RequestBody String requestString) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		in.kmbs.vlethyme.model.Group group = mapper.readValue(requestString, in.kmbs.vlethyme.model.Group.class);
		Group groupEntity = groupService.updateGroupMembers(group);
		if (CollectionUtils.isNotEmpty(groupEntity.getGroupUsers())) {
			List<in.kmbs.vlethyme.model.GroupUser> groupUsers = new ListVOConverter<in.kmbs.vlethyme.model.GroupUser>() {
	
				@Override
				public List<in.kmbs.vlethyme.model.GroupUser> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
					if (CollectionUtils.isNotEmpty(entityList)) {
						@SuppressWarnings("unchecked")
						List<GroupUser> groupUsersEntity = (List<GroupUser>) entityList;
						List<in.kmbs.vlethyme.model.GroupUser> groupUsers = new ArrayList<in.kmbs.vlethyme.model.GroupUser>(entityList.size());
						for (GroupUser groupUserEntity : groupUsersEntity) {
							in.kmbs.vlethyme.model.GroupUser groupUser = EntityToVOConverter.convert(groupUserEntity);
							groupUser.setUser(EntityToVOConverter.convert(groupUserEntity.getUser()));
							groupUser.setRole(EntityToVOConverter.convert(groupUserEntity.getRole()));
							groupUsers.add(groupUser);
						}
						return groupUsers;
					}
					return null;
				}
			}.convertTOVO(groupEntity.getGroupUsers());
			return groupUsers;
		}
		return null;
	}
}
