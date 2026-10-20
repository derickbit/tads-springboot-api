package br.edu.ifsul.derick.tads_springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // usa H2 em memoria e o segredo de teste
class TadsSpringbootDerickApplicationTests {

    @Test
    void contextLoads() {
    }

}
