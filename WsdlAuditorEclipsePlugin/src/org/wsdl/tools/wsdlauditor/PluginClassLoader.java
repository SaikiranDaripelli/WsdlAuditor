package org.wsdl.tools.wsdlauditor;

import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {

	public PluginClassLoader(URL[] urls) {
		super(urls,Thread.currentThread().getContextClassLoader());
	}

	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}

}
