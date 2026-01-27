package ug.visituganda.visituganda.service_impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.dto.request.PostRequest;
import ug.visituganda.visituganda.dto.response.PostResponse;
import ug.visituganda.visituganda.modal.Post;
import ug.visituganda.visituganda.repository.PostRepository;
import ug.visituganda.visituganda.service.PostService;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostResponse createPost(PostRequest request) {

        try {
            Post post = new Post();
            post.setTitle(request.title());
            post.setDate(LocalDate.parse(request.date()));
            post.setDescription(request.description());
            post.setImage(request.image().getBytes());

            Post saved = postRepository.save(post);

            return mapToResponse(saved);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save post: " + e.getMessage());
        }
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToResponse(post);
    }

    private PostResponse mapToResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getDate().toString(),
                post.getDescription(),
                Base64.getEncoder().encodeToString(post.getImage())
        );
    }
}
