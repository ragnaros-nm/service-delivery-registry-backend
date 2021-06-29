package cl.kintsugi.delivery.service.registry.service;

import cl.kintsugi.delivery.service.registry.repository.ISearchRepository;
import cl.kintsugi.delivery.service.registry.models.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class SearchService implements ISearchService{
    Logger logger = Logger.getLogger(SearchService.class.getName());

    @Autowired
    private ISearchRepository searchRepository;

    @Override
    public List<Group> findGroupsByKeyword(String keyword) {
        try {
            return searchRepository.findGroupsByKeyword(keyword);
        }
        catch(Exception e) {
            logger.info(e.getMessage());
            return null;
        }
    }
}
