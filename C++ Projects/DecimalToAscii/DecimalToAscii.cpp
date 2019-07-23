  //////////////////////////////////////////
 /// DecimalToAscii ///////////////////////
//////////////////////////////////////////
#include <conio.h>
#include <iostream>
#include <math.h>

using namespace std;
#pragma warning( disable:4786 )
#include <string>


int main()
{
  int
    nLup    = 0,
    nValue  = 2,
    nBits   = 8, // 8 = 0-7 // 16 = 0-15 ...
    nOutput = 0;
  double
    dPower  = (double)( nBits - 1 ); // 7.0 = 8bit // 15.0 = 16bit ...
  string
    strConv = "00110001"; // 49


  for ( nLup = 0; nLup < nBits; nLup++ )
  {
    nValue = (int)pow( 2.0, dPower );
    int nWhich = (int)( 7.0 - dPower );

    if ( strConv.c_str()[nWhich] == '1' )
    {
      nOutput += nValue;
    }

    dPower--;
  }

  cout << "\n\n " << nOutput << endl;
  cout << "\nPress any key to continue ... ";
  flush( cout );
  _getch();

  return nOutput;
}
/*
int main()
{
  int
    nLup    = 0,
    nValue  = 2,
    nBits   = 16, // 8 = 0-7 // 16 = 0-15 ...
    nConv   = 49;
  double
    dPower  = 15.0; // 7.0 = 8bit // 15.0 = 16bit ...
  string
    strOutput = "";


  for (nLup = 0; nLup < nBits; nLup++)
  {
    nValue = (int)pow(2.0, dPower);
    dPower--;

    if (nConv < nValue)
    {
      strOutput += "0";
    }
    else
    {
      strOutput += "1";
      nConv -= nValue;
    }
  }

  cout << "\n\n " << strOutput << endl;
  cout << "\nPress any key to exit ... ";

  flush( cout );
  _getch();

  return 0;
}
*/
