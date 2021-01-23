package com.sabri.practice;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * class for practice on @TestInstance annotation and use it with @BeforeAll, @AfterAll and @TestMethodOrder.
 * you can check the full example and explanation from this link https://www.baeldung.com/junit-testinstance-annotation
 */

//@TestInstance annotation enables us to ask JUnit to create only one instance of the test class and reuse it between tests.
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdditionTest {

    private int sum;

    @BeforeAll
    public void setUpFixture() {
        sum = 1;
    }

    @AfterEach
    void setUp() {
        sum = 1;
    }

    @Test
//    @Order(1)
    void addingTwoReturnsThree() {
        sum += 2;
        assertEquals(3, sum);
    }

    @Test
//    @Order(2)
    void addingThreeReturnsFour() {
        sum += 3;
        assertEquals(4, sum);
    }
}
