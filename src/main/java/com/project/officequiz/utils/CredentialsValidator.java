package com.project.officequiz.utils;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class CredentialsValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    private static final Set<String> DISPOSABLE_EMAIL_DOMAINS;

    static {
        Set<String> disposableEmailDomains = new HashSet<>();
        // Load the disposable email blocklist
        try (InputStream inputStream = CredentialsValidator.class.getResourceAsStream("/disposable_email_blocklist.conf");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            if (inputStream == null) {
                throw new IOException("Resource not found: /disposable_email_blocklist.conf");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                disposableEmailDomains.add(line.trim());
            }
            System.out.println("Loaded " + disposableEmailDomains.size() + " disposable email domains.");
        } catch (IOException e) {
            System.err.println("Error loading disposable email blocklist: " + e.getMessage());
            throw new RuntimeException(e);
        }
        DISPOSABLE_EMAIL_DOMAINS = disposableEmailDomains;
    }

    public static boolean validatePassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean validateEmail(String email){
        boolean isValidEmail = true;
        if(!EMAIL_PATTERN.matcher(email).matches())
            isValidEmail = false;
        try {
            if(isDisposable(email))
                throw new AddressException("Domain is found in Disposable domains");
        }catch (AddressException e) {
            System.out.println(e.getMessage());
            isValidEmail = false;
        }catch (Exception e) {
            isValidEmail = false;
        }
        return isValidEmail;
    }

    public static boolean isDisposable(String email) throws AddressException {
        InternetAddress contact = new InternetAddress(email);
        return isDisposable(contact);
    }

    public static boolean isDisposable(InternetAddress contact) throws AddressException {
        String address = contact.getAddress();
        int domainSep = address.indexOf('@');
        String domain = (domainSep >= 0) ? address.substring(domainSep + 1) : address;
        return DISPOSABLE_EMAIL_DOMAINS.contains(domain);
    }
}
