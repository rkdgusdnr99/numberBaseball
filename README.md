# numberBaseball
숫자야구 - 컴퓨터가 맞추기

user가 문제를 내고 컴퓨터가 맞추는 방법이다.
컴퓨터는 user가 설정한 숫자를 확인할 수 없다.
각각의 시도마다
"xx번째 시도 : 000
xBxS"
의 형태의 안내문 출력,
마지막에
"x번만에 맞히셨습니다.
게임을 종료합니다."
형태의 안내문 출력
컴퓨터의 사고력(?)을 증진시켜보자!

사람의 역할:
숫자 3개 입력 - hashSet과 배열 이용해서 저장, 배열은 정확한 숫자를 저장, hashSet은 나중에 중복검사 편하게 하기 위함

컴퓨터의 역할:
1. 3S가 나올때 까지 계속해서 반복
2. 숫자 3개 입력(Random), 하나 입력시 1~9 까지 저장 되어있는 List에서 하나씩 빼서 중복 방지 
3. 사람의 기억 역할하는 hashSet 이용해 중복되는 숫자인지 검사 -> 중복되면 attempt 늘리지 않고 다시 숫자 입력
4. 입력한 숫자들 판정해서 ball과 strike 수 저장
5. ball과 strike 합이 3일때 - 3S만들기에 집중, 반복문 따로 돌릴 것!
   - 3B
   - 숫자를 좌측 혹은 우측으로 한칸씩 밀어줌(ex: 123  -> 312 or 231)
   - 이 때 둘중 하나는 무조건 3S이므로 컴퓨터가 할일은 둘중하나 선택(Random)하는 것

   - 2B1S
   - 두 숫자 위치를 바꿔줌-> 그 결과 1/3 확률로 3S, 2/3 확률로 3B
   - 컴퓨터가 할 일은 (1,2), (2,3), (1,3) 중에 선택(Random)하는 것
  
   - 3S
   - 정답!
 6. ball과 strike 합이 0일때 - 어느 숫자도 포함 x 고로 List에 숫자 돌려주지 말고 continue
 7. 그 외- 아직 구현 x, ball과 strike의 합이 2인 경우에는 또 다른 사고과정을 설계하면 더 적은 시도로 답을 찾아낼 듯하다.