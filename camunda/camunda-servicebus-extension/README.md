# ServiceBus Plugin

Hazelcast Plugin and the BPMN Listener Plugin

### Activate the BPMN Parse Listener Plugin

Both plugins can be activated in the `camunda.cfg.xml`:

``` xml
<!-- activate bpmn parse listener as process engine plugin -->
<property name="processEnginePlugins">
  <list>
    <bean class="de.iisys.sysint.hicumes.HicumesServicebusPlugin" />
  </list>
</property>
```

And in `bpmn-platform.xml`

``` xml
<plugin>
    <class>de.iisys.sysint.hicumes.HicumesServicebusPlugin</class>
</plugin>
```

Copy `camunda-servicebus-extension-7.15.0-SNAPSHOT-jar-with-dependencies.jar` file
to `$CAMUNDA_HOME/server/apache-xxx/lib`
