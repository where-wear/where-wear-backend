package WhereWear.server.wherewear.logRecommend;

import WhereWear.server.wherewear.log.domain.Log;
import WhereWear.server.wherewear.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogRecommendService {

    private final LogRepository logRepository;

    public List<Log> getRecommendLogs(String gu, int height, int weight, int footSize, String job) {
        if (height == 0 && weight == 0) {
            return logRepository.findRecommendLogs(gu, 0, 200, 0, 200).orElse(Collections.emptyList());
        } else if (height == 0) {
            return logRepository.findRecommendLogs(gu, 0, 200, weight - 10, weight + 10).orElse(Collections.emptyList());
        } else if (weight == 0) {
            return logRepository.findRecommendLogs(gu, height - 10, height + 10, 0, 200).orElse(Collections.emptyList());
        } else {
            return logRepository.findRecommendLogs(gu, height - 10, height + 10, weight - 10, weight + 10)
                    .orElse(Collections.emptyList());
        }
    }
}
