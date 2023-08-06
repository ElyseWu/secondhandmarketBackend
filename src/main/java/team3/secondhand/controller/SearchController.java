package team3.secondhand.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "lat") double lat,
            @RequestParam(name = "lon") double lon,
            @RequestParam(name = "distance", required=false) String distance
    ) {
        return searchService.searchByKeyword(keyword, lat, lon, distance);
    }

    @GetMapping("/items/{category}")
    public List<ItemDto> searchByCategory(@PathVariable("category") String category,
                                          @RequestParam(name = "lat") double lat,
                                          @RequestParam(name = "lon") double lon,
                                          @RequestParam(name = "distance", required=false) String distance) {
        return searchService.searchByCategory(category, lat, lon, distance);
    }

}
