package WhereWear.server.wherewear.log.recommend;

import WhereWear.server.wherewear.log.Log;
import WhereWear.server.wherewear.log.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogRecommendService {

    private final LogRepository logRepository;

    public List<Log> getRecommendLogs(String gu, int height, int weight, int footSize, String job) {
        if(height == 0){
            return logRepository.findRecommendLogs(gu, 0, 200, weight - 10, weight + 10)
                    .orElse(Collections.emptyList());
        }

        if(weight == 0){
            return logRepository.findRecommendLogs(gu, height - 10, height + 10,0, 200)
                    .orElse(Collections.emptyList());
        }

        if(height == 0 && weight == 0){
            return logRepository.findRecommendLogs(gu, 0, 200,0, 200)
                    .orElse(Collections.emptyList());
        }

        return logRepository.findRecommendLogs(gu, height - 10, height + 10, weight - 10, weight + 10)
                .orElse(Collections.emptyList());
    }
}
