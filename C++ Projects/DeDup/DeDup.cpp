
  /////////////////////////////////////////////////
 /// De-Dup a string /////////////////////////////
/////////////////////////////////////////////////

#include <iostream>
#include <conio.h>
#include <string>

using namespace std;
#pragma warning( disable:4786 )

int main()
{
  string
    strSrc = "AAA BBB CCC ABC",
    strTgt;
  int
    nPos = 0,
    nCnt = strSrc.length();

  for ( int nLup = 0; nLup < nCnt; nLup++ )
  {
    if ( strSrc[nLup] != ' ' ) // remove if spaces are to be deduped
    {
      nPos = strTgt.find( strSrc[nLup] );

      if ( nPos == string::npos )
      {
        strTgt += strSrc[nLup];
      }
    }
    else
    {
      strTgt += strSrc[nLup];
    }
  }

  cout << strTgt << endl;
  cout << "\nPress any key to exit ... ";

  flush( cout );
  _getch();

  return 0;
}

