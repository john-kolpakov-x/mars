package kz.pompei.mars.util;

import kz.pompei.mars.application.ImporterAll;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(classes = ImporterAll.class)
public abstract class ParentTestNg extends AbstractTestNGSpringContextTests {
}
