package com.jems.playlistia.repository;


import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Queue;

import java.io.*;
import java.util.ArrayList;

public class QueueRepository {
    private final ArrayList<Queue> queueList = new ArrayList<>();
    private static final String FILE_PATH = "src/main/java/com/jems/playlistia/db/Queue.dat";

    public ArrayList<Queue> selectAllQueueMusic() {
        return queueList;
    }
    public QueueRepository() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ArrayList<Queue> queue = new ArrayList<>();
            queue.add(new Queue(1, "Dynamite", "Cause I-I-I'm in the stars tonight...",
                    new String[] {"Jessica Agombar", "David Stewart"},
                    new String[] {"Jessica Agombar", "David Stewart"},
                    "Pop", "BE", "BTS", 199));

            saveQueue(file, queue); // 파일에 queue 객체 저장
        }
        loadQueue(file);
    }

    private void loadQueue(File file) {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                queueList.add((Queue) ois.readObject());    // Obejct -> Queue
            }

        } catch (EOFException e) {
            System.out.println("재생목록을 모두 로딩하였습니다.");
        } catch (IOException | ClassNotFoundException e) {   // ClassNotFoundException 추가!!
            throw new RuntimeException(e);
        }
    }

    private void saveQueue(File file, ArrayList<Queue> queueList) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (Queue q : queueList) {
                oos.writeObject(q);
            }

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
