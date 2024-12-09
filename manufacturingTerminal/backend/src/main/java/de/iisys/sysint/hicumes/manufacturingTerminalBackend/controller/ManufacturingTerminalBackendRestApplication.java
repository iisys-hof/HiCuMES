package de.iisys.sysint.hicumes.manufacturingTerminalBackend.controller;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@LoginConfig(authMethod = "MP-JWT", realmName = "hicumes")
@ApplicationPath("/data")
public class ManufacturingTerminalBackendRestApplication extends Application {
}
