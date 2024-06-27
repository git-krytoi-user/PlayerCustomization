package dev.r1nex.playercustomization.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.r1nex.playercustomization.data.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MySQL {

    private final HikariDataSource source;

    public MySQL(String host, int port, String database, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("maximumPoolSize", 30);
        this.source = new HikariDataSource(config);
    }

    private Connection connection() throws SQLException {
        return source.getConnection();
    }

    public List<Tag> getAllTags() {
        CompletableFuture<List<Tag>> future = CompletableFuture.supplyAsync(() -> {
            List<Tag> tags = new ArrayList<>();
            try (Connection connection = connection()) {
                String query = "SELECT * FROM tags";
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            UUID uuid = UUID.fromString(rs.getString("uuid"));
                            String tagName = rs.getString("tag_name");

                            Tag tag = new Tag(uuid, tagName);
                            tags.add(tag);
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return tags;
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertPlayerTag(UUID whereUuid, String tagName) {
        try (Connection connection = connection()) {
            String query = "INSERT INTO tags (uuid, tag_name) VALUES (?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, whereUuid.toString());
                ps.setString(2, tagName);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
