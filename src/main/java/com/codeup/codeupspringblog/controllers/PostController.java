package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.models.User;
import com.codeup.codeupspringblog.repositories.PostRepository;
import com.codeup.codeupspringblog.repositories.UserRepository;
import com.codeup.codeupspringblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PostController {

    PostRepository postDAO;
    UserRepository userDAO;

    private EmailService emailService;

//    public PostController() {
//        this.emailService = emailService;
//    }

    public PostController(PostRepository postDAO, UserRepository userDAO,EmailService emailService){

        this.postDAO = postDAO;
        this.userDAO = userDAO;
        this.emailService = emailService;
    }


@GetMapping(path = "/email")
public String sendEmail(){
    emailService.prepareAndSend("subjecttttt", "get shrimpeed");
    return "redirect:/posts";
}
    @GetMapping(path = "/")
//    @ResponseBody
    public String postHome() {

        return "home";
    }

    @GetMapping(path = "/posts")
//    @ResponseBody
    public String postIndex(Model model) {
        List<Post> posts = postDAO.findAll();

        model.addAttribute("posts",posts);
        return "posts/index";
    }

    @RequestMapping(path = "/posts/{id}", method = RequestMethod.GET)
//    @ResponseBody
    public String postId(@PathVariable int id, Model model) {
        Post post = postDAO.findById(id).get();
//        model.addAttribute("id",id);
        model.addAttribute("post", post);
        model.addAttribute("creator", post.getOwner());
        return "posts/show";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
//    @ResponseBody
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post());

        return "/posts/create";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.POST)
//    @ResponseBody
    public String create(@ModelAttribute Post post) {
//        System.out.println("here");
        User creator = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(post.getBody());
        System.out.println(post.getTitle());
        post.setOwner(creator);
//            Post post = new Post(title, body, creator);
            postDAO.save(post);
            if(creator.getPosts().size() > 0){
                List<Post> posts = creator.getPosts();
                posts.add(post);
                creator.setPosts(posts);
                userDAO.save(creator);

            }else{
                List<Post> posts = new ArrayList<>();
                posts.add(post);
                creator.setPosts(posts);
                userDAO.save(creator);

            }
        System.out.println(creator.getPosts());



        return "redirect:/posts";
        }


    @RequestMapping(path = "/posts/{id}/edit", method = RequestMethod.GET)
    public String showEditForm(@PathVariable int id,Model model) {
        Post post = postDAO.findById(id).get();
        model.addAttribute("post", post);
        return "/posts/edit";
    }

    @RequestMapping(path = "/posts/{id}/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute Post post) {
        postDAO.save(post);
        return "redirect:/posts";
    }



}
