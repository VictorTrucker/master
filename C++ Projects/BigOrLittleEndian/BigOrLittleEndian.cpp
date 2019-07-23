
  /////////////////////////////////////////////
 /// Big Endian or Little Endian /////////////
/////////////////////////////////////////////

#include <iostream>
#include <conio.h>

using namespace std;
#pragma warning( disable:4786 )

int main()
{
  int
    nVal = 0x00000001;
  char
    *ch  = (char*)&nVal;
  char
    *szOutput = (ch[0] == 1) ? "Little" : "Big";

  cout << szOutput << " Endian" << endl;
  cout << "\nPress any key to exit ... ";

  flush( cout );
  _getch();

  return 0;
}

