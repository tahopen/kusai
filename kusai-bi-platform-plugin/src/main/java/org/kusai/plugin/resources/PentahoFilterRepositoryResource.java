package org.kusai.plugin.resources;

import org.kusai.plugin.util.PluginConfig;
import org.kusai.web.rest.resources.FilterRepositoryResource;
import org.pentaho.platform.api.engine.IPluginManager;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.plugin.services.pluginmgr.PluginClassLoader;

import java.io.File;

import javax.ws.rs.Path;

@Path("/saiku/api/{username}/filters")
public class PentahoFilterRepositoryResource extends FilterRepositoryResource {
	
	public void setPath(String path) {
		final IPluginManager pluginManager = (IPluginManager) PentahoSystem.get(IPluginManager.class, PentahoSessionHolder.getSession());
		final PluginClassLoader pluginClassloader = (PluginClassLoader)pluginManager.getClassLoader(PluginConfig.PLUGIN_NAME);
		File pluginDir = pluginClassloader.getPluginDir();
		String absolute = "file:" +pluginDir.getAbsolutePath();
		if (!absolute.endsWith("" + File.separatorChar)) {
			 absolute += File.separatorChar;
		}
		absolute += path;
		System.out.println("Using tag repository path: " + absolute);
		//super.setPath(absolute);
	}

}
