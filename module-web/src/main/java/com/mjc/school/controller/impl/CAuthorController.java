package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorController;
import com.mjc.school.service.dto.author.AuthorDTOReq;
import com.mjc.school.service.dto.author.AuthorDTOResp;
import com.mjc.school.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CAuthorController implements AuthorController {
    @Autowired
    AuthorService service;

    @Override
    @GetMapping(value = "authors")
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return service.readAll(page, size);
    }

    @Override
    @GetMapping(value = "authors/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "authors/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTOResp create(@RequestBody AuthorDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "authors/{id:\\d+}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTOResp update(@PathVariable Long id, @RequestBody AuthorDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "authors/{id:\\d+}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "news/{id:\\d+}/author")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTOResp readByNewsId(@PathVariable Long id) {
        return service.readAuthorByNewsId(id);
    }
}
