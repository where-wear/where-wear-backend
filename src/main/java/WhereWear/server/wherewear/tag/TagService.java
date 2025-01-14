package WhereWear.server.wherewear.tag;

import WhereWear.server.wherewear.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final PlaceService placeService;

    public Tag addTag(String tagName) {
        Tag tag = new Tag(tagName);
        return tagRepository.save(tag);
    }

    public List<String> getHotKeywords(String category) {
        placeService.getPlaceByCategory(category);
        return tagRepository.findHotKeywords(category);
    }

    public Tag findTagById(Long TagId) {
        return tagRepository.findById(TagId).orElseThrow(() -> new IllegalArgumentException("Unexpected tag"));
    }
}
