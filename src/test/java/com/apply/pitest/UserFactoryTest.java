package com.apply.pitest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserFactoryTest {

    @Test
    @DisplayName("Should return a User - When Success")
    void create_User_WhenSuccess() {
        final Integer year = 10;
        final String name = "Dudu";

        final User user = UserFactory.create(name, year);

        Assertions.assertNotNull(user);
        Assertions.assertEquals(name, user.getName());
    }
}