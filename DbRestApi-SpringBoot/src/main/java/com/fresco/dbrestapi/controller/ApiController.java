package com.fresco.dbrestapi.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fresco.dbrestapi.model.Post;
import com.fresco.dbrestapi.model.UserPost;
import com.fresco.dbrestapi.model.Userposts;
import com.fresco.dbrestapi.repo.UserpostsRepository;

@RestController
public class ApiController {
	JSONParser parser = new JSONParser();
	@Autowired
	UserpostsRepository postsRepo;

	@CrossOrigin
	@PostMapping("/addpost")
//	public String post(String postBody, String user) {
	public ResponseEntity<String> post(@RequestBody Userposts userposts) {
		postsRepo.save(userposts);
		return new ResponseEntity<String>("OK 200", HttpStatus.OK);
		// return "OK 200";
	}

//	@CrossOrigin
//	@RequestMapping("/getposts")
//	public Object[] getPosts(String user) {
//		return new Object[0];
//	}

	@CrossOrigin
	@RequestMapping(value = "/getposts", method = RequestMethod.POST)
	public Userposts getPosts(@RequestBody UserPost userPost) throws ParseException {

		// JSONObject json = (JSONObject)parser.parse(user);
		// String userId = (String)json.get("user");
		Optional<Userposts> findById = postsRepo.findById(userPost.getUser());
		if (findById.isPresent()) {
			return findById.get();
		} else {
			return new Userposts();
		}

	}

//	@CrossOrigin
//	@RequestMapping(value ="/getposts", method = RequestMethod.POST)
//	public List<Post> getPosts(@RequestBody String user) throws ParseException {
//		JSONParser parser = new JSONParser();
//		 JSONObject json = (JSONObject)parser.parse(user);
//		 String userId = (String)json.get("user");
//		Optional<Userposts> findById = postsRepo.findById(userId);
//		if(findById.isPresent()) {
//			return findById.get().getPosts();
//		}else {
//			return new Userposts().getPosts();
//		}
//	}

//	@CrossOrigin
//	@RequestMapping("/delpost")
//	public String delPosts(String user, String postId) {
//		return "OK 200";
//	}

	@CrossOrigin
	@RequestMapping(value = "/delpost", method = RequestMethod.POST)
	public ResponseEntity<String> delPosts(@RequestBody UserPost userPost) {

		Optional<Userposts> findById = postsRepo.findById(userPost.getUser());
		if (findById.isPresent()) {
			Userposts userposts = findById.get();
			ArrayList<Post> posts = findById.get().getPosts();
			Iterator<Post> iterator = posts.iterator();

			while (iterator.hasNext()) {
				Post post = (Post) iterator.next();
				if (post.getPostId() == userPost.getPostId()) {
					iterator.remove();
				}

			}
			userposts.setPosts(posts);
			postsRepo.save(userposts);

		}
		return new ResponseEntity<String>("OK 200", HttpStatus.OK);
	}
//	@CrossOrigin
//	@RequestMapping("/searchuser")
//	public HashMap<String, Boolean> searchUser(String user, String searchText) {
//		return new HashMap<String, Boolean>();
//	}

	@CrossOrigin
	@RequestMapping(value = "/searchuser", method = RequestMethod.POST)
	public Map<String, Boolean> searchUser(@RequestBody UserPost userPost) {
		Map<String, Boolean> map = new LinkedHashMap<>();
		List<Userposts> findAll = postsRepo.findAll();
		Collections.sort(findAll);

		List<Userposts> filteredSearch = new ArrayList<>();

		findAll.forEach(user -> {

			String tempUser = user.get_id().toLowerCase();
			String searchString = userPost.getSearchText().toLowerCase();
			if (tempUser.contains(searchString)) {

				filteredSearch.add(user);
			}

		});

		filteredSearch.forEach(user -> {

			if (user.get_id().equals(userPost.getUser())) {
				map.put(user.get_id(), true);
			} else {
				map.put(user.get_id(), false);
			}

		});

		return map;
	}

//	@CrossOrigin
//	@RequestMapping("/subscriber")
//	public String subscriber(String user, String subuser) {
//		return "OK 200";
//	}

	@CrossOrigin
	@RequestMapping(value = "/subscriber", method = RequestMethod.POST)
	public ResponseEntity<String> subscriber(@RequestBody UserPost userPost) {
		Optional<Userposts> findById = postsRepo.findById(userPost.getUser());
		if (findById.isPresent()) {
			Userposts userposts = findById.get();
			ArrayList<String> subscribed = userposts.getSubscribed();

			if (!StringUtils.isEmpty(subscribed)) {
				subscribed.add(userPost.getSubuser());
				userposts.setSubscribed(subscribed);
			} else if (StringUtils.isEmpty(subscribed)) {
				subscribed = new ArrayList<>();
				subscribed.add(userPost.getSubuser());
				userposts.setSubscribed(subscribed);
			}
			postsRepo.save(userposts);

		}
		return new ResponseEntity<String>("OK 200", HttpStatus.OK);
	}
}
