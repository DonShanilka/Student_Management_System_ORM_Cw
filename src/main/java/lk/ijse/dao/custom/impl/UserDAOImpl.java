package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;
import lk.ijse.util.PasswordUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean save(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object user = session.save(entity);
        System.out.println(user);

        if (user != null) {
            transaction.commit();
            session.close();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("delete from User where userName = ?1");
            query.setParameter(1, id);
            boolean isDelete = query.executeUpdate() > 0;
            transaction.commit();
            session.close();
            return true;

        } catch (HibernateException e) {
            new Alert(Alert.AlertType.CONFIRMATION,e.getMessage()).show();
            return false;
        }

    }

    @Override
    public List<User> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("FROM User ");

            List<User> users = query.list();
            session.close();
            return users;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public User searchByName(String userNameText) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("FROM User WHERE userName=:userName");
            query.setParameter("userName", userNameText);
            User user = (User) query.uniqueResult();
            if (user != null) {
                return user;
            }else{

                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }

    }

    public void initializeDefaultUser(){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            NativeQuery nativeQuery = session.createNativeQuery("SHOW TABLES LIKE 'user'");

            boolean isUserTableExist = nativeQuery.uniqueResult() == null;

            if (isUserTableExist) {
                String hasedPass = PasswordUtil.hashPassword("1111");
                //session.createNativeQuery("CREATE TABLE user(userName)")
                User user = new User("admin",hasedPass,"Admin","admin@gmail.com");
                session.save(user);

            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public boolean updatePassword(String newPassword, String userName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {

            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE User SET password=:userPassword WHERE userName=:username");

            query.setParameter("username", userName);
            query.setParameter("userPassword", newPassword);
            boolean isUpdatedPass = query.executeUpdate() > 0;
            if (isUpdatedPass) {
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }

    }
}
