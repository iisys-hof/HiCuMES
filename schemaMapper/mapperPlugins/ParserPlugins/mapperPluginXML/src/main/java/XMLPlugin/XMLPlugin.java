package XMLPlugin;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

public class XMLPlugin extends Plugin {

    public XMLPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("XMLPlugin.start()");
    }

    @Override
    public void stop() {
        System.out.println("XMLPlugin.stop()");
    }

    @Override
    public void delete() {
        System.out.println("XMLPlugin.delete()");
    }
}
