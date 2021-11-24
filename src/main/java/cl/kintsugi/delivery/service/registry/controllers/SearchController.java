package cl.kintsugi.delivery.service.registry.controllers;

import java.util.logging.Logger;
import cl.kintsugi.delivery.service.registry.service.SearchService;
import cl.kintsugi.delivery.service.registry.models.entity.Group;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/delivery-service-registry")
public class SearchController {
	
	Logger logger = Logger.getLogger(GroupController.class.getName());

	@Autowired
	private SearchService searchService;
	
	//@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/groups/search")
    public ResponseEntity<List<Group>> findGroupsByKeyword(@RequestParam(name = "keyword", required = false) String keyword){
        logger.info("Initizalizing method findAllGroups() with keyword: " + keyword);
        if (!searchService.findGroupsByKeyword(keyword).isEmpty())
        	return ResponseEntity.ok(searchService.findGroupsByKeyword(keyword));
        else
        	return new ResponseEntity<List<Group>>(HttpStatus.NOT_FOUND);
    }
}
