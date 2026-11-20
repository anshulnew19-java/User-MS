package in.ashokit.service;

import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.UserDto;
import in.ashokit.entity.UserEntity;
import in.ashokit.mapper.UserMapper;
import in.ashokit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService emailService;

    public UserDto saveUser(UserDto userDto) {
        String randomPwd = generateRandomPwd(6);
        userDto.setPwd(randomPwd);
        userDto.setPwdUpdated("NO");
        UserEntity userEntity = UserMapper.dtoToEntity(userDto);
        UserEntity saved = userRepo.save(userEntity);
        if(saved != null) {
            String subject = "Your Account Created - OurMart";
            String body = "Your Temporary password is :"+ randomPwd;
            emailService.sendEmail(userDto.getEmail(), subject, body);
        }
        return UserMapper.entityToDto(saved);
    }

    public UserDto login(String email, String pwd) {
        UserEntity byEmailAndPwd = userRepo.findByEmailAndPwd(email, pwd);
        if(byEmailAndPwd != null) {
            return UserMapper.entityToDto(byEmailAndPwd);
        }
        return null;
    }

    public UserDto resetPwd(ResetPwdDto resetPwdDto) {
        UserEntity byEmail = userRepo.findByEmail(resetPwdDto.getEmail());
        if (byEmail != null) {
            byEmail.setPwd(resetPwdDto.getNewPwd());
            byEmail.setPwdUpdated("YES");
            userRepo.save(byEmail);
            return UserMapper.entityToDto(byEmail);
        }
        return null;
    }

    public UserDto getUserByEmail(String email) {
        UserEntity byEmail = userRepo.findByEmail(email);
        if(byEmail != null) {
            return UserMapper.entityToDto(byEmail);
        }
        return null;
    }

    private String generateRandomPwd(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789#@$";
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<length; i++) {
            int index = random.nextInt(chars.length());
            char ch = chars.charAt(index);
            buffer.append(ch);
        }
        return buffer.toString();
    }


}
