package ttv.poltoraha.pivka.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.repository.MyUserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CryptPassConfig {
    private final MyUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${feature.flag.encodePasswords:false}")
    private boolean encodePasswordsFlag;

    @PostConstruct
    public void encodeExistingPasswords() {
        if (!encodePasswordsFlag) {
            return;
        }

        List<MyUser> users = userRepository.findAll();

        for (MyUser user : users) {
            String pass = user.getPassword();
            if (pass.startsWith("{noop}"))
                pass = pass.replace("{noop}", "");
            userRepository.updatePassword(user.getUsername(), passwordEncoder.encode(pass));

        }

    }
}
