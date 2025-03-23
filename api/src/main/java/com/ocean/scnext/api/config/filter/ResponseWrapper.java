package com.ocean.scnext.api.config.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

class ResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream copy = new ByteArrayOutputStream();
    private final ServletOutputStream outputStream;
    private final PrintWriter writer;

    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        this.outputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }
            @Override
            public void setWriteListener(WriteListener listener) {}
            @Override
            public void write(int b) throws IOException {
                copy.write(b);
            }
        };
        this.writer = new PrintWriter(copy);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    public byte[] getCopy() {
        writer.flush();
        return copy.toByteArray();
    }
}