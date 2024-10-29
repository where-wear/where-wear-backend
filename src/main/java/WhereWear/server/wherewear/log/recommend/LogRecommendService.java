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

    public List<Log> getRecommendLogs(String gu,int height,int weight,int footSize,String job){
        return logRepository.findRecommendLogs(gu,height,weight,footSize,job)
                .orElse(Collections.emptyList());
    }
}
