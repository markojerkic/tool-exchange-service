package com.example.toolexchangeservice.repository;

import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomPsqlDialect extends PostgreSQL82Dialect {
    public CustomPsqlDialect() {
        super();
        registerFunction("calculate_distance",
                new StandardSQLFunction("calculate_distance", StandardBasicTypes.DOUBLE));
    }
}
