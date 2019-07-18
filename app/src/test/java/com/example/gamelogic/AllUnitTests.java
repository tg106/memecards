package com.example.gamelogic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        DatabaseStubTest.class,
        GameLogicTest.class,
        UpvotesCalculationTest.class
})

public class AllUnitTests {
} 