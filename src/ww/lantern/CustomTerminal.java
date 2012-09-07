package ww.lantern;

import org.lantern.terminal.Terminal;

public interface CustomTerminal extends Terminal {
    public void clearKeyQueue();
}
