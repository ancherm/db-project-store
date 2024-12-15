package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.chermashentsev.dbproductstore.model.User;

import java.sql.Types;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public User authenticateUser(String username) {
        String sql = "CALL authenticate_user(?, ?, ?)";
        return jdbcTemplate.execute(sql, (CallableStatementCallback<User>) callableStatement -> {
            callableStatement.setString(1, username);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            callableStatement.registerOutParameter(3, Types.VARCHAR);

            callableStatement.execute();

            String passwordHash = callableStatement.getString(2);
            String role = callableStatement.getString(3);
//            boolean enabled = callableStatement.getBoolean(4);

            if (passwordHash == null) {
                return null; // Пользователь не найден
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordHash);
            user.setRole(role);
            return user;
        });
    }
}
