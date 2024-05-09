package com.example.democuatao.Service;

import com.example.democuatao.dtos.UserDTO;
import com.example.democuatao.model.Role;
import com.example.democuatao.model.User;
import com.example.democuatao.repositories.RoleRepo;
import com.example.democuatao.repositories.UserRepo;
import com.example.democuatao.component.JwtTokenUtil;

import com.shopcuatao.bangiay.exeption.DataNotFound;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{
//    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
//    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws DataNotFound {
        String phoneNumber =userDTO.getPhoneNumber();
        if(userRepo.existsByPhoneNumber(phoneNumber)){
            throw new DataNotFound("da co phoneNumber");
        }
        Role role = roleRepo.findById(1)
                .orElseThrow(()-> new DataNotFound("khong thay id role"));
        User newUser = User.builder()
                .fullname(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .birth(userDTO.getBirth())
                .googleId(userDTO.getGoogleId())
                .facebookId(userDTO.getFacebookId())
                .roleId(role)
               .build();
//       String pass= passwordEncoder.encode(userDTO.getPassword());
//       newUser.setPassword(pass);
        return userRepo.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> user = userRepo.findByPhoneNumber(phoneNumber);
        if (user.isEmpty()){
            throw new DataNotFound("sai tk mk");
        }
//        User existingUser = user.get();
//        if(existingUser.getFacebookId() == 0 && existingUser.getGoogleId()==0){
//            if(!passwordEncoder.matches(password,existingUser.getPassword())){
//                throw new BadCredentialsException("Wrong phone number or password");
//            }
//        }

//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                phoneNumber,password,
//                existingUser.getAuthorities()
//        );

//        authenticationManager.authenticate(authenticationToken);

//        return jwtTokenUtil.generateToken(existingUser);
        return null;
    }
}
