package com.codeup.codeupspringblog.controllers;

import com.codeup.codeupspringblog.models.Post;
import com.codeup.codeupspringblog.repositories.PostRepository;
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

    public PostController(PostRepository postDAO){
        this.postDAO = postDAO;
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
        return "posts/show";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.GET)
//    @ResponseBody
    public String viewPostCreate() {
        return "/posts/create";
    }

    @RequestMapping(path = "/posts/create", method = RequestMethod.POST)
//    @ResponseBody
    public String postCreatedPost(@RequestParam (name="title") String title, @RequestParam (name="body") String body) {

            postDAO.save(new Post(title,body));


        return "redirect:/posts";
        }

}
