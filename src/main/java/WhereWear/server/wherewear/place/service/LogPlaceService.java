package WhereWear.server.wherewear.place.service;

import WhereWear.server.wherewear.log.dto.LogResponse;
import WhereWear.server.wherewear.log.service.LogService;
import WhereWear.server.wherewear.place.dto.LogCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LogPlaceService {
    private final LogService logService;

    public List<LogCountDto> countLogsByXY(double minX, double maxX, double minY, double maxY) {
        return logService.countLogsByXY(minX, maxX, minY, maxY).stream()
                .map(arr -> new LogCountDto((Double) arr[0], (Double) arr[1], (Long) arr[2]))
                .collect(Collectors.toList());
    }

    public List<LogResponse> findLogsByXY(double x, double y) {
        return logService.findByXY(x, y).stream()
                .map(log -> new LogResponse(log))
                .collect(Collectors.toList());
    }

    public List<LogResponse> nearPlaceLogsByXY(double x, double y) {
        return logService.nearPlaceLogsByXY(x, y).stream()
                .map(log -> new LogResponse(log))
                .collect(Collectors.toList());
    }
}
