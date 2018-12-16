package com.sateekot.sql.serviceimpl;

import com.sateekot.sql.services.PrepareSQLScripts;
import org.junit.Before;
import org.junit.Test;


public class PrepareSQLScriptsImplTest {

    private PrepareSQLScripts prepareSQLScripts;

    @Before
    public void setUp() {
        prepareSQLScripts = new PrepareSQLScriptsImpl();
    }


    @Test
    public void prepareUpdateScriptsTest() {
        String filePath = "src/test/resources/SampleData.csv";
        int updateFiledCount = 5;
        int conditionFieldCount = 1;
        prepareSQLScripts.prepareUpdateScripts(filePath,"CPGOfferIngestionState",updateFiledCount,conditionFieldCount);
    }
}
