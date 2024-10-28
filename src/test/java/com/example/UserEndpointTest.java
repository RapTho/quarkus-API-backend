package com.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserEndpointTest {

        @Test
        public void testUserRepository() {
        performTest("/users");
        }

        private void performTest(String path) {
        // List all, should have all 3 fruits the database has initially:
        given()
                .when().get(path)
                .then()
                .statusCode(200)
                .body(
                        containsString("Bob"),
                        containsString("Alice"),
                        containsString("Steven"));

        // Delete the Cherry:
        given()
                .when().delete(path + "/1")
                .then()
                .statusCode(204);

        // List all, cherry should be missing now:
        given()
                .when().get(path)
                .then()
                .statusCode(200)
                .body(
                        not(containsString("Bob")),
                        containsString("Alice"),
                        containsString("Steven"));
        }

}
