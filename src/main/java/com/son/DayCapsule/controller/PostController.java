package com.son.DayCapsule.controller;

import com.son.DayCapsule.controller.fom.PostForm;
import com.son.DayCapsule.domain.Post;
import com.son.DayCapsule.domain.User;
import com.son.DayCapsule.service.PostService;
import com.son.DayCapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/post/new")
    public String postForm(Model model) {
        model.addAttribute("postForm", new PostForm());

        return "postCreate";
    }

    @PostMapping("/post/create")
    public String postCreate(PostForm postForm) {
        UserDetails me = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(me.getUsername());
        Post post = Post.createPost(user, postForm.getTitle(), postForm.getBody(), user.getUsername());
        postService.create(post);
        return "home";
    }

}
