package WhereWear.server.wherewear.log.service;

import WhereWear.server.wherewear.fashion.fashionItem.domain.FashionItem;
import WhereWear.server.wherewear.fashion.fashionItem.dto.FashionItemRequest;
import WhereWear.server.wherewear.fashion.fashionItem.service.FashionItemService;
import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.log.repository.LogRepository;
import WhereWear.server.wherewear.logImage.service.LogImageService;
import WhereWear.server.wherewear.place.domain.Place;
import WhereWear.server.wherewear.place.service.PlaceService;
import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateLogService {

    private final LogRepository logRepository;
    private final UserService userService;
    private final FashionItemService fashionItemService;
    private final PlaceService placeService;
    private final LogImageService logImageService;

    public Log create(String email,
                      String text,
                      List<MultipartFile> imageUrls,
                      List<FashionItemRequest> items,
                      double x,
                      double y,
                      String address,
                      String placeName,
                      Boolean isShow,
                      List<String> tags) throws IOException {

        User user = userService.findByEmail(email);

        List<FashionItem> fashionItems = fashionItemService.createFashionItems(items);

        Place place = placeService.createPlace(x,y,address,placeName);

        List<String> publicUrls = logImageService.createImages(imageUrls);

        return logRepository.save(Log.of(user, place, fashionItems, text, publicUrls, isShow, tags));
    }
}
