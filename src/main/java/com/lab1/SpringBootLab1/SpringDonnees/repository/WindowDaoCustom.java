package com.lab1.SpringBootLab1.SpringDonnees.repository;

import com.lab1.SpringBootLab1.SpringDonnees.models.WindowEntity;

import java.util.List;

public interface WindowDaoCustom {
    List<WindowEntity> findRoomsWithOpenWindows(Long roomId);
    List<WindowEntity> findWindowByRoomName(String roomName);
    int deleteWindowByRoomId(Long roomId);
    void closeOrOpenAllWindowsByRoomId(Long roomId, boolean isOpen);
}