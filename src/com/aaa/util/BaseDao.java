package com.aaa.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * 公共增删改查
**/ 
public class BaseDao<T> {
    private static final String URL = "jdbc:mysql://localhost:3306/tjjs?characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "tiger";
    static Connection conn = null;
    static PreparedStatement pstmt = null;
    static ResultSet rs = null;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int executeUpdate(String sql, Object[] params) {
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return 0;
    }

    public List<Map<String, Object>> executeQuery(String sql, Object[] params) {
        List<Map<String, Object>> lists = new ArrayList<>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            int len = rd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < len; i++) {
                    map.put(rd.getColumnName(i + 1), rs.getString(i + 1));
                }
                lists.add(map);
            }
            return lists;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 查询 Staff  --> List<Staff>
     * Staff.get...
     *
     * @param sql
     * @param params
     * @param clazz
     * @return
     */
    public List<T> queryList(String sql, Object[] params, Class<?> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int colCount = md.getColumnCount();
            String fieldName = "";
            Field field = null;
            while (rs.next()) {
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= colCount; i++) {
                    fieldName = md.getColumnName(i);
                    try {
                        field = clazz.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(obj, convert(rs.getString(i), field.getType()));
                    } catch (Exception e) {

                    }
                }
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, pstmt, rs);
        }
        return null;
    }

    private <T extends Serializable> T convert(String param, Class<?> clas) {
        if (param == null || param == "" || clas == null) {
            return null;
        }
        String type = clas.getName();
        if (type.equals("java.lang.String")) {
            return (T) param;
        }
        try {
            if (type.equals("java.util.Date")) {
                return (T) new java.util.Date(Timestamp.valueOf(param).getTime());
            }
            if (type.equals("java.sql.Date")) {
                return (T) new Date(Timestamp.valueOf(param).getTime());
            }
            if (type.equals("java.sql.Timestamp")) {
                return (T) Timestamp.valueOf(param);
            }
            if (type.equals("java.lang.Char")) {
                return (T) Character.valueOf(param.charAt(0));
            }
            if (type.equals("java.lang.Integer") || type.equals("int")) {
                return (T) Integer.valueOf(param);
            }
            if (type.equals("java.lang.Double") || type.equals("double")) {
                return (T) Double.valueOf(param);
            }
            if (type.equals("java.lang.Float") || type.equals("float")) {
                return (T) Float.valueOf(param);
            }
            if (type.equals("java.lang.Byte") || type.equals("byte")) {
                return (T) Byte.valueOf(param);
            }
            if (type.equals("java.lang.Short") || type.equals("short")) {
                return (T) Short.valueOf(param);
            }
            if (type.equals("java.lang.Long") || type.equals("long")) {
                return (T) Long.valueOf(param);
            }
            if (type.equals("java.lang.Boolean") || type.equals("boolean")) {
                return (T) Boolean.valueOf(param);
            }
        } catch (Exception e) {

        }
        return null;
    }
}
