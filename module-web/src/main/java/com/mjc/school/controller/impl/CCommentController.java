package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.comment.CommentDTOReq;
import com.mjc.school.service.dto.comment.CommentDTOResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(produces = "application/json", tags = "Comments", description = "CRUD operations for comments")
public class CCommentController implements CommentController {
    @Autowired
    CommentService service;
    @Override
    @GetMapping(value = "/comments")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List all comments. Accepts parameters 'page' for page number and " +
            "'size' for the amount of entries returned per page.",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public List<com.mjc.school.service.dto.comment.CommentDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        return service.readAll(page, size);
    }

    @Override
    @GetMapping(value = "/comments/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve a comment by ID", response = CommentDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "Comment with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public com.mjc.school.service.dto.comment.CommentDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "/comments/create")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a comment", response = CommentDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a comment"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public com.mjc.school.service.dto.comment.CommentDTOResp create(@RequestBody CommentDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/comments/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a comment with given ID", response = CommentDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the comment"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "Comment with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public com.mjc.school.service.dto.comment.CommentDTOResp update(@PathVariable Long id, @RequestBody CommentDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/comments/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a comment with given ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the comment"),
            @ApiResponse(code = 404, message = "Comment with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{id:\\d+}/comments")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Read a comment by news ID ID", response = CommentDTOResp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved requested data"),
            @ApiResponse(code = 400, message = "Received a malformed request"),
            @ApiResponse(code = 404, message = "News with given ID was not found"),
            @ApiResponse(code = 500, message = "Unexpected internal error")
    })
    public List<CommentDTOResp> readByNewsId(@PathVariable Long id) {
        return service.readCommentsByNewsId(id);
    }
}
