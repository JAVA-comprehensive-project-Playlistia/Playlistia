package com.jems.playlistia.run;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Playlist;
import com.jems.playlistia.Aggregate.Queue;
import com.jems.playlistia.repository.MusicRepository;
import com.jems.playlistia.repository.PlaylistRepository;
import com.jems.playlistia.repository.QueueRepository;
import com.jems.playlistia.service.MusicService;

import javax.script.ScriptContext;
import java.util.Collections;
import java.util.Scanner;

public class Application {

    private static final MusicService musicService = new MusicService();
    private static final MusicRepository musicRepository = new MusicRepository();
    private static final QueueRepository queueRepository = new QueueRepository();
    private static final PlaylistRepository playlistRepository = new PlaylistRepository();

    private static int selectedMusicNo = 0;

    public static void main(String[] args) {
//        Application app = new Application();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== Playlistia =====");
            System.out.println("세상 모든 음악 여기 모여라! 플레이리스티아에 오신 것을 환영합니다.");
            System.out.println("1. 전체 음악 보기");
            System.out.println("2. 재생 목록 보기");
            System.out.println("3. 플레이리스트 보기");
            System.out.println("9. 플레이리스티아 나가기");
            System.out.print("메뉴 선택 : ");
            int choice = scanner.nextInt();

            switch(choice) {
                case 1:
                    musicService.findAllMusic();    // 전체 음악 보여주기
                    selectedMusicNo = chooseMusicNo();  // 사용자로부터 노래 번호 입력 받기
                    Music selectedMusic = musicRepository.findMusicByNo(selectedMusicNo);   // 사용자가 입력한 노래 번호로 노래 찾기

                    if (selectedMusic != null) {
                        playMusic(selectedMusic);   // 선택한 노래 재생
                    }
                    break;
                case 2:
                    musicService.showAllQueue();
                    selectedMusicNo = chooseMusicNo();
                    Queue selectedQueue = queueRepository.findQueueByNo(selectedMusicNo);

                    if (selectedQueue != null) {
                        playQueue(selectedQueue); // 비어있는데 작동함
                    } else {
                        System.out.println("재생목록이 비어있습니다.");
                        return;
                    }
                    break;
                case 3:
                    musicService.findAllPlaylist(); // 전체 플레이리스트 보여주기

                    int selectedPlaylistNo = choosePlaylistNo();    // 사용자로부터 플리 번호 입력 받기

                    // 선택한 플레이리스트의 음악 목록 출력
                    musicService.findAllMusicInPlaylist(selectedPlaylistNo);

                    break;

                case 9:
                    System.out.println("플레이리스티아를 종료합니다. 또 만나요!");
                    return;
                default:
                    System.out.println("메뉴 번호를 잘못 입력했습니다.");
                    break;
            }

        }

    }

    private static int choosePlaylistNo() {
        Scanner sc = new Scanner(System.in);
        int selectedPlaylistNo = 0;
        System.out.println("원하는 플레이리스트 번호를 선택하세요.=====");
        System.out.print("플레이리스트 번호 : ");
        selectedPlaylistNo = sc.nextInt();
        return selectedPlaylistNo;
    }

    // 1. 전체 음악 보기 -> 노래 선택
    // 1. 전체 음악 보기 -> 노래 선택 -> 재생, 중지, 이전(원하는 노래 번호 선택하세요)로 돌아가기

    // 원하는 노래 번호를 입력 받아 반환하는 메소드
    // 이후에도 노래 번호를 입력 받을 경우가 있을까봐 Application에 선언함... 있나?
    private static int chooseMusicNo() {

        Scanner sc = new Scanner(System.in);
        int selectedMusicNo = 0;
        System.out.println("=====원하는 노래 번호를 선택하세요.=====");
        System.out.print("노래 번호 : ");
        selectedMusicNo = sc.nextInt();
        return selectedMusicNo;
    }

    // 원하는 메뉴 번호를 입력 받아 반환하는 메소드(1)
    private static int showMusicMenu1() {
        Scanner sc = new Scanner(System.in);
        int selectedMenuNo = 0;
        System.out.println("=====원하는 메뉴 번호를 선택하세요.=====");
        System.out.println("1. 노래 재생");
        System.out.println("2. 셔플 재생");
        System.out.println("3. 재생목록에 추가");
        System.out.println("4. 플레이리스트에 추가");
        System.out.println("5. 나가기");
        System.out.print("메뉴 번호 : ");
        selectedMenuNo = sc.nextInt();
        return selectedMenuNo;
    }

    private static int showMusicMenu2() {
        Scanner sc = new Scanner(System.in);
        int selectedMenuNo = 0;
        System.out.println("=====원하는 메뉴 번호를 선택하세요.=====");
        System.out.println("1. 노래 멈춤");
        System.out.println("2. 나가기");
        System.out.print("메뉴 번호 : ");
        selectedMenuNo = sc.nextInt();
        return selectedMenuNo;
    }


    // Music 객체를 받아 해당 노래의 이름 가져오기
    private static void playMusic(Music music) {
        System.out.println(music.getName() + " 를 선택했습니다.");
        Scanner sc = new Scanner(System.in);
        // 저장된 노래 번호

        while (true) {
            int menuNo1 = showMusicMenu1();   // 노래 선택 후 추가 메뉴 선택 받기

            if (menuNo1 == 1) {  // 1. 노래 재생
                System.out.println(music.getName() + " 이 재생");

                int menuNo2 = showMusicMenu2();
                if (menuNo2 == 1) { // 1. 노래 멈춤
                    System.out.println(music.getName() + " 의 노래 멈춤");
                    // 다시 재생할지
                    System.out.print("다시 재생하겠습니까? (y/Y) : ");
                    String answer = sc.nextLine();
                    if(answer.equalsIgnoreCase("y")) {
                        System.out.println(music.getName() + " 재생 중~");
                    } else {
                        return;
                    }

                } else if (menuNo2 == 2) { // 2. 나가기
                    return;
                }
                // 노래 멈추고 return 해서 메인 메뉴로
                // 노래 멈추고 다시 재생도 할 수 있게 하려면 어케 하지??

            } else if (menuNo1 == 2) {   // 2. 셔플 재생
                musicRepository.originalMusicList();
                System.out.println(music.getName() + " 이 재생");

                int menuNo2 = showMusicMenu2();
                if (menuNo2 == 1) { // 1. 노래 멈춤
                    System.out.println(music.getName() + " 의 노래 멈춤");
                    // 다시 재생할지
                    System.out.print("다시 재생하겠습니까? (y/Y) : ");
                    String answer = sc.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        System.out.println(music.getName() + " 재생 중~");
                    } else {
                        return;
                    }
                }

            } else if (menuNo1 == 3) {   // 3. 재생 목록에 추가
                // 선택한 음악이 재생목록에 없으면 추가하고싶은데 어떤 식으로 작성해야할지 모르겠음.
                // selectedMusicNo != queue.getMusicNo() 이런식으로 하면 될것같은데
                // 그럼 Queue queue = new Queue();를 생성해야하나...?
                Queue queue = new Queue();

                if(selectedMusicNo != queue.getMusicNo()) {
                    musicService.registQueue(selectedMusicNo);
                    return;
                } else {
                    System.out.println("이미 추가된 노래입니다.");
                    return;
                }

            } else if (menuNo1 == 4) {  // 4. 플레이리스트에 추가
                return;
            } else if (menuNo1 == 5) {
              return;
            } else {
                System.out.println(" 메뉴 번호를 잘못 입력했습니다.");
            }
        }

    }

    private static int showQueueMenu1() {
        Scanner sc = new Scanner(System.in);
        int selectedMenuNo = 0;
        System.out.println("=====원하는 메뉴 번호를 선택하세요.=====");
        System.out.println("1. 이전 노래 재생");
        System.out.println("2. 노래 재생");
        System.out.println("3. 다음 노래 재생");
        System.out.println("4. 셔플 재생");
        System.out.println("5. 노래 삭제");
        System.out.println("6. 순서 변경");
        System.out.println("7. 나가기");
        System.out.print("메뉴 번호 : ");
        selectedMenuNo = sc.nextInt();
        return selectedMenuNo;
    }

    private static int showQueueMenu2() {
        Scanner sc = new Scanner(System.in);
        int selectedMenuNo = 0;
        System.out.println("=====원하는 메뉴 번호를 선택하세요.=====");
        System.out.println("1. 노래 멈춤");
        System.out.println("2. 나가기");
        System.out.print("메뉴 번호 : ");
        selectedMenuNo = sc.nextInt();
        return selectedMenuNo;
    }


    private static void playQueue(Queue queue) {
        System.out.println(queue.getName() + " 를 선택했습니다.");
        Scanner sc = new Scanner(System.in);
        // 저장된 노래 번호

        while (true) {
            int menuNo1 = showQueueMenu1();   // 노래 재생 후 추가 메뉴 선택 받기
            if (menuNo1 == 1) {  // 1. 이전 노래 재생
                queueRepository.backQueue(selectedMusicNo);

                int menuNo2 = showQueueMenu2();
                if (menuNo2 == 1) { // 1. 노래 멈춤
                    System.out.println(queue.getName() + " 의 노래 멈춤");
                    // 다시 재생할지
                    System.out.print("다시 재생하겠습니까? (y/Y) : ");
                    String answer = sc.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        System.out.println(queue.getName() + " 재생 중~");
                    } else {
                        return;
                    }

                } else if (menuNo2 == 2) { // 2. 나가기
                    return;
                }
            } else if (menuNo1 == 2) {  // 2. 노래 재생
                System.out.println(queue.getName() + " 이 재생");

                int menuNo2 = showQueueMenu2();
                if (menuNo2 == 1) { // 1. 노래 멈춤
                    System.out.println(queue.getName() + " 의 노래 멈춤");
                    // 다시 재생할지
                    System.out.print("다시 재생하겠습니까? (y/Y) : ");
                    String answer = sc.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        System.out.println(queue.getName() + " 재생 중~");
                    } else {
                        return;
                    }

                } else if (menuNo2 == 2) { // 2. 나가기
                    return;
                }
            } else if (menuNo1 == 3) {  // 3. 다음 노래 재생
                queueRepository.fowardQueue(selectedMusicNo);

                int menuNo2 = showQueueMenu2();
                if (menuNo2 == 1) { // 1. 노래 멈춤
                    System.out.println(queue.getName() + " 의 노래 멈춤");
                    // 다시 재생할지
                    System.out.print("다시 재생하겠습니까? (y/Y) : ");
                    String answer = sc.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        System.out.println(queue.getName() + " 재생 중~");
                    } else {
                        return;
                    }

                } else if (menuNo2 == 2) { // 2. 나가기
                    return;
                }
            } else if (menuNo1 == 4) {   // 4. 셔플 재생
                queueRepository.originalQueueList();
                System.out.println(queue.getName() + " 이 재생");

                int menuNo2 = showMusicMenu2();
                if (menuNo2 == 1) { // 1. 노래 멈춤
                    System.out.println(queue.getName() + " 의 노래 멈춤");
                    // 다시 재생할지
                    System.out.print("다시 재생하겠습니까? (y/Y) : ");
                    String answer = sc.nextLine();
                    if (answer.equalsIgnoreCase("y")) {
                        System.out.println(queue.getName() + " 재생 중~");
                    } else {
                        return;
                    }
                }
            }else if (menuNo1 == 5) {   // 5. 노래 삭제
                musicService.removeQueueMusic(selectedMusicNo);
                musicService.showAllQueue();
                return;

            } else if (menuNo1 == 6) {  // 6. 순서 변경
                int changeNum = selectedMusicNo - 1; // 인덱스로 바꿔줘기
                queueRepository.changeOrderQueue(changeNum); // 순서변경이 안됨.
                System.out.println("순서변경이 완료되었습니다.");
                return;

            } else if (menuNo1 == 7) {
                return;
            } else {
                System.out.println(" 메뉴 번호를 잘못 입력했습니다.");
            }
        }
    }

}
