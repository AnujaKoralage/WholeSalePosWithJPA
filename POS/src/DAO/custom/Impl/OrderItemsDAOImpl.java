package DAO.custom.Impl;

import DAO.DAO.custom.OrderItemsDAO;
import Entities.OrderItems;
import Entities.OrderItemsPK;
import UtilityClasses.DBConnection;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsDAOImpl implements OrderItemsDAO {

    private EntityManager session;
    public void save(OrderItems orderitems) throws SQLException {
        session.persist(orderitems);
    }

    public void update(OrderItems orderitems) throws SQLException {
        session.merge(orderitems);
    }

    public  void delete(OrderItemsPK orderItemsPK) throws SQLException {
        OrderItemsPK load = session.getReference(OrderItemsPK.class, orderItemsPK);
    }

    public List<OrderItems> findAll() throws SQLException {
        List<OrderItems> list = session.createQuery("from Entities.OrderItems", OrderItems.class).getResultList();
        return list;
    }

    public OrderItems find(OrderItemsPK orderItemsPK) throws SQLException {
        OrderItems orderItems = (OrderItems) session.createNativeQuery("SELECT * FROM orderitems WHERE orderid=? AND itemCode=?", OrderItems.class)
                .setParameter(1, orderItemsPK.getOrderid()).setParameter(2, orderItemsPK.getItemcode()).getSingleResult();
        return orderItems;

    }

    @Override
    public void updateQtyOnHand(String itemcode,String qtyOnHand) {
        session.createNativeQuery("UPDATE item SET qty=? WHERE code=?").setParameter(1,qtyOnHand)
                .setParameter(2,itemcode).executeUpdate();
    }

    public EntityManager getSession() {
        return session;
    }

    public void setSession(EntityManager session) {
        this.session = session;
    }
}
