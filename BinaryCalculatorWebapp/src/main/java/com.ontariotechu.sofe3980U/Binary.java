package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary {
	private String number = "0"; // Binary number represented as a String.
	static final int INSERT_BEFORE = 0;

	/**
	 * Constructs a Binary object from a given string.
	 * The string should only contain binary digits (0s and 1s). Any invalid input
	 * will result in a Binary object representing zero. Leading zeros are removed.
	 *
	 * @param number Binary string to be converted into a Binary object.
	 */
	public Binary(String number) {
		this.number = sanitizeBinaryString(number);
	}

	// Sanitizes the input string to ensure it's a valid binary number.
	private static String sanitizeBinaryString(String binaryString) {
		// Validate each character; return "0" for invalid strings.
		for (char ch : binaryString.toCharArray()) {
			if (ch != '0' && ch != '1') {
				return "0";
			}
		}

		// Remove leading zeros from the binary string.
		String noLeadingZeros = binaryString.replaceFirst("^0+(?!$)", "");
		return noLeadingZeros.isEmpty() ? "0" : noLeadingZeros;
	}

	/**
	 * Return the binary value of the variable
	 *
	 * @return the binary value in a string format.
	 */
	public String getValue() {
		return this.number;
	}

	/**
	 * Adds two Binary objects together. The addition follows binary arithmetic
	 * rules
	 * similar to decimal addition but carried out on binary digits. The result is a
	 * new Binary object representing the sum of the two binary numbers.
	 *
	 * @param binary1 The first Binary object to be added.
	 * @param binary2 The second Binary object to be added.
	 * @return A new Binary object representing the sum of binary1 and binary2.
	 */
	public static Binary add(Binary binary1, Binary binary2) {
		// To hold the carry-over value in binary addition.
		int carry = 0;

		// The length of each binary number.
		int length1 = binary1.number.length();
		int length2 = binary2.number.length();

		// Indices to track the current digit in each binary number, starting from the
		// rightmost digit.
		// The index should start from the index of the least significant bit (LSB) of
		// each number.
		int index1 = length1 - 1;
		int index2 = length2 - 1;

		// Efficiently constructs the resulting binary sum.
		StringBuilder resultBuilder = new StringBuilder();

		// Loop until all digits and the carry are processed.
		while (index1 >= 0 || index2 >= 0 || carry != 0) {
			// Start with the carry-over value.
			int digitSum = carry;

			// Add the corresponding digit from binary1 if available.
			if (index1 >= 0) {
				digitSum += binary1.number.charAt(index1) == '1' ? 1 : 0;
				index1--; // Move to the next digit to the left.
			}

			// Add the corresponding digit from binary2 if available.
			if (index2 >= 0) {
				digitSum += binary2.number.charAt(index2) == '1' ? 1 : 0;
				index2--; // Move to the next digit to the left.
			}

			// We're taking advantage of the fact that we're using the integer datatype.
			carry = digitSum / 2; // Calculate the new carry-over (either 0 or 1).
			int resultDigit = digitSum % 2; // Determine the resulting digit (0 or 1).

			// Prepend the result digit to the sumBuilder.
			resultBuilder.insert(INSERT_BEFORE, resultDigit == 0 ? '0' : '1');
		}

		// Construct and return the Binary object representing the sum.
		return new Binary(resultBuilder.toString());
	}

	/**
	 * Performs a bitwise OR operation on two Binary objects. The OR operation is
	 * performed on each pair of corresponding bits from the two binary numbers.
	 * The result is a new Binary object representing the bitwise OR.
	 *
	 * @param binary1 The first operand for the bitwise OR operation.
	 * @param binary2 The second operand for the bitwise OR operation.
	 * @return A new Binary object representing the result of binary1 OR binary2.
	 */
	public static Binary or(Binary binary1, Binary binary2) {
		// The length of each binary number.
		int length1 = binary1.number.length();
		int length2 = binary2.number.length();

		// Indices to track the current digit in each binary number, starting from the
		// rightmost digit.
		// The index should start from the index of the least significant bit (LSB) of
		// each number.
		// E.g.     0000 1110
		//          ^       ^ 
		// index:   0       7 (Least Significant Bit)
		int index1 = length1 - 1;
		int index2 = length2 - 1;

		int maxLength = Math.max(length1, length2);
		StringBuilder resultBuilder = new StringBuilder();

		// Process each bit, computing the OR operation.
		for (int i = 0; i < maxLength; i++) {
			char binaryBit1 = i < length1 ? binary1.number.charAt(index1 - i) : '0';
			char binaryBit2 = i < length2 ? binary2.number.charAt(index2 - i) : '0';

			// If either bit is 1, the result is 1.
			char orResult = (binaryBit1 == '1' || binaryBit2 == '1') ? '1' : '0';
			resultBuilder.insert(INSERT_BEFORE, orResult);
		}

		return new Binary(resultBuilder.toString());
	}

	/**
	 * Performs a bitwise AND operation on two Binary objects. The AND operation is
	 * performed on each pair of corresponding bits from the two binary numbers.
	 * The result is a new Binary object representing the bitwise AND.
	 *
	 * @param binary1 The first operand for the bitwise AND operation.
	 * @param binary2 The second operand for the bitwise AND operation.
	 * @return A new Binary object representing the result of binary1 AND binary2.
	 */
	public static Binary and(Binary binary1, Binary binary2) {
		// The length of each binary number.
		int length1 = binary1.number.length();
		int length2 = binary2.number.length();

		// Indices of the rightmost digit in each binary number
		// The index of the least significant bit (LSB) of each number.
		// E.g.     0000 1110
		//          ^       ^ 
		// index:   0       7 (Least Significant Bit)
		int lsbIndex1 = length1 - 1;
		int lsbIndex2 = length2 - 1;

		int maxLength = Math.max(length1, length2);
		StringBuilder resultBuilder = new StringBuilder();

		// Process each bit, computing the OR operation.
		for (int i = 0; i < maxLength; i++) {
			char binaryBit1 = i < length1 ? binary1.number.charAt(lsbIndex1 - i) : '0';
			char binaryBit2 = i < length2 ? binary2.number.charAt(lsbIndex2 - i) : '0';

			// If both bits are 1, the result is 1.
			char andResult = (binaryBit1 == '1' && binaryBit2 == '1') ? '1' : '0';
			resultBuilder.insert(INSERT_BEFORE, andResult);
		}

		return new Binary(resultBuilder.toString());
	}

	/**
	 * Multiplies two Binary objects using bitwise multiplication. The
	 * multiplication
	 * is performed similar to decimal multiplication but using binary arithmetic.
	 * The result is a new Binary object representing the product.
	 *
	 * @param binary1 The multiplicand Binary object.
	 * @param binary2 The multiplier Binary object.
	 * @return A new Binary object representing the product of binary1 and binary2.
	 */
	public static Binary multiply(Binary binary1, Binary binary2) {
		Binary result = new Binary("0");
		String zeroPadding = "";

		// The length of each binary number.
		int length2 = binary2.number.length();

		// Index to track the current digit in each binary number, starting from the
		// rightmost digit.
		// The index of the least significant bit (LSB) of each number.
		int lsbIndex2 = length2 - 1;

		// Loop over each bit of binary2 and perform multiplication.
		for (int i = lsbIndex2; i >= 0; i--) {
			if (binary2.number.charAt(i) == '1') {
				result = Binary.add(result, new Binary(binary1.number + zeroPadding));
			}
			zeroPadding += "0";
		}

		return result;
	}
}