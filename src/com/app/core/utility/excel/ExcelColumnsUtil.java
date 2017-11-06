package com.app.core.utility.excel;

/**
 * 
 * Excel列号转字母工具类
 * 
 */
public final class ExcelColumnsUtil {

	private ExcelColumnsUtil() {
	}

	private static String[] sources = new String[] { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };

	/**
	 * (256 for *.xls, 16384 for *.xlsx)
	 * 
	 * @param columnNum
	 *            列的个数，从1开始
	 * @throws IllegalArgumentException
	 *             如果 columnNum 超出该范围 [1,16384]
	 * @return 返回[1,columnNum]共columnNum个对应xls列字母的数组
	 */
	public static String[] getColumnLabels(int columnNum) {
		if (columnNum < 1 || columnNum > 16384)
			throw new IllegalArgumentException();
		String[] columns = new String[columnNum];
		if (columnNum < 27) {
			System.arraycopy(sources, 0, columns, 0, columnNum);
			return columns;
		}
		int multiple = -1;
		int remainder;
		System.arraycopy(sources, 0, columns, 0, 26);
		int currentLoopIdx = 0;
		if (columnNum < 703) {
			for (int i = 26; i < columnNum; i++) {
				remainder = currentLoopIdx % 26;
				if (remainder == 0) {
					multiple++;
				}
				columns[i] = sources[multiple] + columns[remainder];
				currentLoopIdx++;
			}
		} else {
			int currentLen = 26;
			int totalLen = 26;
			int lastLen = 0;
			for (int i = 26; i < columnNum; i++) {
				remainder = currentLoopIdx % currentLen;
				if (remainder == 0) {
					multiple++;
					int j = multiple % 26;
					if (j == 0 && multiple != 0) {
						lastLen = totalLen;
						currentLen = 26 * currentLen;
						totalLen = currentLen + lastLen;
						currentLoopIdx = 0;
					}
				}
				columns[i] = sources[multiple % 26]
						+ columns[remainder + lastLen];
				currentLoopIdx++;
			}
		}

		return columns;
	}

	/**
	 * 返回该列号对应的字母
	 * 
	 * @param columnNo
	 *            (xls的)第几列（从1开始）
	 */
	private static String getCorrespondingLabel(int columnNo) {
		if (columnNo < 1/** ||columnNo>16384 **/
		)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(5);
		int remainder = columnNo % 26;
		if (remainder == 0) {
			sb.append("Z");
			remainder = 26;
		} else {
			sb.append(sources[remainder - 1]);
		}

		while ((columnNo = (columnNo - remainder) / 26 - 1) > -1) {
			remainder = columnNo % 26;
			sb.append(sources[remainder]);
		}

		return sb.reverse().toString();
	}

	/**
	 * 列号转字母
	 * 
	 * @param columnIndex
	 *            poi里xls的列号（从0开始）
	 * @throws IllegalArgumentException
	 *             if columnIndex less than 0
	 * @return 该列对应的字母
	 */
	public static String getIndexLabel(int columnIndex) {
		return getCorrespondingLabel(columnIndex + 1);
	}

}