package ru.javaops.restaurants.common.service;

import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AbstractServiceTest {
    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        assertThatExceptionOfType(Throwable.class)
                .isThrownBy(runnable::run)
                .satisfiesAnyOf(
                        ex -> assertThat(ex).isInstanceOf(rootExceptionClass),
                        ex -> assertThat(ex).hasRootCauseInstanceOf(rootExceptionClass));
    }
}
