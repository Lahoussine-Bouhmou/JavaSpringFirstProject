package com.lab1.SpringBootLab1.SpringDonnees.repository;

import com.lab1.SpringBootLab1.SpringDonnees.models.WindowEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class WindowDaoImpl implements WindowDaoCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<WindowEntity> findRoomsWithOpenWindows(Long id) {
        String jpql = "SELECT w FROM WindowEntity w INNER JOIN w.windowStatus s " +
                "WHERE w.room.id = :id AND s.sensorValue > 0.0 ORDER BY w.name";
        return em.createQuery(jpql, WindowEntity.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<WindowEntity> findWindowByRoomName(String name) {
        String jpql = "SELECT w FROM WindowEntity w INNER JOIN w.room r WHERE r.name = :name";
        return em.createQuery(jpql, WindowEntity.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public int deleteWindowByRoomId(Long roomId) {
        String jpql = "DELETE FROM WindowEntity w WHERE w.room.id = :id";
        return em.createQuery(jpql)
                .setParameter("id", roomId)
                .executeUpdate();
    }

    @Override
    public void closeOrOpenAllWindowsByRoomId(Long roomId, boolean isOpen) {
        Double openValue = isOpen ? 1.0 : 0.0;
        String jpql = "UPDATE SensorEntity s SET s.sensorValue = :openValue " +
                "WHERE s.id IN (" +
                "   SELECT w.windowStatus.id FROM WindowEntity w WHERE w.room.id = :roomId" +
                ")";
        em.createQuery(jpql)
                .setParameter("openValue", openValue)
                .setParameter("roomId", roomId)
                .executeUpdate();
        em.flush();
        em.clear(); // Clear the persistence context
    }



}
