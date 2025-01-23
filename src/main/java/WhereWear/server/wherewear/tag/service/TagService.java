package WhereWear.server.wherewear.tag.service;

import WhereWear.server.wherewear.place.service.PlaceService;
import WhereWear.server.wherewear.tag.domain.Tag;
import WhereWear.server.wherewear.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final PlaceService placeService;

    public List<String> getHotKeywords(String category) {
        placeService.getPlaceByCategory(category);
        return tagRepository.findHotKeywords(category);
    }
}
