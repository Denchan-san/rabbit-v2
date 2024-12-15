package org.example.util;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import static org.example.util.Strings.tableize;
import static org.example.util.Strings.underscore;

public class PostgreSqlPhysicalNamingStrategy implements PhysicalNamingStrategy {
    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier == null ? null : Identifier.toIdentifier(tableize(identifier.getText()));
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier == null ? null : Identifier.toIdentifier(tableize(identifier.getText()));
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier == null ? null : Identifier.toIdentifier(tableize(identifier.getText()));
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier == null ? null : Identifier.toIdentifier(underscore(identifier.getText()));
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return identifier == null ? null : Identifier.toIdentifier(underscore(identifier.getText()));
    }
}



