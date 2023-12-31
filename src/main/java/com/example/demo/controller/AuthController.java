package com.example.demo.controller;

import com.example.demo.dto.request.ChangeAvatar;
import com.example.demo.dto.request.SignInForm;
import com.example.demo.dto.request.SignUpForm;
import com.example.demo.dto.response.JwtResponse;
import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.jwt.JwtTokenFilter;
import com.example.demo.security.userprincal.UserPrinciple;
import com.example.demo.service.impl.RoleServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RequestMapping("/")
@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
        }
        if (userService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponMessage("noemail"), HttpStatus.OK);
        }
        User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getBirthday());
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            if (role.equals("admin")) {
                Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                        () -> new RuntimeException("Role not found")
                );
                roles.add(adminRole);
            } else {
                Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                roles.add(userRole);
            }
        });
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getId(), userPrinciple.getName(), userPrinciple.getAvatar(), userPrinciple.getAuthorities()));
    }

    @PutMapping("change-avatar")
    public ResponseEntity<?> changeAvatar(HttpServletRequest request, @Valid @RequestBody ChangeAvatar changeAvatar) {
        String token = jwtTokenFilter.getJwt(request);
        String username = jwtProvider.getUerNameFromToken(token);
        User user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (changeAvatar.getAvatar() == null || changeAvatar.getAvatar().trim().equals("")) {
            return new ResponseEntity<>(new ResponMessage("no"), HttpStatus.OK);
        } else {
            user.setAvatar(changeAvatar.getAvatar());
            userService.save(user);
            return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getListUser() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
}
