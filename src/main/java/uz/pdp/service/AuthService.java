package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.entity.User;
import uz.pdp.entity.enums.SystemRoleName;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.RegisterDto;
import uz.pdp.repository.UserRepository;

import java.util.Random;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public ApiResponse registerUser(RegisterDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())){
            return new ApiResponse("This email is already exist in our system",false);
        }
        User user = new User(
                dto.getFullName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                SystemRoleName.SYSTEM_ROLE_USER
        );
        int code = new Random().nextInt(999999);
        user.setEmailCode(String.valueOf(code).substring(0,4));
        sendMail(user.getEmail(), user.getEmailCode());
        userRepository.save(user);
        return new ApiResponse("Successfully Registered",true);
    }

    public Boolean sendMail(String sendingEmail,String emailCode){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Verification Code");
            mailMessage.setFrom("@Karimberdi.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setText(emailCode);
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
