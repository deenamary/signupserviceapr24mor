package com.example.signupserviceapr24mor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class MainRestController {

    CredentialRepository credentialRepository;
    UserdetailRepository userdetailRepository;
    WalletRepository walletRepository;
    UsernamewalletlinkRepository usernamewalletlinkRepository;
    UsertypelinkRepository usertypelinkRepository;

    MainRestController(CredentialRepository credentialRepository,
                       UserdetailRepository userdetailRepository,
                       WalletRepository walletRepository,
                       UsernamewalletlinkRepository usernamewalletlinkRepository,
                       UsertypelinkRepository usertypelinkRepository){
        this.credentialRepository = credentialRepository;
        this.userdetailRepository = userdetailRepository;
        this.walletRepository = walletRepository;
        this.usernamewalletlinkRepository = usernamewalletlinkRepository;
        this.usertypelinkRepository = usertypelinkRepository;
    }

    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestBody Credential credential) {
        if (credentialRepository.existsByUsername(credential.getUsername())) {
            return ResponseEntity.badRequest().body("User already exists!");
        } else {
            credentialRepository.save(credential);
            return ResponseEntity.ok("New user signup");
        }
    }

    @PostMapping("save/user/details")
    public ResponseEntity<Userdetail>  saveUserDetails(@RequestBody Userdetail userdetail,
                                                       @Autowired Wallet wallet,
                                                       @Autowired Usernamewalletlink usernamewalletlink
    ) {

        userdetailRepository.save(userdetail);
        wallet.setWalletid(String.valueOf(UUID.randomUUID()));
        wallet.setBalance(5000000);
        wallet.setState("valid");
        walletRepository.save(wallet);

        usernamewalletlink.setWalletid(wallet.getWalletid());
        usernamewalletlink.setUsername(userdetail.getUsername());
        usernamewalletlinkRepository.save(usernamewalletlink);

        return ResponseEntity.ok(userdetail);
    }


    @PostMapping("save/user/type/{username}/{usertype}")
    public ResponseEntity<Usertypelink>  saveUserType(@PathVariable String username,
                                                      @PathVariable String usertype,
                                                      Usertypelink usertypelink)
    {
        usertypelink.setLinkid(String.valueOf(UUID.randomUUID()));
        usertypelink.setUsername(username);
        usertypelink.setType(usertype);

        usertypelinkRepository.save(usertypelink);

        return ResponseEntity.ok(usertypelink);
    }
}
