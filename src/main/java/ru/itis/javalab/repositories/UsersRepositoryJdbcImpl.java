package ru.itis.javalab.repositories;


import ru.itis.javalab.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    //language = SQL
    private static final String SQL_FIND_ALL = "select * from users";
    //language = SQL
    private static final String SQL_FIND_ALL_BY_AGE = "select * from users where age = ?";
    private DataSource dataSource;
    //language = SQL
    private static final String SQL_FIND_ALL_BY_UUID = "select * from users where uuid = ?";
    //language = SQL
    private static final String SQL_SAVE_USER = "insert into " +
            "users (login, password, uuid, first_name, last_name, age) values (?,?,?,?,?,?)";
    //language = SQL
    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "select * from users where login = ? and password = ?";

    SimpleJdbsTemplate template;
    Connection connection;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        try {
            connection = dataSource.getConnection();
            template = new SimpleJdbsTemplate(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .login(row.getString("login"))
            .password(row.getString("password"))
            .uuid(row.getString("uuid"))
            .build();


    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public Optional findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = dataSource.getConnection();
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(SQL_FIND_ALL);
//
//            List<User> result = new ArrayList<>();
//            while (resultSet.next()) {
//
//                result.add(userRowMapper.mapRow(resultSet));
//            }
//            return result;
//
//        } catch (SQLException e) {
//            throw new IllegalArgumentException(e);
//        } finally {
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//            }
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//

//        }
        return template.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public List<User> findAllByAge(int age) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = dataSource.getConnection();
//            statement = connection.prepareStatement(SQL_FIND_ALL_BY_AGE);
//            statement.setInt(1, age);
//            resultSet = statement.executeQuery();
//
//            List<User> result = new ArrayList<>();
//            while (resultSet.next()) {
//
//                result.add(userRowMapper.mapRow(resultSet));
//            }
//            return result;
//
//        } catch (SQLException e) {
//            throw new IllegalArgumentException(e);
//        } finally {
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//            }
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//            }
//        }
        return template.query(SQL_FIND_ALL_BY_AGE, userRowMapper, age);
    }

    @Override
    public List<User> findAllByUUID(String uuid) {
        return template.query(SQL_FIND_ALL_BY_UUID, userRowMapper, uuid);
    }

    @Override
    public void saveUser(User user) {
        System.out.println("lol");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(SQL_SAVE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setObject(3, user.getUuid());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setInt(6, user.getAge());
            statement.executeUpdate();


        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
//        } finally {
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//            }
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//            }
        }
    }

    @Override
    public List<User> getUserByLoginPassword(String login, String password) {
        return template.query(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD, userRowMapper, login, password);
    }
}
