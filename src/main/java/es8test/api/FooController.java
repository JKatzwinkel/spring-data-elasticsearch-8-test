package es8test.api;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es8test.es.model.FooEntity;
import es8test.es.repo.FooRepo;
import es8test.es.service.SearchService;

@RestController
@RequestMapping("/foo")
public class FooController {

    @Autowired
    private FooRepo repo;

    @Autowired
    private SearchService search;

    @RequestMapping(value = "/get/{id}", method=RequestMethod.GET)
    public ResponseEntity<FooEntity> get(@PathVariable String id) {
        return new ResponseEntity<>(
            repo.findById(id).get(),
            HttpStatus.OK
        );
    }

    @RequestMapping(value = "/", method=RequestMethod.POST)
    public ResponseEntity<FooEntity> post(@RequestBody FooEntity foo) {
        repo.save(foo);
        return new ResponseEntity<>(
            foo, HttpStatus.CREATED
        );
    }

    @RequestMapping(value = "/search", method=RequestMethod.GET)
    public ResponseEntity<List<FooEntity>> search(@RequestParam String q) {
        System.out.println(q);
        return new ResponseEntity<>(
            search.searchResults(q), HttpStatus.OK
        );
    }

    @RequestMapping(value = "/list", method=RequestMethod.GET)
    public ResponseEntity<List<FooEntity>> list(@RequestParam List<String> ids) {
        List<FooEntity> results = StreamSupport.stream(
            repo.findAllById(ids).spliterator(), false
        ).toList();
        return new ResponseEntity<>(
            results, HttpStatus.CREATED
        );
    }

}
