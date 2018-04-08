#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#define MAX_LINE 80 //명령최대 입력 수 80자

int main(void)
{
	char *argsStack[MAX_LINE / 2 + 1]={'0',}; // 히스토리를 위한 스택 선언
	char *args[MAX_LINE / 2 + 1] = {'0',}; // 커멘드 라인 매개변수
	int should_run = 1; // 프로그램 종료시 플래그값
	char temp[MAX_LINE]; // 문자열을 복사할 임시변수 선언.
	int tempstrlen = 0; //  글자갯수를 0으로 초기화하고 루프를 돌면서 글자수 카운트 체크하고
						//  포인터배열을 동적할당하는데 쓰는 임시변수
	int i;
	int chunk = 0; // 단어 단위로 args배열에 동적할당해주기 위한 인덱스변수
	int cursor = 0; // 커서자체는 copyarr의 커서역할 
	int init =0; // 명령어 스택을 초기화시켜줌과 동시에 인덱싱
	int chunkcnt=1; // test용 변수 청크단위로 끊어졌는지 확인하기위해 임의로 설정
	char copyarr[MAX_LINE]; // 피매개체
	int waitflag=0;
	pid_t pid;

	memset(copyarr, NULL, MAX_LINE); // 포인터배열의 각 동적할당한 배열에 입력하기위한 피매개체(복사되는 대상)을 초기화


	while (should_run)
	{
		printf("osh>");
		fflush(stdout); // 버퍼 초기화
		fgets(temp, MAX_LINE, stdin); // scanf는 띄어쓰기를 입력받을수없어서 temp의 크기만큼의 입력값을 fgets를 이용하여 표준입력을 통해 입력

		argsStack[init] = (char *)malloc(sizeof(strlen(temp))); // 히스토리 스택 쌓아주기
		strcpy(argsStack[init],temp);
		init++;
		// 스택이 채워지는지 확인하는 loop
		if (strcmp(argsStack[init-1],"history\n")==0)
		{
			for(int z=init-1;z>=0;z--)
			{
				printf("%d : %s",z, argsStack[z]);
			}
		}
		else if(strcmp(argsStack[init-1],"!!\n")==0)
		{
			if(init-1<=0)
			{
				printf("No commands in history\n");
				continue;
			}

			else
				strcpy(temp,argsStack[init-2]);
		}	
		

//////////////////////////////////////////////////////////// start : args배열을 입력받은 데이터의 청크의 크기에 맞게 동적할당
		for (i = 0; i<MAX_LINE; i++) //arg[]배열의 동적할당을 위한 temp 탐색
		{
			if (temp[i] == NULL) // 문장의 마무리에 도달했을 때,
			{
				args[chunk] = (char *)malloc(sizeof(char)); // args배열의 마지막은 NULL로 마무리
				chunkcnt++;
				break; // 문장의 마무리에 도달하였기에 무의미한 루프 순환 방지
			}
			else if (temp[i] == ' ' || temp[i] == '\n')// 문장 탐색 중에 띄어쓰기나 개행문자를 발견 했을 때
			{
				args[chunk] = (char*)malloc(sizeof(char)*tempstrlen);
				chunk++; //다음 chunk를 담기 위한 인덱싱 증가
				chunkcnt++;
				tempstrlen = 0; // 띄어쓰기 다음부터 다시 글자탐색을 위한 tempstrlen 초기화
			}
			else // NULL과 띄어쓰기,개행을 제외한 나머지
			{
				tempstrlen++; // 동적할당을위해 temp배열의 띄어쓰기문자가 나오기 전까지 스펠링 카운팅
			}
		}

		chunk = 0; // 배열의 공간만큼 동적할당을 완료하였으니, 데이터 삽입을 위한 초기화
//////////////////////////////////////////////////////////// end :  args배열을 입력받은 데이터의 청크의 크기에 맞게 동적할당

//////////////////////////////////////////////////////////// start : 동적할당 해준 args배열에 입력받은 데이터를 청크단위로 입력
		for (int j = 0; j < MAX_LINE; j++) // 데이터 삽입
		{
			
			if (temp[j] == NULL)
			{
				args[chunk] = NULL;
				break;
			}
			else if (temp[j] == ' ' || temp[j] == '\n')
			{

				strcpy(args[chunk], copyarr);
				if(args[chunk]=='&')
				{
					waitflag=1;
				}
				memset(copyarr, NULL, MAX_LINE);
				chunk++;
				cursor = 0;
			}
			else
			{
				copyarr[cursor] = temp[j];
				cursor++;
			}
		}
//////////////////////////////////////////////////////////// end : 동적할당 해준 args배열에 입력받은 데이터를 청크단위로 입력

		if(strcmp(args[0],"exit")==0)
		{
			should_run=0;
			continue;
		}
		else if(strcmp(args[0],"history")==0)
		{
			continue;
		}
//////////////////////////////////////////////////////////// start : 입력한 값이 제대로 분할되는지 test하기위한 루프(문제에는 포함이 안되어있어 주석처리함) 
		/*
		for (int k = 0; k < chunkcnt-1; k++)
		{
			printf("%s\n", args[k]);
		}

		chunkcnt=1;
		*/
//////////////////////////////////////////////////////////// end : 입력한 값이 제대로 분할되는지 test하기위한 루프(문제에는 포함이 안되어있어 주석처리함) 

		pid = fork();

		if(pid<0)
		{
			printf("Fork Failed.\n");
			exit(1);
		}
		else if(pid == 0)
		{
			if(execvp(args[0],args)==-1)
			{
				printf("Error executeing command\n");
			}
		}
		else
		{
			if(waitflag==0)
			{
				wait(NULL);
				printf("\n");
			}
		}
	}
	return 0;
}
