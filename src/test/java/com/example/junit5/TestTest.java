package com.example.junit5;

import com.example.junit5.domain.Study;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TestTest {


    @DisplayName("테스트하기")
    @EnabledOnJre({JRE.JAVA_11})
    @FastTest //local case
    void test() {
        System.out.println("test");
    }


    @EnabledIfEnvironmentVariable(named="HOME", matches = "/Users/parkey19")
    @SlowTest //release case
    void test2() {
        System.out.println("test run");
    }

    @DisplayName("반복테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {cirrentRepetition}/{totalRepetation}")
    void repeateTest(RepetitionInfo repetitionInfo) {
        System.out.println("test :" + repetitionInfo.getCurrentRepetition());
    }

    @DisplayName("파람테스트")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "추워용"})
    @NullAndEmptySource
    void parameterTest(String message) {
        System.out.println("me: " + message);
    }

    @DisplayName("cvs")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 30})
    void cvsTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println("me: " + study.getLimitCount());
    }

    //하나의 argument
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("cvs")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, 자바 스터디", "20, 스프링"})
    void cvsTest2(ArgumentsAccessor argumentsAccessor) {
        System.out.println("me: " + new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1)));
    }

    @DisplayName("cvs")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, 자바 스터디", "20, 스프링"})
    void cvsTest3(@AggregateWith(StudyAggri.class) Study study) {
        System.out.println("me: " + study);
    }

    //제약 조건: 이너 static class
    static class StudyAggri implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }

    /**
     * 매 테스트 마다 인스턴스 클래스 만들어 테스트 하기 떄문에
     * before, after 호출 하기 위해서는 static
     * 하지만 @TestInstance Lifecycle.PER_CLASS 설정이라면 static 아니여도 됨
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after All");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach() {
        System.out.println("AfterEach");
    }

}