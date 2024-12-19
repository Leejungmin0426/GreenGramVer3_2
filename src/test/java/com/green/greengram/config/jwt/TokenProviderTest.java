package com.green.greengram.config.jwt;

import com.green.greengram.config.security.MyUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {
    //테스트는 생성자를 이용한 DI가 불가능'
    // DI방법은 필드, Setter메소드, todtjdwk
    //테스트 때는 필드 주입방식을 사용한다.

    @Autowired
    private TokenProvider tokenProvider;
    @Test
    public void generateToken() {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(10);

        List<String> roles = new ArrayList<>(2);
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        jwtUser.setRoles(roles);

        //When(실행단계)
        String token = tokenProvider.generateToken(jwtUser, Duration.ofHours(3));

        //Then 검증단계
        assertNotNull(token);

        System.out.println("token: " + token);


    }

    @Test
    void validToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJncmVlbkBncmVlbi5rciIsImlhdCI6MTczNDUwNjQ0NiwiZXhwIjoxNzM0NTE3MjQ2LCJzaWduZWRVc2VyIjoie1wic2lnbmVkVXNlcklkXCI6MTAsXCJyb2xlc1wiOltcIlJPTEVfVVNFUlwiLFwiUk9MRV9BRE1JTlwiXX0ifQ.YSMrdwxkq3RWIsjEMcDC-niicTOQqBifDlPace9MDKAGdMD9OLPTX3MByshgmi3O6tn-KHPpASwmas9LNliVbQ";
        boolean result = tokenProvider.validToken(token);

        assertFalse(result);
    }

    @Test
    void getAuthentication() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJncmVlbkBncmVlbi5rciIsImlhdCI6MTczNDQwMzYyNSwiZXhwIjoxNzM0NDE0NDI1LCJzaWduZWRVc2VyIjoie1wic2lnbmVkVXNlcklkXCI6MTAsXCJyb2xlc1wiOltcIlJPTEVfVVNFUlwiLFwiUk9MRV9BRE1JTlwiXX0ifQ.E5ONOtj9X8-6lBtM-A5Q0VCAkJheEtNEcXzyvDPgi3i0mkniJfOFa9ToxcsVNL7_n_qQRVifwvhbDXbdMEHCMw";  //3시간 짜리
        Authentication authentication = tokenProvider.getAuthentication(token);

        assertNotNull(authentication);

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        JwtUser jwtUser = myUserDetails.getJwtUser();

        JwtUser expectedJwtUser = new JwtUser();
        expectedJwtUser.setSignedUserId(10);

        List<String> roles = new ArrayList<>(2);
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        expectedJwtUser.setRoles(roles);

      assertEquals(expectedJwtUser, jwtUser);

    }
}