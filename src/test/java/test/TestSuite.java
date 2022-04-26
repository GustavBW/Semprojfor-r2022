package test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ProductManagerTest.class, ProductAttributeTest.class, ProductJSONReaderTest.class, ProductTest.class})
//@SelectPackages({"test"})
public class TestSuite {

}