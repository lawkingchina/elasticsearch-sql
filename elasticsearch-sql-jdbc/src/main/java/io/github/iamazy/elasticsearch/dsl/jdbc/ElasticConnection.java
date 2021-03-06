package io.github.iamazy.elasticsearch.dsl.jdbc;

import io.github.iamazy.elasticsearch.dsl.cons.CoreConstants;
import io.github.iamazy.elasticsearch.dsl.jdbc.statement.ElasticPreparedStatement;
import io.github.iamazy.elasticsearch.dsl.jdbc.statement.ElasticStatement;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author iamazy
 * @date 2019/12/21
 **/
public class ElasticConnection extends AbstractConnection {

    private RestHighLevelClient client;

    private List<String> databases;

    ElasticConnection(String url, Properties properties, RestHighLevelClient client) {
        super(url, properties);
        this.client = client;
        String[] items = url.split("/");
        String database = items[items.length - 1];
        if (database.contains(CoreConstants.COND)) {
            database = database.split("[?]")[0];
        }
        this.databases = Arrays.asList(database.split("[,]"));
    }

    public RestHighLevelClient getRestClient() {
        return client;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new ElasticStatement(this);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new ElasticPreparedStatement(this, sql);
    }

    /**
     * @param sql
     * @param resultSetType
     * @param resultSetConcurrency
     * @return
     * @throws SQLException
     */
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return new ElasticPreparedStatement(this, sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return new ElasticDatabaseMetaData(this.url, this.properties.getOrDefault("user", "").toString());
    }

    public List<String> getDatabaseNames() {
        return databases;
    }

    @Override
    public void close() throws SQLException {
        try {
            client.close();
            closed = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
