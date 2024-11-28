package WhereWear.server.wherewear.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {
    public static <T> List<T> reverseList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<T> reversedList = new ArrayList<>(list); // 원본 보호를 위해 복사
        Collections.reverse(reversedList); // 역순 정렬
        return reversedList;
    }
}
