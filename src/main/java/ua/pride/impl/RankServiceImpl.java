package ua.pride.impl;

import org.springframework.stereotype.Service;
import ua.pride.entity.User;
import ua.pride.repository.UserRepository;
import ua.pride.service.RankService;

import java.util.Optional;

@Service
public class RankServiceImpl implements RankService {

    private final UserRepository userRepository;

    public RankServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean registerNewUser(String disTag, String siegeUsername, String rank) {
        Optional<User> existUser = userRepository.findByDisTag(disTag);
        if (existUser.isPresent()) {
            return false;
        } else {
            userRepository.save(new User(disTag, siegeUsername, rank));
            return true;
        }
    }
}
