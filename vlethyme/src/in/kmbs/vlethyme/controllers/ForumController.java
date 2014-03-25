package in.kmbs.vlethyme.controllers;

import in.kmbs.vlethyme.converter.EntityToVOConverter;
import in.kmbs.vlethyme.entity.Forum;
import in.kmbs.vlethyme.entity.ForumMember;
import in.kmbs.vlethyme.entity.User;
import in.kmbs.vlethyme.service.ForumService;

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
@RequestMapping(value = "/forum")
public class ForumController {

	@Autowired
	ForumService forumService;

	@RequestMapping(value = "myforums")
	public @ResponseBody
	List<in.kmbs.vlethyme.model.Forum> myForums() {
		in.kmbs.vlethyme.model.User loggedInUser = ((in.kmbs.vlethyme.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		List<Forum> forumsEntity = forumService.getForumsByUserId(loggedInUser.getId());

		List<in.kmbs.vlethyme.model.Forum> forums = new ListVOConverter<in.kmbs.vlethyme.model.Forum>() {

			@Override
			public List<in.kmbs.vlethyme.model.Forum> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
				if (CollectionUtils.isNotEmpty(entityList)) {
					@SuppressWarnings("unchecked")
					List<Forum> forumsEntity = (List<Forum>) entityList;
					List<in.kmbs.vlethyme.model.Forum> forums = new ArrayList<in.kmbs.vlethyme.model.Forum>(entityList.size());
					for (Forum forumEntity : forumsEntity) {
						in.kmbs.vlethyme.model.Forum forum = EntityToVOConverter.convert(forumEntity);
						forums.add(forum);
					}
					return forums;
				}
				return null;
			}
		}.convertTOVO(forumsEntity);

		return forums;
	}
	
	@RequestMapping(value = "getUserAllForums")
	public @ResponseBody
	List<in.kmbs.vlethyme.model.Forum> getUserAllForums(@RequestParam Integer userId) {
		List<Forum> forumsEntity = forumService.getForumsByUserId(userId);

		List<in.kmbs.vlethyme.model.Forum> forums = new ListVOConverter<in.kmbs.vlethyme.model.Forum>() {

			@Override
			public List<in.kmbs.vlethyme.model.Forum> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
				if (CollectionUtils.isNotEmpty(entityList)) {
					@SuppressWarnings("unchecked")
					List<Forum> forumsEntity = (List<Forum>) entityList;
					List<in.kmbs.vlethyme.model.Forum> forums = new ArrayList<in.kmbs.vlethyme.model.Forum>(entityList.size());
					for (Forum forumEntity : forumsEntity) {
						in.kmbs.vlethyme.model.Forum forum = EntityToVOConverter.convert(forumEntity);
						forums.add(forum);
					}
					return forums;
				}
				return null;
			}
		}.convertTOVO(forumsEntity);

		return forums;
	}

	@RequestMapping(value = "getForumById")
	public @ResponseBody
	in.kmbs.vlethyme.model.Forum getForumById(@RequestParam Integer forumId) {
		// UserDetails user = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Forum forumEntity = forumService.getForumById(forumId);

		in.kmbs.vlethyme.model.Forum forumModel = EntityToVOConverter.convert(forumEntity);
		return forumModel;
	}

	@RequestMapping(value = "createForum", method = RequestMethod.POST)
	public @ResponseBody
	in.kmbs.vlethyme.model.Forum createForum(@RequestBody String requestString) throws JsonParseException, JsonMappingException, IOException {
		in.kmbs.vlethyme.model.User loggedInUser = ((in.kmbs.vlethyme.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Forum forum = mapper.readValue(requestString, in.kmbs.vlethyme.entity.Forum.class);
		User user = new User();
		user.setId(loggedInUser.getId());
		forum.setUser(user);
		forumService.createForum(forum);
		in.kmbs.vlethyme.model.Forum forumModel = EntityToVOConverter.convert(forum);
		return forumModel;
	}
	
	@Transactional
	@RequestMapping(value = "getForumMembers")
	public @ResponseBody
	List<in.kmbs.vlethyme.model.ForumMember> getForumMembers(@RequestParam Integer forumId) {
		Forum forumEntity = forumService.getForumById(forumId);
		if (CollectionUtils.isNotEmpty(forumEntity.getForumMembers())) {
			List<in.kmbs.vlethyme.model.ForumMember> forumMembers = new ListVOConverter<in.kmbs.vlethyme.model.ForumMember>() {
	
				@Override
				public List<in.kmbs.vlethyme.model.ForumMember> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
					if (CollectionUtils.isNotEmpty(entityList)) {
						@SuppressWarnings("unchecked")
						List<ForumMember> forumMembersEntity = (List<ForumMember>) entityList;
						List<in.kmbs.vlethyme.model.ForumMember> forumMembers = new ArrayList<in.kmbs.vlethyme.model.ForumMember>(entityList.size());
						for (ForumMember forumMemberEntity : forumMembersEntity) {
							in.kmbs.vlethyme.model.ForumMember forumMember = EntityToVOConverter.convert(forumMemberEntity);
							forumMember.setUser(EntityToVOConverter.convert(forumMemberEntity.getUser()));
							forumMember.setGroup(EntityToVOConverter.convert(forumMemberEntity.getGroup()));
							forumMember.setRole(EntityToVOConverter.convert(forumMemberEntity.getRole()));
							forumMembers.add(forumMember);
						}
						return forumMembers;
					}
					return null;
				}
			}.convertTOVO(forumEntity.getForumMembers());
			return forumMembers;
		}
		return null;
	}
	
	@Transactional
	@RequestMapping(value = "updateForumMembers", method = RequestMethod.POST)
	public @ResponseBody
	List<in.kmbs.vlethyme.model.ForumMember> updateForumMembers(@RequestBody String requestString) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		in.kmbs.vlethyme.model.Forum forum = mapper.readValue(requestString, in.kmbs.vlethyme.model.Forum.class);
		Forum forumEntity = forumService.updateForumMembers(forum);
		if (CollectionUtils.isNotEmpty(forumEntity.getForumMembers())) {
			List<in.kmbs.vlethyme.model.ForumMember> forumMembers = new ListVOConverter<in.kmbs.vlethyme.model.ForumMember>() {
	
				@Override
				public List<in.kmbs.vlethyme.model.ForumMember> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
					if (CollectionUtils.isNotEmpty(entityList)) {
						@SuppressWarnings("unchecked")
						List<ForumMember> forumMembersEntity = (List<ForumMember>) entityList;
						List<in.kmbs.vlethyme.model.ForumMember> forumMembers = new ArrayList<in.kmbs.vlethyme.model.ForumMember>(entityList.size());
						for (ForumMember forumMemberEntity : forumMembersEntity) {
							in.kmbs.vlethyme.model.ForumMember forumMember = EntityToVOConverter.convert(forumMemberEntity);
							forumMember.setUser(EntityToVOConverter.convert(forumMemberEntity.getUser()));
							forumMember.setRole(EntityToVOConverter.convert(forumMemberEntity.getRole()));
							forumMembers.add(forumMember);
						}
						return forumMembers;
					}
					return null;
				}
			}.convertTOVO(forumEntity.getForumMembers());
			return forumMembers;
		}
		return null;
	}
}
