package test;

public class Main {

    public static void main(String[] args) {
        JSONReaderTest readerTester = new JSONReaderTest();

        readerTester.runAllTests();

        ProductAttributeTest productAttributeTest = new ProductAttributeTest();
        productAttributeTest.values();
        productAttributeTest.valueOf();
    }
}
