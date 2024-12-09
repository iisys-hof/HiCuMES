package de.iisys.sysint.hicumes.mapperPlugins.JSONPlugin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class JSONPlugin extends Plugin {

    public JSONPlugin(PluginWrapper wrapper) {
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
