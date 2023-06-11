package com.example.tims;

import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class test {
    public static void main(String[] args) throws IOException {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[64];
        random.nextBytes(keyBytes);
        System.out.println(Base64.getEncoder().encodeToString(keyBytes));
    }

}
