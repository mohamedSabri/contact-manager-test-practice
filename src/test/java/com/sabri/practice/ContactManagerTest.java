package com.sabri.practice;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

    private ContactManager contactManager;

    @BeforeAll
    public void setUpAll() {
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    void setUp() {
        contactManager = new ContactManager();
    }

    @Test
    //@Disabled //test metod will be ignored and disabled (junit will not run this test method)
    void addContact() {
        contactManager.addContact("mohamed", "sabri", "0100002525");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        assertTrue(contactManager.getAllContacts().stream().
                anyMatch(contact -> contact.getFirstName().equals("mohamed") &&
                        contact.getLastName().equals("sabri") &&
                        contact.getPhoneNumber().equals("0100002525")));
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        assertThrows(RuntimeException.class, () -> contactManager.addContact(null, "sabri", "0100002525"));
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        assertThrows(RuntimeException.class, () -> contactManager.addContact("mohamed", null, "0100002525"));
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        assertThrows(RuntimeException.class, () -> contactManager.addContact("mohamed", "sabri", null));
    }

    @Test
    @DisplayName("should Create Contact Only On MAc")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on MAC OS")
    void shouldCreateContactOnlyOnMAc() {
        System.out.println("Run test only on Mac OS");
    }

    @Test
    @DisplayName("should Not Create Contact On Windows OS")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled on Windows OS")
    void shouldNotCreateContactOnWindows() {
        System.out.println("Run test on any system except Windows OS");
    }

    /**
     * If the Assumptions is not successful then the test won't fail,
     * but it will be aborted and won't be executed further.
     * If you pass a System property like you add in the running
     * configurations the test will continue otherwise it will be aborted.
     * <p>
     * we are adding the system property -DENV=DEV in configurations So,
     * <p>
     * Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV"))); // will continue and run the test method
     * Assumptions.assumeTrue("Test".equals(System.getProperty("ENV")));// will abort the test method running
     */
    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    void shouldCreateContactOnDEV() {
        Assumptions.assumeTrue("Test".equals(System.getProperty("ENV")));
        System.out.println("Run test on DEV Machine");
    }

    @Nested
    class RepeatedNestedTest {

        @DisplayName("Repeat Contact Creation Test 5 Times")
        @RepeatedTest(value = 5, name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
        void shouldTestContactCreationRepeatedly() {
            contactManager.addContact("mohamed", "sabri", "0100002525");
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());

        }
    }


    /**
     * parameterized test using @ValueSource, @MethodSource, @CsvSource and @CsvFileSource
     */

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ParameterizedNestedTest {


        @DisplayName("Repeat Contact Creation Test with different inputs every time.")
        @ParameterizedTest
        @ValueSource(strings = {"0100002525", "0100002525", "0100002525"})
        void shouldTestContactCreationRepeatedlyUsingValueSource(String phoneNumber) {
            contactManager.addContact("mohamed", "sabri", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());

        }

        @DisplayName("CSV Source Case - Phone Number should match the required format")
        @ParameterizedTest
        @CsvSource({"0100002525", "0100002525", "0100002525"})
        void shouldTestContactCreationRepeatedlyUsingCSVSource(String phoneNumber) {
            contactManager.addContact("mohamed", "sabri", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());

        }

        @DisplayName("CSV File Source Case - Phone Number should match the required format")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        void shouldTestContactCreationRepeatedlyUsingCSVFileSource(String phoneNumber) {
            contactManager.addContact("mohamed", "sabri", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());

        }


    }

    @DisplayName("Method Source Case - Phone Number should match the required format")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber) {
        contactManager.addContact("mohamed", "sabri", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());

    }

    private static List<String> phoneNumberList() {
        return Arrays.asList("0100002525", "0100002525", "0100002525");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Should Execute After Each Test");
    }

    @AfterAll
    void tearDownAll() {
        System.out.println("Should be Executed at the end of the Test");
    }
}