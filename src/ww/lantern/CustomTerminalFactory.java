package ww.lantern;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.lantern.TerminalFactory;
import org.lantern.terminal.Terminal;

public class CustomTerminalFactory extends TerminalFactory {
    @Override
    public Terminal createTerminal(
            InputStream terminalInput, 
            OutputStream terminalOutput, 
            Charset terminalCharset)
    {
        return new CustomSwingTerminal();
    }
}
