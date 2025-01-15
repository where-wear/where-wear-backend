package WhereWear.server.wherewear.log.repository;

import WhereWear.server.wherewear.log.domain.Log;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LogRepositoryCustomImpl implements LogRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<List<Log>> findLogsByLikedCount(String category) {
        String queryStr = "SELECT l FROM Log l " +
                "LEFT JOIN l.likedLogs likedLog " +
                "WHERE l.place.category = :category "+
                "GROUP BY l " +
                "ORDER BY COUNT(likedLog) DESC";

        TypedQuery<Log> query = em.createQuery(queryStr, Log.class);
        query.setMaxResults(3);

        query.setParameter("category", category);
        List<Log> resultList = query.getResultList();

        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList);
    }


    @Override
    public Optional<List<Object[]>> countLogsByXY(double xMin, double xMax, double yMin, double yMax) {
        String queryStr = "SELECT l.place.x, l.place.y, COUNT(l) " +
                "FROM Log l " +
                "WHERE l.place.x BETWEEN :xMin AND :xMax " +
                "AND l.place.y BETWEEN :yMin AND :yMax " +
                "GROUP BY l.place.x, l.place.y";

        TypedQuery<Object[]> query = em.createQuery(queryStr, Object[].class);
        query.setParameter("xMin", xMin);
        query.setParameter("xMax", xMax);
        query.setParameter("yMin", yMin);
        query.setParameter("yMax", yMax);

        List<Object[]> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList);
    }

    @Override
    public Optional<List<Log>> findByXY(double x, double y) {
        String queryStr = "SELECT l FROM Log l " +
                "WHERE l.place.x = :x " +
                "AND l.place.y = :y";

        TypedQuery<Log> query = em.createQuery(queryStr, Log.class);
        query.setParameter("x", x);
        query.setParameter("y", y);

        List<Log> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList);
    }

    @Override
    public Optional<List<Log>> nearPlaceLogsByXY(double x, double y) {
        String queryStr = "SELECT l FROM Log l " +
                "WHERE l.place.y != :y AND l.place.x != :x " +
                "ORDER BY (6371 * ACOS(SIN(RADIANS(l.place.y)) * SIN(RADIANS(:y)) + " +
                "COS(RADIANS(l.place.y)) * COS(RADIANS(:y)) * COS(RADIANS(l.place.x) - RADIANS(:x)))) ASC";

        TypedQuery<Log> query = em.createQuery(queryStr, Log.class);
        query.setParameter("x", x);
        query.setParameter("y", y);
        query.setMaxResults(5);

        List<Log> resultList = query.getResultList();

        if (!resultList.isEmpty()) {
            // 거리 계산
            resultList.sort(Comparator.comparingDouble(log -> calculateDistance(
                    y, x, log.getPlace().getY(), log.getPlace().getX())));
        }

        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList);
    }

    // 거리 계산 메서드 (Haversine formula)
    private double calculateDistance(double startLat, double startLon, double endLat, double endLon) {
        double theta = endLon - startLon;
        double dist = Math.sin(deg2rad(startLat)) *
                Math.sin(deg2rad(endLat)) +
                Math.cos(deg2rad(startLat)) *
                        Math.cos(deg2rad(endLat)) *
                        Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344; // 거리 계산 (미터)
        return dist / 1000; // 킬로미터로 변환
    }

    // 10진수를 radian(라디안)으로 변환
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // radian(라디안)을 10진수로 변환
    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Override
    public Optional<List<Log>> findByUserId(Long userId) {
        String queryStr = "SELECT DISTINCT l FROM Log l " +
                "JOIN FETCH l.user u " +
                "JOIN FETCH l.place p " +
                "LEFT JOIN FETCH l.logImages li " +
                "WHERE l.user.id = :userId ";

        TypedQuery<Log> query = em.createQuery(queryStr, Log.class);
        query.setParameter("userId", userId);

        List<Log> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList);
    }

    @Override
    public Optional<List<Log>> findByUserEmail(String userEmail) {
        String queryStr = "SELECT DISTINCT l FROM Log l " +
                "JOIN FETCH l.user u " +
                "JOIN FETCH l.place p " +
                "LEFT JOIN FETCH l.logImages li " +
                "WHERE u.email = :userEmail";

        TypedQuery<Log> query = em.createQuery(queryStr, Log.class);
        query.setParameter("userEmail", userEmail);

        List<Log> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList);
    }
}
