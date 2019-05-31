package com.laptrinhjavaweb.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.laptrinhjavaweb.annotation.Column;
import com.laptrinhjavaweb.annotation.Table;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.mapper.ResultSetMapper;
import com.laptrinhjavaweb.repository.GenericJDBC;

public class AbstractJDBC<T> implements GenericJDBC<T> {
	//nay xoa bi lo ham @@

	private Class<T> zClass;

	@SuppressWarnings("unchecked")
	public AbstractJDBC() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String databaseURL = "jdbc:mysql://localhost:3306/estate-4-2019";
			String user = "root";
			String password = "123456";
			return DriverManager.getConnection(databaseURL, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> query(String sql, Object... parameters) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<T>();
		try (Connection conn = getConnection();
				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();) {
			if (conn != null) {
				for (int i = 0; i < parameters.length; i++) {
					Object parameter = parameters[i];
					int index = i + 1;
					statement.setObject(index, parameters[i]);
				}
				return resultSetMapper.mapRow(resultSet, this.zClass);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/*
	 * @Override public void update(String sql, Object... parameters) { Connection
	 * conn = null; PreparedStatement statement = null; try { conn =
	 * getConnection(); conn.setAutoCommit(false); statement =
	 * conn.prepareStatement(sql); if (conn != null) {
	 * 
	 * // set parameters to statement for (int i = 0; i < parameters.length; i++) {
	 * Object parameter = parameters[i]; int index = i + 1;
	 * statement.setObject(index, parameters[i]); } statement.executeUpdate();
	 * conn.commit(); } } catch (SQLException e) { if (conn != null) { try {
	 * conn.rollback(); } catch (SQLException e1) { e1.printStackTrace(); } } }
	 * finally { try { if (conn != null) {
	 * 
	 * } if (statement != null) { statement.close(); } } catch (SQLException e2) {
	 * e2.printStackTrace(); } } }
	 */

	/*
	 * @Override public Long insert(String sql, Object... parameters) { Connection
	 * conn = null; PreparedStatement statement = null; ResultSet rs = null; try {
	 * conn = getConnection(); conn.setAutoCommit(false); statement =
	 * conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); if (conn !=
	 * null) {
	 * 
	 * // set parameters to statement for (int i = 0; i < parameters.length; i++) {
	 * Object parameter = parameters[i]; int index = i + 1;
	 * statement.setObject(index, parameters[i]); }
	 * 
	 * int rowInserted = statement.executeUpdate(); rs =
	 * statement.getGeneratedKeys(); conn.commit(); if (rowInserted > 0) { while
	 * (rs.next()) { Long id = rs.getLong(1); return id; } } } } catch (SQLException
	 * e) { if (conn != null) { try { conn.rollback(); } catch (SQLException e1) {
	 * e1.printStackTrace(); } } } finally { try { if (conn != null) {
	 * 
	 * } if (statement != null) { statement.close(); } } catch (SQLException e2) {
	 * e2.printStackTrace(); } } return null; }
	 */

	@Override
	public Long insert(Object object) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLInsert();
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (conn != null) {
				Class<?> zClass = object.getClass();
				Field[] fields = zClass.getDeclaredFields();
				// set parameters to statement

				for (int i = 0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					field.setAccessible(true);
					statement.setObject(index, field.get(object));
				}
				Class<?> parentClass = zClass.getSuperclass();
				int intdexParent = fields.length + 1;
				while (parentClass != null) {
					for (int i = 0; i < parentClass.getDeclaredFields().length; i++) {
						Field field = parentClass.getDeclaredFields()[i];
						field.setAccessible(true);
						statement.setObject(intdexParent, field.get(object));
						intdexParent = intdexParent + 1;
					}
					parentClass = parentClass.getSuperclass();
				}

				int rowInserted = statement.executeUpdate();
				rs = statement.getGeneratedKeys();
				conn.commit();
				if (rowInserted > 0) {
					while (rs.next()) {
						Long id = rs.getLong(1);
						return id;
					}
				}
			}
		} catch (SQLException | IllegalAccessException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (conn != null) {

				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	private String createSQLInsert() {
		String tableName = "";
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		StringBuilder fields = new StringBuilder("");
		StringBuilder params = new StringBuilder("");

		for (Field field : zClass.getDeclaredFields()) {
			if (fields.length() > 1) {
				fields.append(",");
				params.append(",");
			}
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				fields.append(column.name());
				params.append("?");

			}

		}

		// check parent class
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			for (Field field : parentClass.getDeclaredFields()) {
				if (fields.length() > 1) {
					fields.append(",");
					params.append(",");
				}
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					fields.append(column.name());
					params.append("?");

				}

			}
			parentClass = parentClass.getSuperclass();
		}

		String sql = "INSERT INTO " + tableName + "(" + fields.toString() + ") VALUES(" + params.toString() + ")";
		return sql;
	}

	@Override
	public void update(Object object) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLUpdate();
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (conn != null) {
				Class<?> zClass = object.getClass();
				Field[] fields = zClass.getDeclaredFields();
				// set parameters to statement

				for (int i = 0; i < fields.length; i++) {
					int index = i + 1;
					Field field = fields[i];
					field.setAccessible(true);
					statement.setObject(index, field.get(object));
				}

				Class<?> parentClass = zClass.getSuperclass();
				int intdexParent = fields.length + 1;
				Object id = null;
				while (parentClass != null) {
					for (int i = 0; i < parentClass.getDeclaredFields().length; i++) {
						Field field = parentClass.getDeclaredFields()[i];
						field.setAccessible(true);
						String name = field.getName();
						if (!name.equals("id")) {
							statement.setObject(intdexParent, field.get(object));
							intdexParent = intdexParent + 1;
						} else {
							id = field.get(object);
						}

					}
					parentClass = parentClass.getSuperclass();
				}
				statement.setObject(intdexParent, id);
				statement.executeUpdate();
				conn.commit();

			}
		} catch (SQLException | IllegalAccessException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (conn != null) {

				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}

	}

	private String createSQLUpdate() {
		String tableName = "";
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		String where = null;
		StringBuilder sets = new StringBuilder("");

		for (Field field : zClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				String columnName = column.name();
				String value = columnName + " = ?";
				if (!columnName.equals("id")) {
					if (sets.length() > 1) {
						sets.append(", ");
					}
					sets.append(value);
				}
			}

		}

		// check parent class
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			for (Field field : parentClass.getDeclaredFields()) {

				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					String columnName = column.name();
					String value = columnName + " = ?";
					if (!columnName.equals("id")) {
						if (sets.length() > 1) {
							sets.append(", ");
						}
						sets.append(value);
					} else {
						where = " WHERE " + value;
					}

				}

			}
			parentClass = parentClass.getSuperclass();
		}

		String sql = "UPDATE " + tableName + " SET " + sets.toString() + where;
		return sql;
	}

	@Override
	public void delete(Object object) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			String sql = createSQLDelete();
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (conn != null) {
				Class<?> zClass = object.getClass();
				Field[] fields = zClass.getDeclaredFields();
				// set parameters to statement

				Class<?> parentClass = zClass.getSuperclass();
				int intdexParent = 1;
				Object id = null;
				while (parentClass != null) {
					for (int i = 0; i < parentClass.getDeclaredFields().length; i++) {
						Field field = parentClass.getDeclaredFields()[i];
						field.setAccessible(true);
						String name = field.getName();
						if (name.equals("id")) {
							id = field.get(object);
						}

					}
					parentClass = parentClass.getSuperclass();
				}
				statement.setObject(intdexParent, id);
				statement.executeUpdate();
				conn.commit();

			}
		} catch (SQLException | IllegalAccessException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (conn != null) {

				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}

	}

	private String createSQLDelete() {
		String tableName = "";
		if (zClass.isAnnotationPresent(Table.class)) {
			Table table = zClass.getAnnotation(Table.class);
			tableName = table.name();
		}
		String where = null;
		StringBuilder sets = new StringBuilder("");

		for (Field field : zClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Column.class)) {
				Column column = field.getAnnotation(Column.class);
				String columnName = column.name();
				String value = columnName + " = ?";
				if (!columnName.equals("id")) {
					if (sets.length() > 1) {
						sets.append(", ");
					}
					sets.append(value);
				}
			}

		}

		// check parent class
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null) {
			for (Field field : parentClass.getDeclaredFields()) {

				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					String columnName = column.name();
					String value = columnName + " = ?";
					if (!columnName.equals("id")) {
						if (sets.length() > 1) {
							sets.append(", ");
						}
						sets.append(value);
					} else {
						where = " WHERE " + value;
					}

				}

			}
			parentClass = parentClass.getSuperclass();
		}

		String sql = "DELETE FROM " + tableName + where;
		return sql;
	}

	private String createSQLFindById(Long id) {
		StringBuilder sql = new StringBuilder("SELECT * FROM "+zClass.getAnnotation(Table.class).name());;

		sql.append(" WHERE id = "+id);
		return sql.toString();

	}

	@Override
	public T findById(long id) {
		String sql = createSQLFindById(id);
		try (Connection conn = getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				){
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					Field[] fields = zClass.getDeclaredFields();

					while (resultSet.next()) {
						T object = (T) zClass.newInstance();
						// get value 1 row of resultset and set to entity
						for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
							String columnName = resultSetMetaData.getColumnName(i + 1);
							Object columnValue = resultSet.getObject(i + 1);
							for (Field field : fields) {
								if (field.isAnnotationPresent(Column.class)) {
									Column column = field.getAnnotation(Column.class);
									if (column.name().equals(columnName) && columnValue != null) {
										BeanUtils.setProperty(object, field.getName(), columnValue);
										break;
									}
								}
							}

							Class<?> parentClass = zClass.getSuperclass();
							while (parentClass != null) {
								Field[] fieldParents = parentClass.getDeclaredFields();
								for (Field field : fieldParents) {
									if (field.isAnnotationPresent(Column.class)) {
										Column column = field.getAnnotation(Column.class);
										if (column.name().equals(columnName) && columnValue != null) {
											BeanUtils.setProperty(object, field.getName(), columnValue);
											break;
										}
									}
								}
								parentClass = parentClass.getSuperclass();
							}
						}
						return object;
					}
				
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
