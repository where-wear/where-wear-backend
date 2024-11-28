package WhereWear.server.wherewear.user.account.service;

import WhereWear.server.wherewear.user.User;
import WhereWear.server.wherewear.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WithdrawService {
    private final UserRepository userRepository;

    public void withdraw(String email) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
        userRepository.delete(existingUser);
    }
}
