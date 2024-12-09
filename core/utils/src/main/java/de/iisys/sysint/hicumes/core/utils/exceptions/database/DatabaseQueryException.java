package de.iisys.sysint.hicumes.core.utils.exceptions.database;

import org.hibernate.query.Query;

import java.util.stream.Collectors;

public class DatabaseQueryException extends DatabaseBaseException{

    javax.persistence.Query query;
    static String errorMessage = "Failed to execute a Query";
    Exception oldException;
    Object dependency;

    public DatabaseQueryException(javax.persistence.Query query, Exception raisedException, Object dependency ) {
        super(errorMessage, raisedException);
        this.query = query;
        this.oldException = raisedException;
        this.dependency = dependency;
    }

    @Override
    public String toString()
    {
        Query query = this.query.unwrap(Query.class);
        String queryString = query.getQueryString();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            var prams = query.getParameters().stream().map(parameter -> parameter.getName() + " -> " + query.getParameterValue(parameter)).collect(Collectors.toList());
            stringBuilder.append(errorMessage).append("\n+ Query: ").append(queryString);
            stringBuilder.append("\n+ with Params: ").append(prams);
        }
        catch (Exception e) {
            stringBuilder.append(errorMessage).append("\n+ Query: ").append(queryString);
        }

        stringBuilder.append("\n+ with raised Exception: ").append(oldException.getMessage());
        stringBuilder.append("\n+ with related dependency: ").append(dependency);
        String result = stringBuilder.toString();
        return result;
    }
}
