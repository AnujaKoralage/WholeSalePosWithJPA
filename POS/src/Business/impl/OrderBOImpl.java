package Business.impl;

import Business.custom.OrderBO;
import DAO.DAOFactory;
import DAO.DAOTypes;
import DAO.custom.Impl.CustomerDAOImpl;
import DAO.custom.Impl.ItemDAOImpl;
import DAO.custom.Impl.OrderDetailsDAOImpl;
import DAO.custom.Impl.OrderItemsDAOImpl;
import DTO.ItemDTO;
import DTO.OrderDetailsDTO;
import Entities.Item;
import Entities.OrderItems;
import UtilityClasses.EntityManagerUtil;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderBOImpl implements OrderBO {

public String qtyGetByCode(String itemcode){
    EntityManager session = EntityManagerUtil.getInstance().getManager();
    OrderDetailsDAOImpl orderDetailsDAO = new OrderDetailsDAOImpl();
    orderDetailsDAO.setSession(session);
    return orderDetailsDAO.getQtyByCode(itemcode);
}

    public void insertOrderDetails(OrderDetailsDTO orderDetailsDTO) throws Exception {

    EntityManager entityManager = EntityManagerUtil.getInstance().getManager();

        entityManager.getTransaction().begin();
        OrderDetailsDAOImpl dao = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);
        dao.setSession(entityManager);
        //dao.save(new OrderDetails(orderDetailsDTO.getOrderid(),orderDetailsDTO.getCusid(),orderDetailsDTO.getOrderdate()));
        entityManager.getTransaction().commit();

    }

    public void insertOrderItems(OrderDetailsDTO orderDetailsDTO) throws Exception {
    EntityManager entityManager = EntityManagerUtil.getInstance().getManager();
        entityManager.getTransaction().begin();
        OrderItemsDAOImpl dao = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_ITEMS);
        dao.setSession(entityManager);
        dao.save(new OrderItems(orderDetailsDTO.getOrderid(),orderDetailsDTO.getCusid(),orderDetailsDTO.getOrderdate()));
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    public void updateItemQty(String qtyOnHand, String itemcode){
        EntityManager session = EntityManagerUtil.getInstance().getManager();
            session.getTransaction().begin();
            OrderItemsDAOImpl dao = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_ITEMS);
            dao.setSession(session);
            dao.updateQtyOnHand(qtyOnHand,itemcode);
            session.getTransaction().commit();

    }

    public List<ItemDTO> allItems() throws Exception {
        EntityManager entityManager = EntityManagerUtil.getInstance().getManager();
        ItemDAOImpl dao = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
        dao.setSession(entityManager);

        return dao.findAll().stream().map(new Function<Item, ItemDTO>() {
            @Override
            public ItemDTO apply(Item item) {
                return new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice());
            }
        }).collect(Collectors.toList());

        /*List<Item> all = dao.findAll();

        List<ItemDTO> list  = new ArrayList<>();
        for (Item item:all) {
            list.add(new ItemDTO(item.getCode(),item.getDescription(),item.getQty(),item.getPrice()));
        }

        return list;*/
    }

    public ObservableList getAllCustomerId(ObservableList list) throws SQLException {
        EntityManager entityManager = EntityManagerUtil.getInstance().getManager();
        CustomerDAOImpl dao = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
        dao.setEntityManager(entityManager);
        ObservableList id = dao.getId(list);

        return id;
    }


}
