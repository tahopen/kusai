package org.kusai.plugin.util;

import org.kusai.datasources.datasource.SaikuDatasource;
import org.kusai.service.datasource.IDatasourceProcessor;
import org.pentaho.platform.engine.core.system.PentahoSessionHolder;

public class PentahoUniqueDatasourceProcessor implements IDatasourceProcessor {

	public SaikuDatasource process(SaikuDatasource ds) {
		String url = ds.getProperties().getProperty("location");
		String sessionName = PentahoSessionHolder.getSession().getName();
		url += ";JdbcConnectionUuid=" + sessionName;
		ds.getProperties().put("location", url);
		return ds;
	}

}
