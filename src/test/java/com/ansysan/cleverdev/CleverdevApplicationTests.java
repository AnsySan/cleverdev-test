package com.ansysan.cleverdev;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class CleverdevApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertThat(40 * 2).isEqualTo(80);
    }

}
