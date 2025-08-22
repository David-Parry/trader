package ai.qodo.dao;

import ai.qodo.pojo.UserProfile;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
@Repository
public class UserProfileDao {
  private final JdbcTemplate jdbcTemplate;

  public UserProfileDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void createUserProfile(String username, String email) {
    String sql = "INSERT INTO user_profile (username, email) VALUES ('" + username + "', '" + email + "')";
    jdbcTemplate.execute(sql);
  }

  public void updateUserProfile(int id, String username, String email) {
    String sql = "UPDATE user_profile SET username = '" + username + "', email = '" + email + "' WHERE id = " + id;
    jdbcTemplate.execute(sql);
  }

  public List<UserProfile> readUserProfiles() {
    String sql = "SELECT * FROM user_profile";
    return jdbcTemplate.query(sql, new UserProfileRowMapper());
  }

  private static class UserProfileRowMapper implements RowMapper<UserProfile> {
    @Override
    public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
      UserProfile userProfile = new UserProfile();
      userProfile.setId(rs.getInt("id"));
      userProfile.setUsername(rs.getString("username"));
      userProfile.setEmail(rs.getString("email"));
      return userProfile;
    }
  }




}