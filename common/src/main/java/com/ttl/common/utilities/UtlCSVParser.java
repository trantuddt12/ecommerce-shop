package com.ttl.common.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

public class UtlCSVParser {
	public static Vector<Vector<String>> parseCSV(String pString) throws IOException {
		BufferedReader lvReader = new BufferedReader(new StringReader(pString));
		Throwable var2 = null;

		Vector var3;
		try {
			var3 = parseCSV(lvReader);
		} catch (Throwable var12) {
			var2 = var12;
			throw var12;
		} finally {
			if (lvReader != null) {
				if (var2 != null) {
					try {
						lvReader.close();
					} catch (Throwable var11) {
						var2.addSuppressed(var11);
					}
				} else {
					lvReader.close();
				}
			}

		}

		return var3;
	}

	public static Vector<Vector<String>> parseCSV(File pFile) throws IOException {
		BufferedReader lvReader = new BufferedReader(new FileReader(pFile));
		Throwable var2 = null;

		Vector var3;
		try {
			var3 = parseCSV(lvReader);
		} catch (Throwable var12) {
			var2 = var12;
			throw var12;
		} finally {
			if (lvReader != null) {
				if (var2 != null) {
					try {
						lvReader.close();
					} catch (Throwable var11) {
						var2.addSuppressed(var11);
					}
				} else {
					lvReader.close();
				}
			}

		}

		return var3;
	}

	public static Vector<Vector<String>> parseCSV(BufferedReader pBufferedReader) throws IOException {
		Vector<Vector<String>> lvVector = new Vector();
		char lvSeparator = 44;

		while (true) {
			String lvLine;
			do {
				if ((lvLine = pBufferedReader.readLine()) == null) {
					return lvVector;
				}
			} while (lvLine.equals(""));

			Vector<String> lvRow = new Vector();
			String lvField = "";

			for (int i = 0; i < lvLine.length(); ++i) {
				char lvChar = lvLine.charAt(i);
				if (lvChar != lvSeparator && i != lvLine.length() - 1) {
					if (lvChar == '"') {
						lvSeparator = 34;
					} else {
						lvField = lvField + lvChar;
					}
				} else {
					if (i == lvLine.length() - 1 && lvChar != lvSeparator && lvChar != '"') {
						lvField = lvField + lvChar;
					}

					lvRow.add(lvField);
					lvField = "";
					if (lvSeparator == 44 && lvChar == lvSeparator && i == lvLine.length() - 1) {
						lvRow.add(lvField);
					}

					if (lvSeparator != 44) {
						lvSeparator = 44;
						++i;
					}
				}
			}

			lvVector.add(lvRow);
		}
	}

	public static Vector<String> parseCSVLine(String pLine) {
		Vector<String> lvRow = new Vector();
		String lvField = "";
		char lvSeparator = 44;

		for (int i = 0; i < pLine.length(); ++i) {
			char lvChar = pLine.charAt(i);
			if (lvChar != lvSeparator && i != pLine.length() - 1) {
				if (lvChar == '"') {
					lvSeparator = 34;
				} else {
					lvField = lvField + lvChar;
				}
			} else {
				if (i == pLine.length() - 1 && lvChar != lvSeparator && lvChar != '"') {
					lvField = lvField + lvChar;
				}

				lvRow.add(lvField);
				lvField = "";
				if (lvSeparator != 44) {
					lvSeparator = 44;
					++i;
				}
			}
		}

		return lvRow;
	}
}