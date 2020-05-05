package ua.nure.tereshchenko.SummaryTask4.password;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password hash.
 *
 * @author A.Tereshchenko
 */
public final class Password {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * @param str Plain password string.
     * @return hashed password string.
     */
    public static String hash(String str) throws NoSuchAlgorithmException {
        MessageDigest digest;
        StringBuilder hexString = new StringBuilder();
        digest = MessageDigest.getInstance("SHA-512");
        digest.update(str.getBytes(StandardCharsets.UTF_8));
        for (byte d : digest.digest()) {
            hexString.append(getFirstHexDigit(d)).append(getSecondHexDigit(d));
        }
        return hexString.toString();
    }

    private static char getFirstHexDigit(byte x) {
        return HEX_DIGITS[(0xFF & x) / 16];
    }

    private static char getSecondHexDigit(byte x) {
        return HEX_DIGITS[(0xFF & x) % 16];
    }
}
