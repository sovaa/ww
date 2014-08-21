package org.eldslott.ww;

import com.googlecode.lanterna.terminal.Terminal;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;


public class CustomTerminalFactory { //extends TerminalFactory {
    //@Override
    public Terminal createTerminal(
            InputStream terminalInput, 
            OutputStream terminalOutput, 
            Charset terminalCharset)
    {
        return new CustomSwingTerminal();
    }
}
