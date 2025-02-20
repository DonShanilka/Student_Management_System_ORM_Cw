package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

public interface UserDAO extends CrudDAO<User> {
    User searchByName(String userNameText);

    public void initializeDefaultUser();

    boolean updatePassword(String newPassword, String userName);
}
