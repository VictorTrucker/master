  ///////////////////////////////////////
 /// Data Enctiption Function //////////
///////////////////////////////////////

#include <stdio.h>
#include <tchar.h>
#include <conio.h>
#include <time.h>
#include <iostream>
#include <string>

using namespace std;
#pragma warning( disable:4786 )

const int UPPER     = 64; // @
const int LOWER     = 96; // .
const int MIN_SHIFT =  4;

string encript( string& str, bool bEncript = true );


int _tmain( void )
{
  string 
    strIn = "Password",
    strOutE = "",
    strOutD = "";

  srand( (unsigned)time(NULL) );

  strOutE = encript( strIn );
  strOutD = encript( strOutE, false );

  cout << "\n\n\t\b==========================\n";
  cout << "\n\tInitially: " << strIn << endl;
  cout << "\n\tEncripted: " << strOutE << endl;
  cout << "\n\tDecripted: " << strOutD << endl;
  cout << "\n\t\b==========================\n";

  cout << "\n\t\b==========================";
  cout << "\n\tPress any key to exit...";
  cout << "\n\t\b==========================\n\n\t";
  cout.flush();
  _getch();

  return 0;
}


string encript( string& str, bool bEncript /*= true*/ )
{
  if ( str.empty() ) return str;

  int
    nIdx   = 1,
    rnd    = 0,
    nCnt   = 0,
    nShift = 0,
    jump   = 0,
    bump   = 0,
    step   = 0,
    nLen   = str.length(),
    ctl[2] = {UPPER, LOWER};
  char
    ch;
  string
    strSource = str,
    strResult = "";

  if ( bEncript )
  {
    rnd        = rand();
    jump       = ( rnd % 26 ) + 1;
    bump       = ( rnd % 2 );
    nShift     = ( rnd % 5 );
    step       = ( jump % 4 ) + 1;
    ch         = (char)( ctl[bump] + jump );
    strResult  = (char)ch;
    strResult += (char)( ctl[nIdx] + nShift + 1 );
  }
  else
  {
    nIdx      *= -1;
    ch         = str[0];
    ch         = toupper( ch );
    ch        -= ctl[0];
    step       = ( ch % 4 ) + 1;
    ch         = str[1];
    ch         = tolower( ch );
    nShift     = (int)( ( ch - ctl[1] ) - 1 );
    strSource  = str.substr( 2, str.length() );
    nLen      -= 2;
  }

  for ( nCnt = 0; nCnt < nLen; nCnt++, nShift++ )
  {
    ch         = strSource[nCnt];
    ch        += ( nIdx * nShift );
    strResult += (char)ch;
    nShift     = ( nShift == ( MIN_SHIFT + step ) ) ? 0 : nShift;
  }

  return strResult;
}

