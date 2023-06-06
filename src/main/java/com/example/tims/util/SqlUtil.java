package com.example.tims.util;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.example.tims.anno.TableName;
import com.example.tims.anno.MySqlField;

public class SqlUtil {
    static ResourceBundle bundle = ResourceBundle.getBundle("sql");

    //    注册驱动
    static {
        try {
            Class.forName(bundle.getString("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //    得到数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(bundle.getString("url"), bundle.getString("username"), bundle.getString("password"));
    }


    //    关闭资源
    public static void close(Connection conn, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    //    关闭打开的资源
    public static void close(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            close(conn, statement);
        }
    }

    /**
     * 检查数据库表是否存在
     */
    public static void isTableExists(Class<?> clazz) {
        // 获取类对象上的 TableName 注解
        TableName tableNameAnnotation = clazz.getAnnotation(TableName.class);
        if (tableNameAnnotation == null) {
            throw new IllegalArgumentException("TableName annotation not found on class " + clazz.getName());
        }

        // 获取注解中的表名
        String tableName = tableNameAnnotation.tableName();

        // 假设你已经建立了数据库连接
        ResultSet resultSet = null;
        try (Connection connection = SqlUtil.getConnection()) {
            // 获取数据库的元数据
            DatabaseMetaData metaData = connection.getMetaData();

            // 根据表名检查数据库中是否存在对应的表
            resultSet = metaData.getTables(null, null, tableName, null);
            if (!resultSet.next()){
                SqlUtil.createTable(SqlUtil.generateTableStatement(clazz));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public static void createTable(String sql) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = SqlUtil.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException se){
            se.printStackTrace();
        }finally {
            SqlUtil.close(conn,stmt);
        }
    }

    // 传入实体类 Class 对象，返回 MySQL 建表语句
    public static String generateTableStatement(Class<?> clazz) {

        String tableName = clazz.getAnnotation(TableName.class).tableName();

        List<String> columnStatements = new ArrayList<>();
        List<String> constraintStatements = new ArrayList<>();

        // 遍历实体类的每个字段，生成对应的 MySQL 列语句和约束语句
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(MySqlField.class)) {
                continue;
            }
            MySqlField mySqlField = field.getAnnotation(MySqlField.class);
            String columnName = mySqlField.name().isEmpty() ? field.getName().toLowerCase() : mySqlField.name();
            String columnType = mySqlField.type();
            String columnNullable = mySqlField.notNull() ? "NOT NULL" : "";
            String autoIncrement = mySqlField.autoIncrement() ? "AUTO_INCREMENT" : "";
            String columnLength = columnType.toLowerCase().contains("varchar") ? "(" + mySqlField.length() + ")" : "";
            String comment = "COMMENT " + "'"+mySqlField.comment()+"'";
            columnStatements.add(columnName + " " + columnType + columnLength + " " + columnNullable + " " + autoIncrement + " " + comment);

            if (mySqlField.primaryKey()) {
                constraintStatements.add("PRIMARY KEY (" + columnName + ")");
            }
        }

        // 构造完整的 MySQL 建表语句
        String columnStatementsAsString = String.join(", ", columnStatements);
        String constraintStatementsAsString = constraintStatements.size() > 0 ? ", " + String.join(", ", constraintStatements) : "";

        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnStatementsAsString + constraintStatementsAsString + ")";
    }

}
