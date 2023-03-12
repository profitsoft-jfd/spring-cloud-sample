package com.springcloud.searcher.api.rest;

import com.springcloud.searcher.api.PersonsSearchHandler;
import com.springcloud.searcher.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonSearchController {

  private final PersonsSearchHandler handler;

  @GetMapping("/search")
  public List<Person> search(@RequestParam String search) {
    if (!StringUtils.hasText(search)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "search string is empty");
    }
    return handler.search(search);
  }

}
