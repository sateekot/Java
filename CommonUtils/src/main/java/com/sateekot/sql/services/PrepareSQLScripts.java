package com.sateekot.sql.services;

/**
 * @author sateekot
 * date 12-12-2018

 */
public interface PrepareSQLScripts {

    String prepareInsertScripts(String fileName);
    String prepareUpdateScripts(String fileName, String tableName, int updateFieldCount, int conditionFieldCount);
}
