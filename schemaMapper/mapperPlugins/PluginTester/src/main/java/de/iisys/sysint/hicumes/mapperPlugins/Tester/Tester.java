package de.iisys.sysint.hicumes.mapperPlugins.Tester;

import de.iisys.sysint.hicumes.mappingEngine.dataSource.readerParser.IReaderParser;
import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Tester {
    public static void main(String[] args) {

        // create the plugin manager
        Path pluginPath = Paths.get("schemaMapper/mapperPlugins/PluginTester/plugins");
        PluginManager pluginManager = new JarPluginManager(pluginPath); // or "new ZipPluginManager() / new DefaultPluginManager()"

        // start and load all plugins of application
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        pluginManager.getStartedPlugins();
        System.out.println(pluginManager.getPlugin("mapperPlugin-XML").getPluginId());

        // retrieve all extensions for "Greeting" extension point
        List<IReaderParser> readerParsers = pluginManager.getExtensions(IReaderParser.class);
        for (IReaderParser readerParser : readerParsers) {
            System.out.println(pluginManager.whichPlugin(readerParser.getClass()).getPluginId());
        }

        List readerParsers2 = pluginManager.getExtensions("mapperPlugin-JSON");
        for (Object readerParser : readerParsers2) {
            System.out.println(pluginManager.whichPlugin(readerParser.getClass()).getPluginId());
        }

        // stop and unload all plugins
        pluginManager.stopPlugins();
        pluginManager.unloadPlugins();

    }
}
