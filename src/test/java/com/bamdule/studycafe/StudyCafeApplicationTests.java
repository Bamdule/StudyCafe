package com.bamdule.studycafe;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")
class StudyCafeApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        //숫자, 알파벳, _!@#$% 문자를 입력할 수 있다.
        Pattern pattern = Pattern.compile("(010|011)[0-9]{3,4}[0-9]{4}");

        Matcher matcher = pattern.matcher("0127371614"); //true


        Assertions.assertTrue(matcher.matches());

    }

    @Test
    void js() {
        String key = "key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");

        pbeEnc.setPassword(key); //2번 설정의 암호화 키를 입력

        String enc = pbeEnc.encrypt("tester"); //암호화 할 내용
        System.out.println("enc = " + enc); //암호화 한 내용을 출력

        //테스트용 복호화
        String des = pbeEnc.decrypt(enc);
        System.out.println("des = " + des);
    }

    @Test
    public void key() throws IOException {

        ClassPathResource resource = new ClassPathResource("key/jasyptKey");
        Path path = Paths.get(resource.getURI());
        String value = Files.readString(path);

        System.out.println(value);
    }


    @Test
    void jasypt() {
        String url = "url";
        String username = "username";
        String password = "password";

        System.out.println(jasyptEncoding(url));
        System.out.println(jasyptEncoding(username));
        System.out.println(jasyptEncoding(password));
    }

    public String jasyptEncoding(String value) {

        String key = "jasyptkey";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

}
