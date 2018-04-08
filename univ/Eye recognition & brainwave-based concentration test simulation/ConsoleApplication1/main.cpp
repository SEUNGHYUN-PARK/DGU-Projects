#include "mindwave.h"
#include "eyedecting.h"
#include "thread"
#include "time.h"

void main() {
	VIDEO video;
	MINDWAVE mw;
	thread t1(&mw.MEDD_ATTD);
	video.videoPlay();
	t1.join();

	//printf("%d\n", mw);
	system("pause");
}