package io.github.iamazy.elasticsearch.dsl.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author iamazy
 * @date 2019/12/21
 * @descrition
 **/
public class ElasticStatement extends AbstractStatement{

    protected ElasticConnection connection;
    protected ResultSet resultSet;

    public ElasticStatement(ElasticConnection connection){
        this.connection=connection;
    }

    @Override
    protected ResultSet executeQuery(String sql, Object[] args) throws SQLException {
        return executeQuery(sql);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        //TODO
        return null;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        executeQuery(sql);
        return true;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return resultSet;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }
}
