package tech.getarrays.api.user.impl;

import dao.DaoUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tech.getarrays.api.user.repo.UserRepo;
import tech.getarrays.api.user.service.UserDao;
import tech.getarrays.api.user.bean.User;

import javax.sql.DataSource;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {

    private final Log logger = LogFactory.getLog(this.getClass());

    private final UserRepo userRepo;

    private NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public UserDaoImpl(UserRepo userRepo) {this.userRepo = userRepo;}

    @Autowired
    public void setDataSource(DataSource dataSource)
    {
        namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User getUser(String email, String password) {
        String sql =
                "   SELECT id                               " +
                "        , email                            " +
                "        , username                         " +
                "        , user_code                        " +
                "     FROM user                             " +
                "    WHERE email    = :email                " +
                "      AND password = :password             ";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", password);

        DaoUtils.debugQuery(logger, sql, sqlParameterSource);

        try {
            return namedTemplate.queryForObject(sql, sqlParameterSource, (rs, i) -> {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setUserCode(rs.getString("user_code"));

                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean checkEmailForExisting(String email) {
        String sql =
                "   SELECT 1                                " +
                "     FROM user                             " +
                "    WHERE email    = :email                ";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("email", email);

        DaoUtils.debugQuery(logger, sql, sqlParameterSource);

        try {
            return Boolean.TRUE.equals(namedTemplate.queryForObject(sql, sqlParameterSource, Boolean.class));
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public User addUser(User user) {
        user.setUserCode(UUID.randomUUID().toString());
        return userRepo.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        String sql =
                "   SELECT id                               " +
                "        , email                            " +
                "        , username                         " +
                "        , user_code                        " +
                "     FROM user                             " +
                "    WHERE email    = :email                ";

        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("email", email);

        DaoUtils.debugQuery(logger, sql, sqlParameterSource);

        try {
            return namedTemplate.queryForObject(sql, sqlParameterSource, (rs, i) -> {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setUserCode(rs.getString("user_code"));

                return user;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
