/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.kusai.service.datasource;

import org.kusai.database.dto.MondrianSchema;
import org.kusai.datasources.connection.RepositoryFile;
import org.kusai.datasources.datasource.SaikuDatasource;
import org.kusai.repository.AclEntry;
import org.kusai.repository.IRepositoryObject;
import org.kusai.service.importer.JujuSource;
import org.kusai.service.user.UserService;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

public interface IDatasourceManager {

  void load();

  void unload();

  SaikuDatasource addDatasource(SaikuDatasource datasource) throws Exception;

  SaikuDatasource setDatasource(SaikuDatasource datasource) throws Exception;

  List<SaikuDatasource> addDatasources(List<SaikuDatasource> datasources);

  boolean removeDatasource(String datasourceName);

  boolean removeSchema(String schemaName);

  Map<String, SaikuDatasource> getDatasources(String[] roles);

  SaikuDatasource getDatasource(String datasourceName);

  SaikuDatasource getDatasource(String datasourceName, boolean refresh);

  void addSchema(String file, String path, String name) throws Exception;

  List<MondrianSchema> getMondrianSchema();

  MondrianSchema getMondrianSchema(String catalog);

  RepositoryFile getFile(String file);

  String getFileData(String file, String username, List<String> roles);

  String getInternalFileData(String file) throws RepositoryException;

  InputStream getBinaryInternalFileData(String file) throws RepositoryException;

  String saveFile(String path, Object content, String user, List<String> roles);

  String removeFile(String path, String user, List<String> roles);

  String moveFile(String source, String target, String user, List<String> roles);

  String saveInternalFile(String path, Object content, String type);

  String saveBinaryInternalFile(String path, InputStream content, String type);

  void removeInternalFile(String filePath);

  List<IRepositoryObject> getFiles(List<String> type, String username, List<String> roles);

  List<IRepositoryObject> getFiles(List<String> type, String username, List<String> roles, String path);

  void createUser(String user);

  void deleteFolder(String folder);

  AclEntry getACL(String object, String username, List<String> roles);

  void setACL(String object, String acl, String username, List<String> roles);

  void setUserService(UserService userService);

  List<org.kusai.database.dto.MondrianSchema> getInternalFilesOfFileType(String type);

  void createFileMixin(String type) throws RepositoryException;

  byte[] exportRepository();

  void restoreRepository(byte[] data);

  boolean hasHomeDirectory(String name);

  void restoreLegacyFiles(byte[] data);

  String getFoodmartschema();

  void setFoodmartschema(String schema);

  void setFoodmartdir(String dir);

  String getFoodmartdir();

  String getDatadir();

  void setDatadir(String dir);

  void setFoodmarturl(String foodmarturl);

  String getFoodmarturl();

  String getEarthquakeUrl();

  String getEarthquakeDir();

  String getEarthquakeSchema();

  void setEarthquakeUrl(String earthquakeUrl);

  void setEarthquakeDir(String earthquakeDir);

  void setEarthquakeSchema(String earthquakeSchema);

  void setExternalPropertiesFile(String file);

  String[] getAvailablePropertiesKeys();

  List<JujuSource> getJujuDatasources();

  String getType();
}
