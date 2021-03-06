package com.son.DayCapsule.controller;

import com.son.DayCapsule.controller.fom.PostForm;
import com.son.DayCapsule.domain.Post;
import com.son.DayCapsule.domain.User;
import com.son.DayCapsule.service.PostService;
import com.son.DayCapsule.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/post/new")
    public String postForm(Model model) {
        model.addAttribute("postForm", new PostForm());

        return "post/postCreate";
    }

    @PostMapping("/post/create")
    public String postCreate(PostForm postForm) {
        UserDetails me = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(me.getUsername());
        postService.create(user, postForm.getTitle(), postForm.getBody(), user.getUsername());

        return "redirect:/post/main";
    }

    @GetMapping("/post/main")
    public String main(@PageableDefault Pageable pageable, Model model) {
        /* 현재 로그인 인증 성공한 UserDetails를 불러온다 */
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        /* 데이터 JPA의 페이징처리가 된 Page형태의 post들을 조회한다 */
        Page<Post> posts = postService.findAllByPage(pageable);
        model.addAttribute("posts", posts);

        return "home";
    }

    @GetMapping("/post/body/search")
    public String searchByBody(
            @PageableDefault Pageable pageable,
            HttpServletRequest request,
            Model model) {
        String body = request.getParameter("body");
        Page<Post> posts = postService.findAllByBodyContaining(body, pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("saved", body);
        return "home";
    }

    @GetMapping("/post/{id}/info")
    public String postInfo(@PathVariable("id") Long id, Model model) {
        // 현재 Security Context에 있는 사용자 정보를 불러온다
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        Post post = postService.findOne(id, true);
        if (username.equals(post.getWriter())) {
            model.addAttribute("username", username);
        }
        model.addAttribute("post", post);
        return "post/postInfo";
    }

    @GetMapping("/post/{id}/update")
    public String postUpdate(@PathVariable("id") Long id, Model model) {
        Post post = postService.findOne(id, false);
        model.addAttribute("post", post);
        model.addAttribute("postForm", new PostForm());
        return "post/postUpdate";
    }

    @PostMapping("/post/{id}/update")
    public String update(@PathVariable("id") Long id, PostForm postForm) {
        String title = postForm.getTitle();
        String body = postForm.getBody();
        postService.update(id, title, body);
        return "redirect:/post/main";
    }

}
