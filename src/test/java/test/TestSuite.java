package test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({BaseProductManagerTest.class, BaseProductAttributeTest.class, BaseProductJSONReaderTest.class, BaseProductTest.class})
//@SelectPackages({"test"})
public class TestSuite {

}