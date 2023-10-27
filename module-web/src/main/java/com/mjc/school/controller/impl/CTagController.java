package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.tag.TagDTOReq;
import com.mjc.school.service.dto.tag.TagDTOResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class CTagController implements TagController {
    @Autowired
    TagService service;

    @Override
    @GetMapping(value = "tags")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1") int size
    ) {
        return service.readAll();
    }

    @Override
    @GetMapping(value = "tags/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "tags/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTOResp create(@RequestBody TagDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "tags/{id:\\d+}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TagDTOResp update(@PathVariable Long id, @RequestBody TagDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "tags/{id:\\d+}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "news/{id:\\d+}/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTOResp> readByNewsId(@PathVariable Long id) {
        return service.readByNewsId(id);
    }
}
