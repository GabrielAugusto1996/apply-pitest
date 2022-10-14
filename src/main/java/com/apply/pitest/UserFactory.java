package com.apply.pitest;

public final class UserFactory {

    private UserFactory() {}

    public static User create(final String name, final Integer year) {
        final User user = new User();

        user.setName(name);
        user.setYear(year);

        return user;
    }
}
