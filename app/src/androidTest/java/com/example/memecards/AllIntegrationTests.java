package com.example.memecards;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DBLoaderTest.class,
        DatabaseTest.class
})
public class AllIntegrationTests {}