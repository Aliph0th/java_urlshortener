package com.bsujava.servlet.util;

import org.testng.annotations.Test;
import java.util.UUID;
import static org.testng.Assert.*;

public class VerificationUtilTest {

    @Test
    public void testGenerateVerificationTokenNotNullOrEmpty() {
        String token = VerificationUtil.generateVerificationToken();
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testGenerateVerificationTokenIsValidUUID() {
        String token = VerificationUtil.generateVerificationToken();
        UUID uuid = UUID.fromString(token);
        assertEquals(uuid.toString(), token);
    }

    @Test
    public void testGenerateVerificationTokenUnique() {
        String t1 = VerificationUtil.generateVerificationToken();
        String t2 = VerificationUtil.generateVerificationToken();
        assertNotEquals(t1, t2);
    }
}
