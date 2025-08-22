package ai.qodo;

import ai.qodo.pojo.UserProfile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserProfileTest {
    private static final int ID = 1;
    private static final String USERNAME = "testuser";
    private static final String EMAIL = "j@davidparry.com";

    @Test
    public void testCreateUserProfile() {
        // Create a user profile and check if it's not null
        UserProfile userProfile = new UserProfile();
        assertNotNull(userProfile);

        // Set properties and check if they are set correctly
        userProfile.setId(ID);
        userProfile.setUsername(USERNAME);
        userProfile.setEmail(EMAIL);

        // Verify all properties
        assertUserProfile(userProfile);
    }

    @Test
    public void testConstructor() {
        // Create a user profile using the constructor and check if it's not null
        UserProfile userProfile = new UserProfile(ID, USERNAME, EMAIL);
        assertNotNull(userProfile);

        // Verify all properties
        assertUserProfile(userProfile);
    }

    @Test
    public void testUpdateUserProfile() {
        // Create a user profile and verify it
        UserProfile userProfile = new UserProfile(ID, USERNAME, EMAIL);
        assertUserProfile(userProfile);

        // Update properties
        int newId = 2;
        String newUsername = "newuser";
        String newEmail = "newuser@davidparry.com";

        userProfile.setId(newId);
        userProfile.setUsername(newUsername);
        userProfile.setEmail(newEmail);

        // Verify updated properties
        assertEquals(newId, userProfile.getId());
        assertEquals(newUsername, userProfile.getUsername());
        assertEquals(newEmail, userProfile.getEmail());
    }

    private void assertUserProfile(UserProfile userProfile) {
        assertEquals(ID, userProfile.getId());
        assertEquals(USERNAME, userProfile.getUsername());
        assertEquals(EMAIL, userProfile.getEmail());
    }
}
