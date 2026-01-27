package ug.visituganda.visituganda.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ug.visituganda.visituganda.dto.request.PostRequest;
import ug.visituganda.visituganda.dto.response.PostResponse;
import ug.visituganda.visituganda.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(
            @RequestParam String title,
            @RequestParam String date,
            @RequestParam String description,
            @RequestParam MultipartFile image
    ) {
        PostRequest request = new PostRequest(title, date, description, image);
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }
}
