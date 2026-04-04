package com.qode.mutablescanner.writer;

import com.qode.mutablescanner.MutableScannerApplicationListener;
import com.qode.mutablescanner.config.AbstractTestConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@PropertySource("classpath:enableContinueOnMutableField.properties")
public class MutableFieldsToFileWriterConfig extends AbstractTestConfig {

    @Value("${mutableScanner.outputPath}")
    private String outputPath;

    @Bean
    public MutableScannerApplicationListener applicationListener() {
        return new MutableScannerApplicationListener();
    }

    @Bean
    public AbstractMutableFieldWriter mutableFieldToFileWriter() throws IOException {
        return new MutableFieldsToFileWriter(outputPath);
    }

}
