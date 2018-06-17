package com.jyoti.dev.social.api;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FacebookController {
	private Facebook facebookApi;
	private ConnectionRepository connectionRepository;
	
	public FacebookController(Facebook facebookApi, ConnectionRepository connectionRepository) {
		System.out.println("Inside constructor");
		this.facebookApi = facebookApi;
		this.connectionRepository = connectionRepository;
	}
	
	@GetMapping("/getAllFeeds")
	public String getAllFeeds(Model model) {
		System.out.println("inside method");
		if(connectionRepository.findPrimaryConnection(Facebook.class)==null) {
			System.out.println("Not connected to facebook");
			return "redirect:/connect/facebook";
		}
		System.out.println("Connected to facebook successfully");
		PagedList<Post> posts = facebookApi.feedOperations().getFeed();
		model.addAttribute("posts", posts);
		model.addAttribute("profileName", posts.get(0).getFrom().getName());
		return "profile";
		
	}
	

}
