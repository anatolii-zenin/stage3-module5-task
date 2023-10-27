package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.news.NewsDTOReq;
import com.mjc.school.service.dto.news.NewsDTOResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class CNewsController implements NewsController {
    @Autowired
    NewsService service;

    @Override
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDTOResp> readAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1") int size
    ) {
        return service.readAll();
    }

    @Override
    @GetMapping(value = "{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDTOResp readById(@PathVariable Long id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDTOResp create(@RequestBody NewsDTOReq createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "{id:\\d+}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public NewsDTOResp update(@PathVariable Long id, @RequestBody NewsDTOReq updateRequest) {
        updateRequest.setId(id);
        return service.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "{id:\\d+}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Override
    @GetMapping(value = "read-by-criteria", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDTOResp> readByCriteria(@RequestBody NewsDTOReq req) {
        return service.readByCriteria(req);
    }
}
