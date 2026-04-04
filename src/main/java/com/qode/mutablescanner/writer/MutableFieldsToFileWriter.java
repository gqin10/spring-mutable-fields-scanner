package com.qode.mutablescanner.writer;

import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

public class MutableFieldsToFileWriter extends AbstractMutableFieldWriter {

    private static final String DEFAULT_FILENAME = "mutableFields.txt";

    private final String path;

    private BufferedWriter writer;

    public MutableFieldsToFileWriter() throws IOException {
        this(null);
    }

    public MutableFieldsToFileWriter(String path) throws IOException {
        if (StringUtils.hasLength(path)) {
            this.path = path;
        } else {
            String userDir = System.getProperty("user.dir");
            Path userDirPath = Path.of(userDir, DEFAULT_FILENAME);
            this.path = userDirPath.toAbsolutePath().toString();
        }
        this.prepareWriter();
    }

    @Override
    protected void prepareWriter() throws IOException {
        this.writer = new BufferedWriter(new FileWriter(path));
    }

    @Override
    protected void writeHeader() throws IOException {
        this.writer.write("| Bean Name | Mutable Fields |");
        this.writer.newLine();
        this.writer.write("| - | - |");
    }

    @Override
    protected void writeLine(String beanName, List<String> mutableFields) throws IOException {
        this.writer.newLine();
        this.writer.write(String.format("| %s | %s |", beanName, mutableFields));
    }

    @Override
    protected void afterWrite() throws IOException {
        this.writer.newLine();
        this.writer.newLine();
        this.writer.write(String.format("Generated on: %s", new Date()));
        writer.close();
    }
}
