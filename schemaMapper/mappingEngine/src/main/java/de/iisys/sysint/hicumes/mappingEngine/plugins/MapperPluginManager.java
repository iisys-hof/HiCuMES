package de.iisys.sysint.hicumes.mappingEngine.plugins;

import de.iisys.sysint.hicumes.mappingEngine.exceptions.ExtensionNotFoundException;
import de.iisys.sysint.hicumes.mappingEngine.plugins.outputPlugin.outputWriter.IOutputWriter;
import de.iisys.sysint.hicumes.mappingEngine.plugins.parserPlugin.dataParser.IDataParser;
import de.iisys.sysint.hicumes.mappingEngine.plugins.inputPlugin.inputReader.IInputReader;
import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MapperPluginManager {

    private static MapperPluginManager INSTANCE;
    private final String PLUGIN_PATH = "../modules/plugins/";
    private PluginManager pluginManager;
    private List<String> availablePlugins = new ArrayList<>();

    private MapperPluginManager() {
        Path pluginPath = Paths.get(PLUGIN_PATH);
        pluginManager = new JarPluginManager(pluginPath);
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        System.out.println("Available IInputReader Plugins: ");
        var readers = pluginManager.getExtensions(IInputReader.class);
        for (IInputReader inputReader : readers) {
            System.out.println(pluginManager.whichPlugin(inputReader.getClass()).getPluginId());
            availablePlugins.add(pluginManager.whichPlugin(inputReader.getClass()).getPluginId());
        }

        System.out.println("Available IDataParser Plugins: ");
        var parsers = pluginManager.getExtensions(IDataParser.class);
        for (IDataParser inputParser : parsers) {
            System.out.println(pluginManager.whichPlugin(inputParser.getClass()).getPluginId());
            availablePlugins.add(pluginManager.whichPlugin(inputParser.getClass()).getPluginId());
        }

        System.out.println("Available IOutputWriter Plugins: ");
        var writers = pluginManager.getExtensions(IOutputWriter.class);
        for (IOutputWriter outputWriter : writers) {
            System.out.println(pluginManager.whichPlugin(outputWriter.getClass()).getPluginId());
            availablePlugins.add(pluginManager.whichPlugin(outputWriter.getClass()).getPluginId());
        }

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    }

    public static MapperPluginManager getInstance() {
        if(INSTANCE == null)
        {
            INSTANCE = new MapperPluginManager();
        }
        return INSTANCE;
    }

    public IInputReader getInputReader(String readerID) throws ExtensionNotFoundException {
        return readerID != null? (IInputReader) getExtensionByID(readerID) : null;
    }

    public IDataParser getDataParser(String parserID) throws ExtensionNotFoundException {
        return parserID != null? (IDataParser) getExtensionByID(parserID) : null;
    }
    public IOutputWriter getOutputWriter(String writerID) throws ExtensionNotFoundException {
        return writerID != null? (IOutputWriter) getExtensionByID(writerID) : null;
    }

    private Object getExtensionByID(String extensionID) throws ExtensionNotFoundException {
        if(availablePlugins.contains(extensionID))
        {
            var extensions =  pluginManager.getExtensions(extensionID);
            if(extensions != null || extensions.size() > 0)
            {
                return extensions.get(0);
            }
        }

        throw new ExtensionNotFoundException("Could not find extension with id " + extensionID, null);
    }

    public List<PluginInformation> getPluginInfos() {
        var pluginInfos = new ArrayList<PluginInformation>();
        for (var extensionID: availablePlugins) {
            var extensions =  pluginManager.getExtensions(extensionID);
            IPluginInformationProvider plugin = (IPluginInformationProvider) extensions.get(0);
            pluginInfos.add(plugin.getPluginInformation());
        }
        return pluginInfos;
    }

    public void stopManager()
    {
        pluginManager.stopPlugins();
        pluginManager.unloadPlugins();
    }


}
