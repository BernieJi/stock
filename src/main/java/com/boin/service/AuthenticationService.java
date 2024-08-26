package com.boin.service;

import com.boin.common.BaseResponse;
import com.boin.config.JwtService;
import com.boin.email.EmailSender;
import com.boin.entity.Authentication.AuthenticationResponse;
import com.boin.entity.Authentication.ConfirmationToken;
import com.boin.entity.Authentication.LoginRequest;
import com.boin.entity.Authentication.RegisterRequest;
import com.boin.entity.User;
import com.boin.repository.TokenRepository;
import com.boin.repository.UserRepository;
import com.boin.repository.WatchListRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final EmailSender emailSender;
    private final WatchListRepository watchListRepository;

    /*
     *
     * 用戶註冊
     *
     */
    public ResponseEntity<AuthenticationResponse> register(HttpServletRequest httpServletRequest, RegisterRequest request){
        try {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            User user = new User(UUID.randomUUID().toString(), request.getUsername(), encodedPassword, request.getEmail(), request.getRole());
            userRepository.addUser(user);
            String jwt = jwtService.generateToken(user);
            ConfirmationToken token = new ConfirmationToken(UUID.randomUUID().toString(), user.getId(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(15));
            tokenRepository.addToken(token);
            // TODO send confirmation email
            String scheme = httpServletRequest.getScheme();
            String domain = httpServletRequest.getServerName();
            String link = scheme + "://" + domain + ":7878/api/v1/auth/confirm?token=" + token.getToken();
            emailSender.send(request.getEmail(), buildEmail(request.getUsername(), link));
            // 創建預設的追蹤表
            watchListRepository.defaultAddWatchList(user.getId());

            AuthenticationResponse res = AuthenticationResponse.builder().code("200").message("成功註冊").token(jwt).build();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            AuthenticationResponse res = AuthenticationResponse.builder().code("500").message("註冊失敗").token(null).build();
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    /*
       用戶登入
     */
    public ResponseEntity<AuthenticationResponse> login(LoginRequest request){
        User user = userRepository.getUserByUserName(request.getUsername());
        if (Objects.isNull(user)) {
            AuthenticationResponse res = AuthenticationResponse.builder().code("400").message("未找到此用戶").token("").build();
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );

            String jwt = jwtService.generateToken(user);
            AuthenticationResponse res = AuthenticationResponse.builder().code("200").message("成功登入").token(jwt).build();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // 密碼错误
            AuthenticationResponse res = AuthenticationResponse.builder()
                    .code("401")
                    .message("密碼错误")
                    .token("")
                    .build();
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenRepository.getToken(token);
        if(Objects.isNull(confirmationToken)){
            throw new IllegalStateException("token doesn't exist");
        }

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        Integer updateConfirmedAt = tokenRepository.updateConfirmedAt(confirmationToken.getToken());
        if(Objects.isNull(updateConfirmedAt)){
            AuthenticationResponse res = AuthenticationResponse.builder().code("500").message("資料庫更新錯誤").token("").build();
            // return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
            return "資料庫更新失敗！！";
        }
        userRepository.enableUser(confirmationToken.getUserId());
        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\" " + link + "\">點選此連結開通會員服務</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
