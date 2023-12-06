package com.Pubrunda.User;

import com.Pubrunda.PubrundaApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PubrundaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(

)
public class UserServiceIntegrationTest {
}
