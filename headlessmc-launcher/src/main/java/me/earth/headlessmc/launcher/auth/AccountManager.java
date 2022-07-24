package me.earth.headlessmc.launcher.auth;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import lombok.CustomLog;
import lombok.Getter;
import lombok.val;
import me.earth.headlessmc.api.config.Config;
import me.earth.headlessmc.launcher.LauncherProperties;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// TODO: support Mojang?
// TODO: when another config is loaded invalidate lastAccount?
@CustomLog
public class AccountManager implements Iterable<Account> {
    private final Map<Integer, Account> cache = new ConcurrentHashMap<>();
    @Getter
    private Account lastAccount;

    public Account login(Config config) throws AuthException {

        return new Account("???", "test", "NoAccount", "???");
    }

    public Account login(String email, String password) throws AuthException {
        return new Account("???", "test", "NoAccount", "???");
    }

    @Override
    public Iterator<Account> iterator() {
        return cache.values().iterator();
    }

    private void save(Account account) {

    }

}
