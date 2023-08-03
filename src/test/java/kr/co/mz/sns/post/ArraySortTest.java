package kr.co.mz.sns.post;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ArraySortTest {

    @Test
    void test() {
        int[][] arr = {
            {1, 123},
            {1, 12},
            {5, 65},
            {12, 4236},
            {436, 1235636},
            {12, 456}
        };
        Arrays.sort(arr, ((o1, o2) -> {
            return o1[1] - o2[1];
        }));
        log.info("----------------------test------------------------------");
        for (int[] num : arr) {
            log.info(String.valueOf(num[1]));
        }
        log.info("----------------------foreach---------------------------");
        Arrays.stream(arr).forEach((num) -> {
            System.out.println(num[1]);
        });
        log.info("-----------------------end------------------------------");
    }

}
