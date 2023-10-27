package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.comment.CommentDTOReq;
import com.mjc.school.service.dto.comment.CommentDTOResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CCommentController implements CommentController {
    @Autowired
    CommentService service;
    @Override
    @GetMapping(value = "comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        return service.readAll(page, size);
    }

    @Override
    @GetMapping(value = "/comments/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "/comments/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTOResp create(@RequestBody CommentDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/comments/{id:\\d+}/update")
    @ResponseStatus(HttpStatus.OK)
    public CommentDTOResp update(@PathVariable Long id, @RequestBody CommentDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/comments/{id:\\d+}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{id:\\d+}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTOResp> readByNewsId(@PathVariable Long id) {
        return service.readCommentsByNewsId(id);
    }
}
