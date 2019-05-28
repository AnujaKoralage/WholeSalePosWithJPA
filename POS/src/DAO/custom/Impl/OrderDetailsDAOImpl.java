package DAO.custom.Impl;

import DAO.DAO.custom.OrderDetailsDAO;
import Entities.OrderDetails;
import UtilityClasses.DBConnection;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    private EntityManager session;

    public void save(OrderDetails orderdetails) throws SQLException {
        session.persist(orderdetails);
    }

    public void update(OrderDetails orderdetails) throws SQLException {
        session.merge(orderdetails);
    }

    public void delete(String orderid) throws SQLException {
        OrderDetails orderDetails = session.getReference(OrderDetails.class,orderid);
        session.remove(orderDetails);
    }

    public List<OrderDetails> findAll() throws SQLException {
        List<OrderDetails> list = session.createQuery("from Entities.OrderDetails", OrderDetails.class).getResultList();
        return list;
    }

    public OrderDetails find(String orderid) throws SQLException {
        OrderDetails orderDetails = (OrderDetails) session.createNativeQuery("SELECT * FROM orderdetails WHERE orderid=?", OrderDetails.class).setParameter(1,orderid).getSingleResult();
        return orderDetails;

    }

    @Override
    public String getQtyByCode(String itemcode) {
        String s = (String) session.createNativeQuery("SELECT qty FROM item WHERE code=?", String.class).setParameter(1, itemcode).getSingleResult();
        return s;
    }

    public EntityManager getSession() {
        return session;
    }

    public void setSession(EntityManager session) {
        this.session = session;
    }
}
