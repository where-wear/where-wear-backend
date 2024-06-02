package WhereWear.server.wherewear.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public Tag addTag(String tagName){
        Tag tag = new Tag(tagName);
        return tagRepository.save(tag);
    }

    public Tag findTagById(Long TagId){
        return tagRepository.findById(TagId).orElseThrow(() -> new IllegalArgumentException("Unexpected tag"));
    }
}
