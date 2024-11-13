package com.xrosstools.xdecision;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.xrosstools.xdecision.ext.ExpressionCompilerTest;
import com.xrosstools.xdecision.ext.TokenParserTest;
import com.xrosstools.xdecision.sample.*;

@RunWith(Suite.class)
@SuiteClasses({
    ExpressionCompilerTest.class,
    TokenParserTest.class,
    Chose_collection_method_v1_Test.class,
    Chose_collection_method_v2_Test.class,
    DecisionRuleTest.class,
    ExpressionTest.class,
    NumberComparationTest.class,
    ObjectValidationTest.class,
    RangeValidationTest.class,
    SampleTest.class,
    SampleV2Test.class,
    StringValidationTest.class,
    XrossEvaluatorTest.class,
    XrossEvaluatorUnitTest.class,
})
public class AllTests  {

}
