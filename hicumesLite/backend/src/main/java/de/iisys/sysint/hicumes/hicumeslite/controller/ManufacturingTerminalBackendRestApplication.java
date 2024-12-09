package de.iisys.sysint.hicumes.hicumeslite.controller;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@LoginConfig(authMethod = "MP-JWT", realmName = "hicumes")
@ApplicationPath("/hicumesLite")
public class ManufacturingTerminalBackendRestApplication extends Application {
}
