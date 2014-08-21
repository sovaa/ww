/*
 * This software is the confidential and proprietary information of
 * Sigma Systems Innovation. ("Confidential Information"). You shall
 * not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sigma Systems Innovation.
 *
 * COPYRIGHT (C) 2014 SIGMA SYSTEMS INNOVATION AB.
 * All rights reserved.
 */
package org.eldslott.ww.util;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * @author <a href="mailto:oscar.eriksson@sigma.se">Oscar Eriksson</a>
 * @date 8/22/14
 */
public enum Color {
    BLACK(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    MAGENTA(5),
    CYAN(6),
    WHITE(7),
    DEFAULT(9);

    private int index;

    private Color(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Terminal.Color toTerminalColor() {
        for (Terminal.Color tc : Terminal.Color.values()) {
            if (tc.getIndex() == getIndex()) {
                return tc;
            }
        }
        throw new IllegalStateException("unknown color: " + name() + "(" + getIndex() + ")");
    }
}
