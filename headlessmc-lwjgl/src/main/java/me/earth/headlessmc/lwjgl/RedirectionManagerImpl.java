package me.earth.headlessmc.lwjgl;

import lombok.var;
import me.earth.headlessmc.lwjgl.api.Redirection;
import me.earth.headlessmc.lwjgl.api.RedirectionManager;
import me.earth.headlessmc.lwjgl.redirections.CastRedirection;
import me.earth.headlessmc.lwjgl.redirections.LwjglRedirections;
import me.earth.headlessmc.lwjgl.redirections.ObjectRedirection;
import me.earth.headlessmc.lwjgl.redirections.DefaultRedirections;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RedirectionManagerImpl implements RedirectionManager {
    private final Map<String, Redirection> redirects = new HashMap<>();
    private final Redirection object = new ObjectRedirection(this);
    private final Redirection cast = new CastRedirection(this);

    public RedirectionManagerImpl() {
        LwjglRedirections.register(this);
    }

    @Override
    public void redirect(String desc, Redirection redirection) {
        redirects.put(desc, redirection);
    }

    @Override
    public Object invoke(Object obj, String desc, Class<?> type, Object... args)
        throws Throwable {
        return invoke(desc, type, obj, () -> getFallback(desc, type), args);
    }

    @Override
    public Object invoke(String desc, Class<?> type, Object obj,
                         Supplier<Redirection> fb, Object... args)
        throws Throwable {
        var redirection = redirects.get(desc);
        if (redirection == null) {
            redirection = fb.get();
        }

        return redirection.invoke(obj, desc, type, args);
    }

    private Redirection getFallback(String desc, Class<?> type) {
        if (desc.startsWith(Redirection.CAST_PREFIX)) {
            return cast;
        }

        return DefaultRedirections.fallback(type, object);
    }

}
