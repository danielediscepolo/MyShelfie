package it.polimi.ingsw.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**Dummy class for testing purposes.</br>
 * Usefull to redirect the PrintStream System.out to null.
 * @see it.polimi.ingsw.view.cli.CLI
 * */
public class NullPrintStream extends PrintStream {

    public NullPrintStream() {
        super(new NullByteArrayOutputStream());
    }

    private static class NullByteArrayOutputStream extends ByteArrayOutputStream {

        @Override
        public void write(int b) {}

        @Override
        public void write(byte[] b, int off, int len) {}

        @Override
        public void writeTo(OutputStream out) throws IOException {}

    }

}