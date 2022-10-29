package com.xrosstools.xdecision;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.xrosstools.xdecision.ext.ExpressionCompilerTest;
import com.xrosstools.xdecision.ext.TokenParserTest;
import com.xrosstools.xdecision.sample.Chose_collection_method_v1_Test;
import com.xrosstools.xdecision.sample.Chose_collection_method_v2_Test;
import com.xrosstools.xdecision.sample.ExpressionTest;
import com.xrosstools.xdecision.sample.SampleTest;
import com.xrosstools.xdecision.sample.SampleV2Test;
import com.xrosstools.xdecision.sample.XrossEvaluatorTest;
import com.xrosstools.xdecision.sample.XrossEvaluatorUnitTest;

@RunWith(Suite.class)
@SuiteClasses({
        ExpressionCompilerTest.class,
        TokenParserTest.class,
        Chose_collection_method_v1_Test.class,
        Chose_collection_method_v2_Test.class,
        ExpressionTest.class,
        SampleTest.class,
        SampleV2Test.class,
        XrossEvaluatorTest.class,
        XrossEvaluatorUnitTest.class,
})
public class AllTests  {

}
