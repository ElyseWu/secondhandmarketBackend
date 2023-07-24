package team3.secondhand.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team3.secondhand.model.ItemDto;
import team3.secondhand.service.SearchService;

import java.util.List;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "/search")
    public List<ItemDto> searchItemsByKeyword(
            @RequestParam(name = "keyword") String keyword
    ) {
        return searchService.searchByKeyword(keyword);
    }
}
