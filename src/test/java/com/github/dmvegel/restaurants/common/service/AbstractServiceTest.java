package com.github.dmvegel.restaurants.common.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AbstractServiceTest {
    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        assertThatExceptionOfType(Throwable.class)
                .isThrownBy(runnable::run)
                .satisfiesAnyOf(
                        ex -> assertThat(ex).isInstanceOf(rootExceptionClass),
                        ex -> assertThat(ex).hasRootCauseInstanceOf(rootExceptionClass));
    }
}
