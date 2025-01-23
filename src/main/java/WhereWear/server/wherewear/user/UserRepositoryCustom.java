package WhereWear.server.wherewear.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustom {
    @PersistenceContext
    private final EntityManager em;

    public List<User> findUserByName(String userName) {
        String searchKeyword = "%" + userName.trim() + "%";
        return em.createQuery(
                        "SELECT u " +
                                "FROM User u " +
                                "WHERE LOWER(u.nickname) LIKE :userName", User.class)
                .setParameter("userName", searchKeyword)
                .setHint("org.hibernate.cacheable", false)
                .setMaxResults(20)
                .getResultList();
    }

    public List<User> findUsers() {
        return em.createQuery(
                        "SELECT u " +
                                "FROM User u ", User.class)
                .setMaxResults(20)
                .setHint("org.hibernate.cacheable", false)
                .getResultList();
    }
}
