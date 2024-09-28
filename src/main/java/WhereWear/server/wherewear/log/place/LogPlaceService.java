package WhereWear.server.wherewear.log.place;

import WhereWear.server.wherewear.fashion.fashionItem.FashionItem;
import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogRepositoryCustom;
import WhereWear.server.wherewear.log.LogService;
import WhereWear.server.wherewear.place.Place;
import WhereWear.server.wherewear.place.PlaceLogSummary;
import WhereWear.server.wherewear.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LogPlaceService {
    private final LogService logService;
    private final PlaceService placeService;

    public Log addPlaceToLog(Long logId, double x, double y, String address, String placeName ) {
        Log log = logService.findByLogId(logId);
        Place place = placeService.addPlace(x,y,address,placeName);

        log.setPlace(place);

        placeService.savePlace(place);
        return logService.saveLog(log);
    }

    public List<PlaceLogSummary> processLogs(double x, double y) {
        // 1km 반경 내의 로그들을 그룹화
        Map<String, List<Log>> groupedLogs = getGroupedLogsByXY(x, y);

        // 그룹화된 로그 출력
        return getPlaceLogSummaries(groupedLogs);
    }

    public Map<String, List<Log>> getGroupedLogsByXY(double x, double y) {
        // 1km 반경 내의 모든 로그 가져오기
        List<Log> logs = getPlaceLogs(x, y);

        // 같은 x, y 값을 가진 로그들을 그룹화
        return groupLogsByXY(logs);
    }

    public List<Log> getPlaceLogs(double x, double y) {
        // x, y의 범위 지정 (반경을 기준으로 계산)
        double xMin = x - 0.01;  // 약 1km 내외
        double xMax = x + 0.01;
        double yMin = y - 0.01;
        double yMax = y + 0.01;

        // 특정 범위 내의 로그를 가져오는 커스텀 메서드 호출
        Optional<List<Log>> logsOptional = logService.findLogsByXYRange(xMin, xMax, yMin, yMax);

        // 결과를 처리하여 반환
        return logsOptional.orElse(Collections.emptyList());
    }

    public Map<String, List<Log>> groupLogsByXY(List<Log> logs) {
        return logs.stream().collect(Collectors.groupingBy(log -> log.getPlace().getX() + "," + log.getPlace().getY()));
    }

    public List<PlaceLogSummary> getPlaceLogSummaries(Map<String, List<Log>> groupedLogs) {
        List<PlaceLogSummary> summaries = new ArrayList<>();

        for (Map.Entry<String, List<Log>> entry : groupedLogs.entrySet()) {
            List<Log> logs = entry.getValue();  // 해당 좌표의 로그 리스트

            if (!logs.isEmpty()) {
                int logCount = logs.size();  // 해당 좌표에서 발생한 로그의 개수
                Place place = logs.get(0).getPlace();  // 로그 리스트 중 첫 번째 로그의 Place 객체 사용
                summaries.add(new PlaceLogSummary(logCount, place));  // Place 객체와 로그 개수를 DTO로 생성
            }
        }

        return summaries;
    }

}
