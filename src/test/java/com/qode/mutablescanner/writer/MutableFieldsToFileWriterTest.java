package com.qode.mutablescanner.writer;

import com.qode.mutablescanner.config.AbstractTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.nio.file.Path;

public class MutableFieldsToFileWriterTest {

    @Test
    public void testWriteMutableFields_expectHasFile() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MutableFieldsToFileWriterConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES, AbstractTestConfig.NOSCAN_PACKAGES, AbstractTestConfig.SCAN_MUTABLE_PACKAGES);
        context.refresh();

        Assertions.assertNotNull(context.getBean(MutableFieldsToFileWriter.class));

        String userDir = System.getProperty("user.dir");
        Path expectedPath = Path.of(userDir, "mutableFields.txt");
        File file = expectedPath.toFile();
        Assertions.assertTrue(file.exists());
    }

}
