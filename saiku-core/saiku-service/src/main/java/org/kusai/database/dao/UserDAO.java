package org.kusai.database.dao;

import org.kusai.database.dto.User;

/**
 * Created by bugg on 01/05/14.
 */
interface UserDAO {
  User getUser(String login);

}
