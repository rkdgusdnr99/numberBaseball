import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int putNum = Integer.parseInt(br.readLine());
        Set<Integer> hashSet = new HashSet<>();

        // 사용자가 원하는 숫자 입력 받기
        int[] myNum = new int[3];
        myNum[0] = putNum / 100;
        hashSet.add(myNum[0]);
        myNum[1] = (putNum % 100) / 10;
        hashSet.add(myNum[1]);
        myNum[2] = putNum % 10;
        hashSet.add(myNum[2]);

        // 링크드 리스트에 0~9 숫자 저장해놓고 삭제와 추가 쉽도록 하기
        LinkedList<Integer> numList = new LinkedList<Integer>();
        for (int i = 0; i < 10; i++) {
            numList.add(i);
        }

        // 여기부터 맞추기
        Random random = new Random();
        int attempt = 1; //시도
        int strike = 0; // 스트라이크
        int ball = 0; // 볼
        while(strike < 3){
            //초기화
            strike = 0;
            ball = 0;

            //숫자 3개 랜덤, 중복x 뽑기
            int[] cpuNum = new int[3];
            int num1 = random.nextInt(numList.size());
            cpuNum[0] = numList.get(num1);
            numList.remove(num1);// 뽑고 제거해주면 중복뽑기 x

            int num2 = random.nextInt(numList.size());
            cpuNum[1] = numList.get(num2);
            numList.remove(num2);

            int num3 = random.nextInt(numList.size());
            cpuNum[2] = numList.get(num3);
            numList.remove(num3);

            // 전과 똑같은 숫자를 뽑지 않기 위한 장치
            Set<Integer> reportSet = new HashSet<>();
            int newNum = cpuNum[0] * 100 + cpuNum[1] * 10 + cpuNum[2];
            if (reportSet.contains(newNum)) {
                for (int i = 0; i < 3; i++) {
                    numList.add(cpuNum[i]);
                }
                continue; // 통과하지 못하면 다시 숫자 뽑기.. 개선점 있으면 좋을듯
            }
            reportSet.add(newNum); // 통과하면 숫자 저장 List에 추가
            String formattednewNum = String.format("%03d", newNum); // 3자리 숫자로 포맷

            // 몇번째 시도인지 출력 후, attempt 증가
            System.out.println(attempt + "번째 시도 : " + formattednewNum);

            // 스트라이크, 볼 판별
            for (int i = 0; i < 3; i++) {
                if(myNum[i] == cpuNum[i])
                    strike++;
                else if (hashSet.contains(cpuNum[i]))
                    ball++;
            }

            // 스트라이크와 볼의 개수에 따라 다음에 할 행동 정해줌

            int count = 0;
            // 볼과 스트라이크 합이 3일때 - 경우의 수는 2B1S, 3B 3S

            if (ball + strike == 3) {
                // 반복문이 들어가기 전에 3B일 경우 사고방향을 정해준다.
                // 인간의 경우 3B이 나오면 앞으로 한칸 미는 경우와 뒤로 한칸 미는 경우 중 하나를 찍을 것이기 때문
                int location = random.nextInt(2);
                // threeNum은 다음에 찍는 숫자이다.
                int threeNum = 0;
                String formattedThreeNum = formattednewNum;

                while (strike != 3) {// 3S가 아닐동안 반복
                    if (count > 0)// 만일 처음 들어오는 거라면 이미 처음 반복문을 거쳐서 아래와 같은 형식이 출력 되었을 것.
                        System.out.println(attempt + "번째 시도 : " + formattedThreeNum);
                    int copy[] = cpuNum.clone();// 비교하기 쉽게 미리 복사해놓는다

                    if (strike == 0) { // 3B(0S)일때는 숫자를 밀 방향만 찍는다(이미 찍어 놓음).
                        strike = 0;// 초기화 해줘야 함
                        System.out.println("3B");
                        if (location == 0){
                            cpuNum[0] = copy[1];
                            cpuNum[1] = copy[2];
                            cpuNum[2] = copy[0];
                        } else {
                            cpuNum[0] = copy[2];
                            cpuNum[1] = copy[0];
                            cpuNum[2] = copy[1];
                        }
                    } else { // 2B1S 말고 이외의 경우는 없다.
                        strike = 0;// 초기화 해줘야 함
                        System.out.println("2B1S");
                        // 2B1S 상황에서는 두개만 바꿔준다. 생각해보면 당연하다.
                        // 1/3의 확률로 3S, 나머지 두개도 3B이다.
                        // 이외의 더 확실한 사고법이 있다면 그때 바꾸도록 하자.
                        int n = random.nextInt(3);
                        switch (n) {
                            case 0 :
                                cpuNum[0] = copy[1];
                                cpuNum[1] = copy[0];
                                break;
                            case 1 :
                                cpuNum[1] = copy[2];
                                cpuNum[2] = copy[1];
                                break;
                            case 2 :
                                cpuNum[2] = copy[0];
                                cpuNum[0] = copy[2];
                                break;
                        }
                    }

                    // 스트라이크가 몇개인지 검사한다.
                    // strike만 사용하기 때문에 if (ball + strike == 3) 이후 ball 사용 금지
                    for (int i = 0; i < 3; i++) {
                        if(myNum[i] == cpuNum[i])
                            strike++;
                    }
                    // 이번에 사용한 숫자를 다음에 출력해주기 위해 저장.
                    threeNum = cpuNum[0] * 100 + cpuNum[1] * 10 + cpuNum[2];
                    formattedThreeNum = String.format("%03d", threeNum);
                    count++;
                    attempt++;
                }
                // while 문이 빠져나오면 필연적으로 3S가 되도록 해놓았다.
                if (count > 0)
                    System.out.println(attempt + "번째 시도 : " + formattedThreeNum);
                System.out.println("3S");
            }

            // 만일 아무것도 없다면 해당 숫자는 사용하지 않는다는 것이다. List에서 삭제된 그대로 반복문을 실행한다.
            else if (ball + strike == 0) {
                attempt++;
                System.out.println(ball + "B" + strike + "S");
                continue;
            }

            // ball, strike 1, 2일때 미구현
            // 2S일때 상당히 아쉽다.. 2B이나 1B1S는 고민해보자..
            else {
                attempt++;
                System.out.println(ball + "B" + strike + "S");
                //사용하지 않을꺼면 일단 제거한 숫자들을 List에 반납한다.
                for (int i = 0; i < 3; i++) {
                    numList.add(cpuNum[i]);
                }
            }
        }
        System.out.println(attempt + "번만에 맞히셨습니다.");
        System.out.println("게임을 종료합니다.");
    }
}
