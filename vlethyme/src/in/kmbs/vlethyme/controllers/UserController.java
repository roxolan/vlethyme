package in.kmbs.vlethyme.controllers;

import in.kmbs.vlethyme.converter.EntityToVOConverter;
import in.kmbs.vlethyme.entity.User;
import in.kmbs.vlethyme.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "getUsers")
	public @ResponseBody
	List<in.kmbs.vlethyme.model.User> getUsers() {
		List<in.kmbs.vlethyme.model.User> users = null;
		List<in.kmbs.vlethyme.entity.User> usersEntity = userService.getUsers();

		users = new ListVOConverter<in.kmbs.vlethyme.model.User>() {

			@Override
			public List<in.kmbs.vlethyme.model.User> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
				if (CollectionUtils.isNotEmpty(entityList)) {
					@SuppressWarnings("unchecked")
					List<User> usersEntity = (List<User>) entityList;
					List<in.kmbs.vlethyme.model.User> users = new ArrayList<in.kmbs.vlethyme.model.User>(entityList.size());
					for (User userEntity : usersEntity) {
						in.kmbs.vlethyme.model.User user = EntityToVOConverter.convert(userEntity);
						users.add(user);
					}
					return users;
				}
				return null;
			}
		}.convertTOVO(usersEntity);

		return users;
	}

	@RequestMapping(value = "getUserById")
	public @ResponseBody
	in.kmbs.vlethyme.model.User getUSerById(@RequestParam Integer userId) {
		User userEntity = userService.findUserById(userId);

		in.kmbs.vlethyme.model.User userModel = EntityToVOConverter.convert(userEntity);
		return userModel;
	}

	@RequestMapping(value = "isUsernameAvailable", method = RequestMethod.GET)
	public @ResponseBody
	boolean isUsernameAvailable(@RequestParam String username) {
		User user = userService.findUserByUsername(username);
		if (user != null) {
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "createUser", method = RequestMethod.POST)
	public @ResponseBody
	in.kmbs.vlethyme.model.User createUser(@RequestBody String requestString) throws JsonParseException, JsonMappingException, IOException {
		// UserDetails user = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		User user = mapper.readValue(requestString, in.kmbs.vlethyme.entity.User.class);
		userService.createUser(user);
		in.kmbs.vlethyme.model.User userModel = EntityToVOConverter.convert(user);
		userModel.setRole(EntityToVOConverter.convert(user.getRole()));
		return userModel;
	}

	@RequestMapping(value = "findUsersByNameLike")
	public @ResponseBody
	List<in.kmbs.vlethyme.model.User> findUsersByNameLike(@RequestParam String q, HttpServletRequest request) {
		List<in.kmbs.vlethyme.model.User> users = null;
		int limit = StringUtils.isNotBlank(request.getParameter("limit")) ? Integer.valueOf(request.getParameter("limit")) : 10;
		if (StringUtils.isNotBlank(q)) {
			List<in.kmbs.vlethyme.entity.User> usersEntity = userService.findUsersByNameLike(q, limit);

			users = new ListVOConverter<in.kmbs.vlethyme.model.User>() {

				@Override
				public List<in.kmbs.vlethyme.model.User> convertTOVO(@SuppressWarnings("rawtypes") List entityList) {
					if (CollectionUtils.isNotEmpty(entityList)) {
						@SuppressWarnings("unchecked")
						List<User> usersEntity = (List<User>) entityList;
						List<in.kmbs.vlethyme.model.User> users = new ArrayList<in.kmbs.vlethyme.model.User>(entityList.size());
						for (User userEntity : usersEntity) {
							in.kmbs.vlethyme.model.User user = EntityToVOConverter.convert(userEntity);
							users.add(user);
						}
						return users;
					}
					return null;
				}
			}.convertTOVO(usersEntity);
		}
		return users;
	}
}
