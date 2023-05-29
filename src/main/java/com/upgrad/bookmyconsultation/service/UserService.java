package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.User;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.provider.PasswordCryptographyProvider;
import com.upgrad.bookmyconsultation.repository.UserRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordCryptographyProvider passwordCryptographyProvider;

    public User register(User user) throws InvalidInputException {
        ValidationUtils.validate(user);

        user.setCreatedDate(LocalDate.now().toString());
        encryptPassword(user);
        userRepository.save(user);
        return user;
    }

    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(ResourceUnAvailableException::new);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private void encryptPassword(final User newUser) {
        String password = newUser.getPassword();
        final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
        newUser.setSalt(encryptedData[0]);
        newUser.setPassword(encryptedData[1]);
    }

	public User registerUser(User user) throws InvalidInputException {
		ValidationUtils.validate(user);
	
		user.setCreatedDate(LocalDate.now().toString());
		encryptPassword(user);
		userRepository.save(user);
		return user;
	}
	
}
