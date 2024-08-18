package com.jems.playlistia.repository;


import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Queue;
import com.jems.playlistia.stream.MyObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class QueueRepository {
    private final ArrayList<Queue> queueList = new ArrayList<>();
    private static final String FILE_PATH = "src/main/java/com/jems/playlistia/db/Queue.dat";

    public QueueRepository() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ArrayList<Queue> queue = new ArrayList<>();
            queue.add(new Queue(1, "Dynamite", "Cause I-I-I'm in the stars tonight...",
                    new String[]{"Jessica Agombar", "David Stewart"},
                    new String[]{"Jessica Agombar", "David Stewart"},
                    "Pop", "BE", "BTS", 199));
            queue.add(new Queue(2, "사랑에 연습이 있었다면", "사랑에 연습이 있었다면...",
                    new String[] {"Jukjae"},
                    new String[] {"Jukjae"},
                    "Ballad", "이해", "Jukjae", 253));

            queue.add(new Queue(3, "Shape of You", "The club isn't the best place to find a lover...",
                    new String[] {"Ed Sheeran", "Steve Mac", "Johnny McDaid"},
                    new String[] {"Ed Sheeran", "Steve Mac", "Johnny McDaid"},
                    "Pop", "÷ (Divide)", "Ed Sheeran", 233));

            queue.add(new Queue(4, "봄날", "보고 싶다...",
                    new String[]{"RM", "Suga", "Adora", "Hitman Bang", "Arlissa Ruppert"},
                    new String[]{"Pdogg", "RM", "Suga", "Adora"},
                    "K-pop, Ballad", "YOU NEVER WALK ALONE", "BTS", 274));

            queue.add(new Queue(5, "Blinding Lights", "I've been tryna call...",
                    new String[]{"Abel Tesfaye", "Ahmad Balshe", "Jason Quenneville", "Max Martin", "Oscar Holter"},
                    new String[]{"Max Martin", "Oscar Holter", "The Weeknd"},
                    "Synthwave, Pop", "After Hours", "The Weeknd", 200));

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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            for (Queue q : queueList) {
                oos.writeObject(q);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Queue findQueueByNo(int musicNo) {
        for (Queue queue : queueList) {
            // 노래 번호와 일치하는 queue 객체를 반환
            if (queue.getMusicNo() == musicNo) {
                return queue;
            }
        }
        return null;
    }

    // 재생목록 번호 != 노래 번호
    // 재생목록에서는 재생목록 번호만 보이고 노래 번호는 안 보임

    // 재생 목록의 마지막 번호 가져오기
    public int selectLastQueueNo() {

        Queue lastqueue = queueList.get(queueList.size() - 1);
        return lastqueue.getMusicNo();

    }

    public ArrayList<Queue> selectAllQueueMusic() {
        return queueList;
    }

    public int addQueueList(Queue queue) {
        int result = 0;

        try (MyObjectOutputStream moos = new MyObjectOutputStream(new FileOutputStream(FILE_PATH, true))) {
            moos.writeObject(queue);
            queueList.add(queue);
            result = 1;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public int deleteQueueMusic(int selectMusicNo) {
        for (int i = 0; i < queueList.size(); i++) {
            if (queueList.get(i).getMusicNo() == selectMusicNo) {
                queueList.remove(i);

                File file = new File(FILE_PATH);
                saveQueue(file, queueList);

                return 1;
            }
        }
        return 0;
    }

    public void originalQueueList() {
        ArrayList<Queue> queues = new ArrayList<>(queueList);

        suffleQueue();

        queueList.clear();
        queues.addAll(queueList);
    }

    public void suffleQueue() {
        Collections.shuffle(queueList);
        // 셔플된 상태 확인
        for(Queue queue : queueList) {
            System.out.println(queue.toString());
        }
    }

    public void changeOrderQueue(int index) {
        if(index < 0 || index > queueList.size()) {
            System.out.println("입력하신 음악 번호는 없습니다.");
            return;
        }

        int newIndex = index + 2; // 두 칸 뒤로 이동할 인덱스

        if(newIndex > queueList.size()) {
            newIndex = queueList.size() - 1; // 범위를 벗어날 경우 마지막 위치로
        }

        if(index != newIndex) {
            Queue songToChange = queueList.remove(index); // 기존위치에서 요소 제거
            queueList.add(newIndex, songToChange);
        }
        for(Queue queue : queueList) {
            System.out.println(queue.toString());
        }
    }
    // 이전 노래 재생
    public void backQueue(int selectMusicNo) {
        int index = selectMusicNo - 2;
        if(index < 0) {
            System.out.println("이전 노래가 없습니다.");
        } else {
            System.out.println(queueList.get(index).getName() + "의 노래가 재생됩니다.");
        }
    }

    // 다음 노래 재생
    public void fowardQueue(int selectMusicNo) {
        int index = selectMusicNo - 1;
        if(index >= queueList.size() - 1) {
            System.out.println("다음 노래가 없습니다.");
        } else {
            System.out.println(queueList.get(index + 1).getName() + "의 노래가 재생됩니다.");
        }
    }
}