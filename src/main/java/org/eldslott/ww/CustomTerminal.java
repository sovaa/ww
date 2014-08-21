package org.eldslott.ww;

import com.googlecode.lanterna.terminal.Terminal;

public interface CustomTerminal extends Terminal {
    public void clearKeyQueue();
}
