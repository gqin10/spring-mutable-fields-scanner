package com.qode.mutablescanner.writer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class AbstractMutableFieldWriter {

    public void write(Map<String, List<String>> mutableFieldMap) throws IOException {
        prepareWriter();
        writeHeader();
        mutableFieldMap.forEach((beanName, mutableFields) -> {
            try {
                writeLine(beanName, mutableFields);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        afterWrite();
    }

    abstract protected void prepareWriter() throws IOException;

    abstract protected void writeHeader() throws IOException;

    abstract protected void writeLine(String beanName, List<String> mutableFields) throws IOException;

    abstract protected void afterWrite() throws IOException;
}
