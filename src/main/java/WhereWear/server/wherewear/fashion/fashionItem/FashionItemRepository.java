package WhereWear.server.wherewear.fashion.fashionItem;

import WhereWear.server.wherewear.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FashionItemRepository extends JpaRepository<FashionItem, Long> {

}
