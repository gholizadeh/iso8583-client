/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2019 jPOS Software SRL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ir.gnrco.credit.card.parser.packer;


import org.jpos.iso.Interpreter;

import java.nio.charset.Charset;

/**
 * Implements Windows 1256 Interpreter. Strings are converted to and from Windows 1256 bytes.
 *
 * @author sa.gholizadeh [gholizade.saeed@yahoo.com]
 */
public class FarsiInterpreter implements Interpreter
{
    public static final Charset WINDOWS_1256 = Charset.forName("Windows-1256");

    /** An instance of this Interpreter. Only one needed for the whole system */
    public static final FarsiInterpreter INSTANCE = new FarsiInterpreter();

    /**
	 * (non-Javadoc)
	 *
     */
    @Override
    public void interpret(String data, byte[] b, int offset)
    {
        System.arraycopy(data.getBytes(FarsiInterpreter.WINDOWS_1256), 0, b, offset, data.length());
    }

    /**
	 * (non-Javadoc)
	 *
     */
    @Override
    public String uninterpret (byte[] rawData, int offset, int length) {
        byte[] ret = new byte[length];
        try {
            System.arraycopy(rawData, offset, ret, 0, length);
            return new String(ret, FarsiInterpreter.WINDOWS_1256);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException(
                String.format("Required %d but just got %d bytes", length, rawData.length-offset)
            );
        }
    }

    /**
	 * (non-Javadoc)
	 *
     */
    @Override
    public int getPackedLength(int nDataUnits)
    {
        return nDataUnits;
    }
}
