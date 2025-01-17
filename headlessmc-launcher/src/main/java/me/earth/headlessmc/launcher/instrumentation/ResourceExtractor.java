package me.earth.headlessmc.launcher.instrumentation;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.val;
import me.earth.headlessmc.launcher.files.FileManager;
import me.earth.headlessmc.launcher.util.IOUtil;
import me.earth.headlessmc.util.ResourceUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class ResourceExtractor extends AbstractTransformer {
    private final FileManager fileManager;
    private final String resourceName;

    @Override
    public List<Target> transform(List<Target> targets) throws IOException {
        @Cleanup
        val is = ResourceUtil.getHmcResource(resourceName);
        val file = fileManager.create(resourceName);

        @Cleanup
        val os = new FileOutputStream(file);
        IOUtil.copy(is, os);

        targets.add(new Target(false, file.getAbsolutePath()));
        setRun(true);
        return targets;
    }

}
