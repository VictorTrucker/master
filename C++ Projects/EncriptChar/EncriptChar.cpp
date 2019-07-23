        //////////////////////////////////////////////////
       //  Program: Encript by Char                    //
      //    Author: Victor Trucker                    //
     //       Date: 06/12/2015                       //
    //   Copyright: 2015 all rights reserved        //
   //  Description: An implementation of another   //
  //                encription algorythm which    //
 //                 encripts by characters       //
//////////////////////////////////////////////////

#include <stdlib.h>
#include <tchar.h>
#include <time.h>
#include <math.h>
#include <iostream>
#include <io.h>
#include <conio.h>
#include <fstream>
#include <vector>
#include <string>

using namespace std;
#pragma warning( disable:4786 )

typedef vector<string> _vecS;
typedef _vecS::iterator _vecSIt;

const int UBOUND    =  3;
const int BITS_8    =  8;
const int BITS_16   = 16;
const int SEGS      = 18;

const double POW_8  =  7.0;
const double POW_16 = 15.0;

const string ZERO_SEGMENTS[SEGS] = { "ish", "rea", "blu", "mer", "for", "ply",
                                     "ood", "ist", "ent", "har", "ing", "ity",
                                     "tem", "bar", "kel", "mod", "les", "win" };

const string ONE_SEGMENTS[SEGS]  = { "bra", "dem", "gre", "jol", "kem", "que",
                                     "nso", "sta", "abl", "fre", "eet", "lem",
                                     "sev", "fou", "ate", "ism", "pre", "wri" };

const string INVALID_CODE  = "\n\n\n\n\t  >>  INVALID CODE  <<\n\tPress any key to continue...";
const string FILE_ERROR_1  = "\n\n\n\t>> Unable to open ";
const string FILE_ERROR_2  = "\n\n\t  >> Failed creating file lists! <<\n\n";
const string NO_FILES      = "\n\nNo Files in This Directory!\n\n";
const string SELECTION     = "\n\t  >> Your Selection: ";
const string MAINMENU      = "\n\n\t  >> Main Menu <<\n\n";
const string FILEMENU      = "\n\n\t  >> Files to %s <<\n\n";
const string MENU_LINE     = "\t %d = %s\n";
const string BACK_TO_MAIN  = "\t . = Main Menu\n";
const string CRLF          = "00001010";
const string NEW_LINE      = "\n\0\0";
const string NEW_LINES     = " <<\n\n";
const string NIX           = "";
const string ENC           = ".enc\0";
const string DEC           = ".dec\0";

const string EXT[UBOUND]        = { "None",
                                    ".txt",
                                    ".enc" };

const string OPERATION[UBOUND]  = { "None",
                                    "Encript",
                                    "Decript" };

const string MAIN_MENU[UBOUND]  = { "\t 1 = Encript a File\n",
                                    "\t 2 = Decript a File\n",
                                    "\t . = Exit Program\n" };

string decimalToAscii( int nConv );
int asciiToDecimal( string & strConv );
void split( string & strText, const int nSize, _vecS & words );
char doMenu( _vecS & menu, string & strHeader );
bool loadFileMap( _vecS & vecText, _vecS & vedEncript );
void encriptFile( _vecS & vFiles, char chSelection, bool bEncript = true );
void saveFile( _vecS & vecFile, string & strFileName, bool bEncript );


/////////////
int main()//
{//////////
  bool 
    bEncripting = true;
  char
    ch[256],
    chSelect1,
    chSelect2;
  _vecS
    mainMenu,
    txtFiles,
    encFiles;
  string
    strHeader = "";

  srand( (unsigned)time(NULL) );

  for ( int i = 0; i < UBOUND; i++ )
  {
    mainMenu.push_back( MAIN_MENU[i] );
  }

  do
  {
    if ( !loadFileMap( txtFiles, encFiles ) )
    {
      cerr << FILE_ERROR_2;
      _getch();
      exit( 1 );
    }

    strHeader = MAINMENU;

    system( "cls" );
    chSelect1 = doMenu( mainMenu, strHeader );
    system( "cls" );

    bEncripting = ( chSelect1 == '1' ) ? true : false;
    sprintf_s( ch, 256, FILEMENU.c_str(), OPERATION[chSelect1 - 48].c_str() );
    strHeader = ch;

    switch ( chSelect1 )
    {
    case '.': // Exit Program
      break;

    case '1': // Encript File
      chSelect2 = doMenu( txtFiles, strHeader );
      if ( chSelect2 != '.' )
        encriptFile( txtFiles, chSelect2, bEncripting );
      break;

    case '2': // Decript File
      chSelect2 = doMenu( encFiles, strHeader );
      if ( chSelect2 != '.' )
        encriptFile( encFiles, chSelect2, bEncripting );
      break;

    default:
      system( "cls" );
      cout << INVALID_CODE;
      flush( cout );
      _getch();
      break;
    };
  }
  while ( chSelect1 != '.' );

  return 0;
}


/////////////////////////////////////
string decimalToAscii( int nConv )//
{//////////////////////////////////
  int
    nLup    = 0,
    nValue  = 0,
    nBits   = BITS_8; // 8 = 0-7 // 16 = 0-15 ...
  double
    dPower  = POW_8; // 7.0 = 8bit // 15.0 = 16bit ...
  string
    strOutput = "";


  for ( nLup = 0; nLup < nBits; nLup++ )
  {
    nValue = (int)pow( 2.0, dPower );
    dPower--;

    if ( nConv < nValue )
    {
      strOutput += "0";
    }
    else
    {
      strOutput += "1";
      nConv -= nValue;
    }
  }

  return strOutput;
}


/////////////////////////////////////////
int asciiToDecimal( string & strConv )//
{//////////////////////////////////////
  int
    nLup    = 0,
    nValue  = 0,
    nBits   = BITS_8, // 8 = 0-7 // 16 = 0-15 ...
    nOutput = 0;
  double
    dPower  = (double)( nBits - 1 ); // 7.0 = 8bit // 15.0 = 16bit ...


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

  return nOutput;
}


/////////////////////////////////////////////////////////////////
void split( string & strText, const int nSize, _vecS & words )//
{//////////////////////////////////////////////////////////////
  int
    nTextLen = strText.length(),
    nStart   = 0;

  while ( ( nStart >= 0 ) && ( nStart < nTextLen ) )
  {
    int
      stop = nStart + nSize;

    if ( ( stop < 0 ) || ( stop > nTextLen ) )
    {
      stop = nTextLen;
    }

    string
      str = strText.substr( nStart, nSize );

    words.push_back( str );
    nStart += nSize;
  } 
}


//////////////////////////////////////////////////
char doMenu( _vecS & menu, string & strHeader )//
{///////////////////////////////////////////////
  cout << strHeader;

  for ( _vecSIt _sit = menu.begin(); _sit != menu.end() ; _sit++ )
  {
    cout << (*_sit);
  }

  cout << SELECTION;

  flush( cout );
  return _getch();
}


///////////////////////////////////////////////////////////
bool loadFileMap( _vecS & vecFiles, _vecS & vecEncript )//
{////////////////////////////////////////////////////////
  bool
    bRet  = true;
  int
    nCnt1 = 0,
    nCnt2 = 0;
  char
    ch[256];
  long
    hFile;
  _finddata_t //struct
    fileList;
  string
    strFileName = "";


  if ( ( hFile = _findfirst( "*.*", &fileList ) ) == -1L )
  {
    system( "cls" );
    cerr << NO_FILES;
    exit( 1 );
  }

  vecFiles.clear();
  vecEncript.clear();

  do
  {
    strFileName = fileList.name;
    _strlwr_s( (char*)strFileName.c_str(), strFileName.length() + 1 );

    if ( strstr( fileList.name, EXT[1].c_str() ) != NULL )
    {
      nCnt1++;
      sprintf_s( ch, 256, MENU_LINE.c_str(), nCnt1, fileList.name );
      const string str = ch;
      vecFiles.push_back( str );
    }
    else if( strstr( fileList.name, EXT[2].c_str() ) != NULL )
    {
      nCnt2++;
      sprintf_s( ch, 256, MENU_LINE.c_str(), nCnt2, fileList.name );
      const string str = ch;
      vecEncript.push_back( str );
    }
  }
  while ( _findnext( hFile, &fileList ) == 0 );

  if ( nCnt1 + nCnt2 == 0 )
  {
    bRet = false;
  }

  _findclose( hFile );
  vecFiles.push_back( BACK_TO_MAIN );
  vecEncript.push_back( BACK_TO_MAIN );

  return bRet;
}


////////////////////////////////////////////////////////////////////////
void saveFile( _vecS & vecFile, string & strFileName, bool bEncript )//
{/////////////////////////////////////////////////////////////////////
  ofstream
    outFile;
  int
    nStart = strFileName.length() - 5;

  if ( bEncript )
  {
    strFileName.replace( nStart, 5, ENC );
  }
  else
  {
    strFileName.replace( nStart, 5, DEC );
  }

  outFile.open( strFileName.c_str(), ios::out );

  if ( !outFile.is_open() )
  {
    cerr << FILE_ERROR_1 << strFileName << NEW_LINES;
    _getch();
    exit( 1 );
  }

  for ( _vecSIt _sit = vecFile.begin(); _sit != vecFile.end(); _sit++ )
  {
    outFile << (*_sit);
  }

  outFile.close();
}


////////////////////////////////////////////////////////////////////////////////
void encriptFile( _vecS & vFiles, char chSelection, bool bEncript /*= true*/)//
{/////////////////////////////////////////////////////////////////////////////
  int
    nLup   = 0,
    nWhich = 0,
    nSize  = 0;
  char
    chRet;
  string
    strText,
    strReturn,
    strEncLine,
    strDecLine,
    strTempLine,
    strFileName;
  _vecS
    vecEncFile,
    vecDecFile,
    vecFileChars,
    vecFileChunks,
    vecTempFile,
    vecFileHold;
  _vecSIt
    _dec,
    _sit,
    _vit;


  for ( _vecSIt _fn = vFiles.begin(); _fn != vFiles.end(); _fn++ )
  {
    string
      fileName = (*_fn);
    char
      chTarget = fileName.c_str()[2];

    strFileName = fileName.substr( fileName.find( "=" ) + 2, fileName.length() - 4  );
    strFileName[strFileName.length() - 1] = '\0';

    if ( chTarget == chSelection )
    {
      ifstream
        inFile( strFileName.c_str(), ios::in );

      if ( !inFile.is_open() )
      {
        cerr << FILE_ERROR_1 << strFileName << NEW_LINES;
        _getch();
        exit( 1 );
      }

      strText.clear();
      strReturn.clear();
      vecEncFile.clear();
      vecDecFile.clear();
      strEncLine.clear();
      strDecLine.clear();
      strTempLine.clear();
      vecTempFile.clear();
      vecFileHold.clear();

      nSize = ( bEncript ) ? 1 : 3;
      getline( inFile, strText );

      if ( bEncript ) // encripting //
      {
        do
        {
          split( strText, nSize, vecFileChars ); // one byte chunks
          strEncLine.clear();

          for ( _sit = vecFileChars.begin(); _sit != vecFileChars.end(); _sit++ )
          {
            char * ch = (char*)(*_sit).c_str();
            strReturn = decimalToAscii( (int)*ch );

            for ( nLup = 0; nLup < BITS_8; nLup++ )
            {
              nWhich = ( rand() % 18 );

              if ( strReturn.c_str()[nLup] == '1' )
              {
                strEncLine += ONE_SEGMENTS[nWhich];
              }
              else
              {
                strEncLine += ZERO_SEGMENTS[nWhich];
              }
            }
          }

          strEncLine += NEW_LINE;
          vecEncFile.push_back( strEncLine );

          strText.clear();
          strEncLine.clear();
          vecFileChars.clear();
        }
        while ( getline( inFile, strText ) );
      }
      else // decripting //
      {
        do
        {
          split( strText, nSize, vecFileChunks ); // 3 byte chunks

          for ( _sit = vecFileChunks.begin(); _sit != vecFileChunks.end(); _sit++ )
          {
            for ( nLup = 0; nLup < SEGS; nLup++ )
            {
              if ( ONE_SEGMENTS[nLup] == (*_sit) )
              {
                strTempLine += '1';
                break;
              }

              if ( ZERO_SEGMENTS[nLup] == (*_sit) )
              {
                strTempLine += '0';
                break;
              }
            }
          }

          strTempLine += CRLF;
          vecTempFile.push_back( strTempLine );
          strTempLine.clear();
          vecFileChunks.clear();
        }
        while ( getline( inFile, strText ) );

        vecDecFile.clear();

        for ( _dec = vecTempFile.begin(); _dec != vecTempFile.end(); _dec++ )
        {
          split( (*_dec), BITS_8, vecFileHold ); // 8 byte chunks

          for ( _vit = vecFileHold.begin(); _vit != vecFileHold.end(); _vit++ )
          {
            strText = (*_vit);
            chRet = asciiToDecimal( strText );
            strDecLine += chRet;
            strText.clear();
          }

          vecDecFile.push_back( strDecLine );
          strDecLine.clear();
          vecFileHold.clear();
        }
      }

      inFile.close();

      if ( bEncript )
      {
        saveFile( vecEncFile, strFileName, bEncript );
      }
      else
      {
        saveFile( vecDecFile, strFileName, bEncript );
      }
    }
  }
}

