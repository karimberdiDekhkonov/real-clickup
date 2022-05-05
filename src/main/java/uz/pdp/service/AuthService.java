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

import java.util.Optional;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
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
        Boolean sent = sendMail(user.getEmail(), user.getEmailCode());
        if (sent){
            userRepository.save(user);
            return new ApiResponse("Successfully Registered",true);
        }
        return new ApiResponse("Error",false);
    }

    public Boolean sendMail(String sendingEmail,String emailCode){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Verification Code");
            mailMessage.setFrom("PDP.UZ");
            mailMessage.setTo(sendingEmail);
            mailMessage.setText(emailCode);
            javaMailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (user.getEmailCode().equals(emailCode)){
                user.setEmailCode(null);
                user.setEnabled(true);
                userRepository.save(user);
                return new ApiResponse("Your account is activated",true);
            }
            else return new ApiResponse("EmailCode is wrong",false);
        }
        else return new ApiResponse("Email is not found",false);
    }
}
