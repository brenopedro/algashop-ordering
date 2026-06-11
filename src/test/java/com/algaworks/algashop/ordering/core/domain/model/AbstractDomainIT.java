package com.algaworks.algashop.ordering.core.domain.model;

import com.algaworks.algashop.ordering.utils.TestContainerPostgreSQLConfig;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerPostgreSQLConfig.class)
public abstract class AbstractDomainIT {
}
