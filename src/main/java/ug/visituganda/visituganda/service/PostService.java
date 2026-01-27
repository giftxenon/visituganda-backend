package ug.visituganda.visituganda.service;


import ug.visituganda.visituganda.dto.request.PostRequest;
import ug.visituganda.visituganda.dto.response.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostRequest request);

    List<PostResponse> getAllPosts();

    PostResponse getPost(Long id);
}
