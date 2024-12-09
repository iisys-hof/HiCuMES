package de.iisys.sysint.hicumes.mapperPlugins.CSVPlugin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class CSVPlugin extends Plugin {

    public CSVPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("CSVPlugin.start()");
    }

    @Override
    public void stop() {
        System.out.println("CSVPlugin.stop()");
    }

    @Override
    public void delete() {
        System.out.println("CSVPlugin.delete()");
    }
}
