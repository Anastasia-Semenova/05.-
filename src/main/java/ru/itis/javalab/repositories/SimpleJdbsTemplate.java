package ru.itis.javalab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleJdbsTemplate {


    Connection connection;

    public SimpleJdbsTemplate(Connection connection) {
        this.connection = connection;
    }

    public SimpleJdbsTemplate() {
    }

    public <T> List<T> query(String SQL, RowMapper<T> rowMapper, Object... args) {


        ResultSet resultSet = null;
        PreparedStatement statement1 = null;
        List<T> result = new ArrayList<>();


        try {
            statement1 = connection.prepareStatement(SQL);

            if (args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    System.out.println(args.length);
                    statement1.setObject(i+1, args[i]);
                }
            }
            resultSet = statement1.executeQuery();
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }


            return result;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    //ignore
//                }
//            }
        }
    }
}
