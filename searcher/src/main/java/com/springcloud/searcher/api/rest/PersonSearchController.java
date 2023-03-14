package com.springcloud.searcher.api.rest;

import com.springcloud.searcher.api.PersonsSearchHandler;
import com.springcloud.searcher.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonSearchController {

  private final PersonsSearchHandler handler;

  @GetMapping("/search")
  public List<Person> search(@RequestParam String search) {
    if (!StringUtils.hasText(search)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "search string is empty");
    }
//     log.info("Service-1");
     log.info("Service-2");
    return handler.search(search);
  }

}
