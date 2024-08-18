package com.jems.playlistia.run;

import com.jems.playlistia.Aggregate.Music;
import com.jems.playlistia.Aggregate.Playlist;
import com.jems.playlistia.Aggregate.Queue;
import com.jems.playlistia.repository.MusicRepository;
import com.jems.playlistia.repository.PlaylistRepository;
import com.jems.playlistia.repository.QueueRepository;
import com.jems.playlistia.service.MusicService;

import javax.script.ScriptContext;
import java.io.File;
import java.util.Scanner;

public class Application {

    private static final MusicRepository musicRepository = new MusicRepository();
    private static final PlaylistRepository playlistRepository = new PlaylistRepository();
    private static final MusicService musicService = new MusicService();
    private static final QueueRepository queueRepository = new QueueRepository();

    private static int selectedMusicNo = 0;

    public static void main(String[] args) {

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
                    Queue selectedQueue = queueRepository.findQueuebyNo(selectedMusicNo);

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
                    shutdown(); // 종료 시 데이터 저장
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
        System.out.println("2. 재생목록에 추가");
        System.out.println("3. 플레이리스트에 추가");
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

    private static void playMusic(Music music) {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            int menuNo1 = showMusicMenu1(); // 노래 선택 후 추가 메뉴 보이기

            switch (menuNo1) {
                case 1:
                    System.out.println(music.getName() + " 을(를) 재생 중...");
                    int menuNo2 = showMusicMenu2(); // 노래 재생 중 추가 메뉴 보이기

                    if (menuNo2 == 1) {
                        System.out.println(music.getName() + " 재생을 멈춤");
                    } else if (menuNo2 == 2) {
                        exit = true;   // 나가기
                    }
                    break;
                case 2:
                    System.out.println("재생 목록에 추가");
                    System.out.println("selectedMusicNo: " + selectedMusicNo);
                    musicService.registQueue(selectedMusicNo);

                    break;
                case 3:
                    System.out.println("플레이리스트에 추가");
                    int playlistNo = choosePlaylistNo();    // 사용자로부터 플레이리스트 번호 입력받기
                    System.out.println("사용자가 입력한 플레이리스트 번호(playlistNo): " + playlistNo);
                    musicService.musicToPlaylist(selectedMusicNo, playlistNo);

                    break;
                default:
                    System.out.println("메뉴 번호를 잘못 입력했습니다.");
                    break;
            }
        }

    }

    private static void playQueue(Queue selectedQueue) {
    }

    private static void addQueue() {
        Scanner sc = new Scanner(System.in);
        int selectedMusicNo = 0;
        System.out.println("=====원하는 노래 번호를 선택하세요.=====");
        System.out.print("노래 번호 : ");
        selectedMusicNo = sc.nextInt();

        musicService.registQueue(selectedMusicNo);
    }

    public static void shutdown() {
        String FILE_PATH = "src/main/java/com/jems/playlistia/db/Playlist.dat";
        playlistRepository.savePlaylists(new File((FILE_PATH)), playlistRepository.selectAllPlaylist());
        System.out.println("플레이리스트가 저장되었습니다.");
    }
}