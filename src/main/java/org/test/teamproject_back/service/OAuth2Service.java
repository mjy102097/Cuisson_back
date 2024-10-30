package org.test.teamproject_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.test.teamproject_back.dto.request.ReqOAuth2SignupDto;
import org.test.teamproject_back.entity.Role;
import org.test.teamproject_back.entity.User;
import org.test.teamproject_back.entity.UserRoles;
import org.test.teamproject_back.repository.OAuth2UserMapper;
import org.test.teamproject_back.repository.RoleMapper;
import org.test.teamproject_back.repository.UserMapper;
import org.test.teamproject_back.repository.UserRolesMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class OAuth2Service implements OAuth2UserService {

    @Autowired
    private DefaultOAuth2UserService defaultOAuth2UserService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRolesMapper userRolesMapper;
    @Autowired
    private OAuth2UserMapper oAuth2UserMapper;

    @Override
    public org.springframework.security.oauth2.core.user.OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> oAuth2Attributes = new HashMap<>();
        oAuth2Attributes.put("provider", userRequest.getClientRegistration().getClientName());

        switch (userRequest.getClientRegistration().getClientName()) {
            case "Google":
                oAuth2Attributes.put("id", attributes.get("sub").toString());
                break;
            case "Naver":
                attributes = (Map<String, Object>) attributes.get("response");
                oAuth2Attributes.put("id", attributes.get("id").toString());
                break;
        }

        return new DefaultOAuth2User(new HashSet<>(), oAuth2Attributes, "id");
    }

//    public ResponseEntity<?> signup(ReqOAuth2SignupDto dto) {
//        User user = dto.toEntity(passwordEncoder);
//        userMapper.save(user);
//        Role role = roleMapper.findByName("ROLE_USER");
//        if (role == null) {
//            role = Role.builder().name("ROLE_USER").build();
//            roleMapper.save(role);
//        }
//        userRolesMapper.save(UserRoles.builder()
//                .userId(user.getUserId())
//                .roleId(role.getRoleId())
//                .build());
//        oAuth2UserMapper.save(org.test.teamproject_back.entity.OAuth2User.builder()
//                        .userId(user.getUserId())
//                        .oAuth2Name(dto.getOauth2Name())
//                        .provider(dto.getProvider())
//                .build())
//    }
}
