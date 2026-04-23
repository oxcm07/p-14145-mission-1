package com.back;

import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);
    int lastId = 0;
    WiseSaying[] wiseSayings = new WiseSaying[100];
    int wiseSayingsLastIndex = -1;

    void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                actionWrite();
            } else if (cmd.equals("목록")) {
                actionList();
            } else if (cmd.startsWith("삭제")) {
                actionDelete(cmd);
            } else if (cmd.startsWith("수정")) {
                actionChange(cmd);
            }
        }
        sc.close();
    }

    //action start
    void actionList() {
        System.out.println("번호 / 작가 / 내용");
        System.out.println("----------------");

        WiseSaying[] forListWiseSayings = findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.println(wiseSaying.id + " / " + wiseSaying.author + " / " + wiseSaying.content);
        }
    }

    void actionWrite() {
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();

        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        WiseSaying wiseSaying = write(content, author);

        System.out.println(wiseSaying.id + "번 명언이 등록되었습니다.");
    }

    void actionDelete(String cmd) {
        String[] cmdBits = cmd.split("=", 2);

        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[1]);

        int deletedIndex = delete(id);

        if (deletedIndex == -1) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.println("%d번 명언이 제외되었습니다".formatted(id));
    }

    void actionChange(String cmd) {
        String[] cmdBits = cmd.split("=", 2);
        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }
        int id = Integer.parseInt(cmdBits[1]);
        WiseSaying wiseSaying = findId(id);

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }
        System.out.printf("명언(기존) : %s\n", wiseSaying.content);
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.author);
        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        change(wiseSaying, content, author);
    }
    //action end

    //logic start
    WiseSaying write(String content, String author) {
        int id = ++lastId;

        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.id = id;
        wiseSaying.author = author;
        wiseSaying.content = content;

        wiseSayings[++wiseSayingsLastIndex] = wiseSaying;

        return wiseSaying;
    }

    int getSize() {
        return wiseSayingsLastIndex + 1;
    }

    WiseSaying[] findForList() {
        WiseSaying[] forListWiseSayings = new WiseSaying[getSize()];

        int forListWiseSayingsIndex = -1;

        for (int i = wiseSayingsLastIndex; i >= 0; i--) {
            forListWiseSayings[++forListWiseSayingsIndex] = wiseSayings[i];
        }

        return forListWiseSayings;
    }

    WiseSaying findId(int id) {
        int index = findIndexId(id);
        if (index == -1) {
            return null;
        }
        return wiseSayings[index];
    }

    int findIndexId(int id) {
        for (int i = 0; i <= wiseSayingsLastIndex; i++) {
            if (wiseSayings[i].id == id) {
                return i;
            }
        }
        return -1;
    }

    void change(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.content = content;
        wiseSaying.author = author;
    }

    int delete(int id) {
        int deleteIndex = findIndexId(id);

        if (deleteIndex == -1) {
            return deleteIndex;
        }
        for (int i = deleteIndex + 1; i <= wiseSayingsLastIndex; i++) {
            wiseSayings[i - 1] = wiseSayings[i];
        }
        wiseSayings[wiseSayingsLastIndex] = null;
        wiseSayingsLastIndex--;
        return deleteIndex;
    }
    //logic end
}